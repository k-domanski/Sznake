package com.example.sznake.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.sznake.gameCore.DifficultyLevel;
import com.example.sznake.gameCore.Game;
import com.example.sznake.utils.Direction;
import com.example.sznake.sensorServices.AccelerometerService;
import com.example.sznake.sensorServices.GyroscopeService;
import com.example.sznake.sensorServices.LightService;
import com.example.sznake.sensorServices.MagnetometerService;
import com.example.sznake.sensorServices.ProximityService;

import java.beans.PropertyChangeSupport;

/**
 * 
 */
public class GameView extends SurfaceView implements Runnable {
    private static final long MILLIS_PER_SECOND = 1000;
    private static final long FPS = 10;
    private static final int NUM_BLOCKS_WIDE = 40;

    private Thread thread;

    private boolean isPlaying;
    private boolean gameOver;

    private PropertyChangeSupport changeSupport;

    private AccelerometerService accelerometerService;
    private LightService lightService;
    private MagnetometerService magnetometerService;
    private GyroscopeService gyroscopeService;
    private ProximityService proximityService;

    private long nextFrameTime;

    private Game game;

    private int blockSize;
    private int numBlocksHigh;

    private int screenX;
    private int screenY;

    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Paint paint;

    /**
     * Creates a new GameView with specified screen size, game and difficulty level.
     * <p>
     * Creates all the required {@link com.example.sznake.sensorServices.SensorService},
     * starts a new game or loads an existing one.
     *
     * @param context          specified {@link Context}
     * @param size             screen size
     * @param game             <code>null</code> if new game, otherwise game to be loaded.
     * @param difficultyLevel  desired difficulty level of the game
     */
    public GameView(Context context, Point size, Game game, DifficultyLevel difficultyLevel) {
        super(context);
        screenX = size.x;
        screenY = size.y;
        blockSize = screenX / NUM_BLOCKS_WIDE;
        numBlocksHigh = screenY / blockSize;

        surfaceHolder = getHolder();
        paint = new Paint();

        gyroscopeService = new GyroscopeService(context);

        proximityService = new ProximityService(context);

        try {
            lightService = new LightService(context);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        accelerometerService = new AccelerometerService(context);

        magnetometerService = new MagnetometerService(context, NUM_BLOCKS_WIDE, numBlocksHigh);

        changeSupport = new PropertyChangeSupport(this);
        if(game == null) {
            newGame(difficultyLevel);
        }
        else {
            loadGame(game);
        }
    }

    /**
     * Contains games main loop.
     * <p>
     * Updates the position of a {@link com.example.sznake.gameCore.gameFields.GrowUpField}.
     * Updates the game {@link Game#update()}.
     * Updates {@link com.example.sznake.gameCore.Snake} position based on
     * {@link GyroscopeService}.
     * If quick time event is active checks if event is successful based on
     * {@link AccelerometerService}.
     * Updates the current screen brightness based on {@link LightService}.
     * <p>
     * Draws game board onto the screen {@link Game#draw(Canvas, Paint, int)}.
     * Draws points onto the screen {@link #drawPoints()}.
     * If {@link com.example.sznake.gameCore.QTE} is active then draws it onto the screen.
     * Checks if {@link Game} is over.
     */
    @Override
    public void run() {
        while (isPlaying) {
            if (isUpdateRequired()) {
                game.setUpgradePosition(magnetometerService.getRandX(),
                        magnetometerService.getRandY());

                game.update();

                if(!game.getGameBoard().getSnake().getDirection().isOpposite(
                        gyroscopeService.getDirection())) {
                    game.getGameBoard().getSnake().setDirection(gyroscopeService.getDirection());
                }

                if(game.isQTEActive()){
                    game.checkQTE(accelerometerService.getDirection());
                }

                android.provider.Settings.System.putInt(
                        getContext().getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS,
                        lightService.getCurrentScreenBrightness());

                if (surfaceHolder.getSurface().isValid()) {
                    canvas = surfaceHolder.lockCanvas();

                    game.draw(canvas, paint, blockSize);

                    drawPoints();

                    if(game.isQTEActive()) {
                        game.getQte().draw(canvas, paint, screenX, screenY);
                    }

                    surfaceHolder.unlockCanvasAndPost(canvas);
                }

                if(game.isDead()) {
                    gameOver = true;
                    this.changeSupport.firePropertyChange("gameOver",false,true);
                }

            }
        }
    }

    /**
     * Resumes game. Registers all {@link com.example.sznake.sensorServices.SensorService}.
     * Starts a new {@link Thread}.
     */
    public void resume() {
        gyroscopeService.register();
        proximityService.register();
        lightService.register();
        accelerometerService.register();
        magnetometerService.register();
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Pauses the game. Unregisters all {@link com.example.sznake.sensorServices.SensorService}.
     */
    public void pause() {
        try {
            gyroscopeService.unregister();
            proximityService.unregister();
            lightService.unregister();
            accelerometerService.unregister();
            magnetometerService.unregister();
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the update is required.
     *
     * @return  <code>true</code> if the next frame time is lower or equal to the
     *          current time in millis, otherwise <code>false</code>
     */
    public boolean isUpdateRequired() {
        if(nextFrameTime <= System.currentTimeMillis()) {
            nextFrameTime = System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
            if (event.getX() >= screenX / 2 && (game.getGameBoard().getSnake().getDirection() == Direction.DOWN || game.getGameBoard().getSnake().getDirection() == Direction.UP)) {
                gyroscopeService.setDirection(Direction.RIGHT);
            } else if (event.getX() < (float)(screenX / 2) && (game.getGameBoard().getSnake().getDirection() == Direction.DOWN || game.getGameBoard().getSnake().getDirection() == Direction.UP)) {
                gyroscopeService.setDirection(Direction.LEFT);
            } else if (event.getY() >= screenY / 2 && (game.getGameBoard().getSnake().getDirection() == Direction.RIGHT || game.getGameBoard().getSnake().getDirection() == Direction.LEFT)) {
                gyroscopeService.setDirection(Direction.DOWN);
            } else if (event.getY() < screenY / 2 && (game.getGameBoard().getSnake().getDirection() == Direction.RIGHT || game.getGameBoard().getSnake().getDirection() == Direction.LEFT)) {
                gyroscopeService.setDirection(Direction.UP);
            }
        }
        return true;
    }


    /**
     * Creates a new {@link Game} with specified difficulty.
     *
     * @param difficultyLevel desired difficulty level of a game.
     * @see DifficultyLevel
     */
    private void newGame(DifficultyLevel difficultyLevel) {
        game = new Game(NUM_BLOCKS_WIDE, numBlocksHigh, 5,
                gyroscopeService.getDirection(),difficultyLevel);
        nextFrameTime = System.currentTimeMillis();
    }

    /**
     * Loads a game.
     *
     * @param game  {@link Game} to be loaded
     */
    private void loadGame(Game game) {
        this.game = game;
        nextFrameTime = System.currentTimeMillis();
    }

    /**
     * Draws the current points value onto the screen.
     */
    private void drawPoints() {
        String points;
        points = String.valueOf(game.getPoints());
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setColor(Color.BLACK);
        paint.setAlpha(60);
        paint.setTextSize(100);
        canvas.drawText(points, 20, 100, paint);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public PropertyChangeSupport getChangeSupport() {
        return changeSupport;
    }

    public Game getGame() {
        return game;
    }
}
