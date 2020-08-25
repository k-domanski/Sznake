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


    public GameView(Context context, Point size, Game game,DifficultyLevel difficultyLevel) {
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
        if(game==null) {
            newGame(difficultyLevel);
        }
        else {
            loadGame(game);
        }
    }


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

    private void drawPoints() {
        String points;
        points = String.valueOf(game.getPoints());
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setColor(Color.BLACK);
        paint.setAlpha(60);
        paint.setTextSize(100);
        canvas.drawText(points, 20, 100, paint);
    }

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

    public boolean isUpdateRequired() {
        if(nextFrameTime <= System.currentTimeMillis()) {
            nextFrameTime = System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;
            return true;
        }
        return false;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private void newGame(DifficultyLevel difficultyLevel) {
        game = new Game(NUM_BLOCKS_WIDE, numBlocksHigh, 5, gyroscopeService.getDirection(),difficultyLevel);
        nextFrameTime = System.currentTimeMillis();
    }
    private void loadGame(Game game) {
        this.game = game;
        nextFrameTime = System.currentTimeMillis();
    }

    public PropertyChangeSupport getChangeSupport() {
        return changeSupport;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
            if (event.getX() >= screenX / 2 && (game.getGameBoard().getSnake().getDirection() == Direction.DOWN || game.getGameBoard().getSnake().getDirection() == Direction.UP)) {
                gyroscopeService.setDirection(Direction.RIGHT);
            } else if (event.getX() < screenX / 2 && (game.getGameBoard().getSnake().getDirection() == Direction.DOWN || game.getGameBoard().getSnake().getDirection() == Direction.UP)) {
                gyroscopeService.setDirection(Direction.LEFT);
            } else if (event.getY() >= screenY / 2 && (game.getGameBoard().getSnake().getDirection() == Direction.RIGHT || game.getGameBoard().getSnake().getDirection() == Direction.LEFT)) {
                gyroscopeService.setDirection(Direction.DOWN);
            } else if (event.getY() < screenY / 2 && (game.getGameBoard().getSnake().getDirection() == Direction.RIGHT || game.getGameBoard().getSnake().getDirection() == Direction.LEFT)) {
                gyroscopeService.setDirection(Direction.UP);
            }
        }


        return true;
    }

    public Game getGame() {
        return game;
    }
}
