package com.example.sznake;

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

import com.example.sznake.sensors.Accelerometer;
import com.example.sznake.sensors.Gyroscope;
import com.example.sznake.sensors.Light;
import com.example.sznake.sensors.Magnetometer;
import com.example.sznake.sensors.Proximity;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;

    private Accelerometer accelerometer;
    private Light light;
    private Magnetometer magnetometer;

    private long nextFrameTime;
    private final long MILLIS_PER_SECOND = 1000;
    private final long FPS = 10;

    private Game game;

    private int blockSize;
    private final int NUM_BLOCKS_WIDE = 40;
    private int numBlocksHigh;

    private int screenX;
    private int screenY;

    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Paint paint;

    private Gyroscope gyroscope;
    //PROXIMITY
    private Proximity proximity;
    private boolean isPaused;
    public GameView(Context context, Point size) {
        super(context);

        screenX = size.x;
        screenY = size.y;

        blockSize = screenX / NUM_BLOCKS_WIDE;
        numBlocksHigh = screenY / blockSize;

        surfaceHolder = getHolder();
        paint = new Paint();

        gyroscope = new Gyroscope(context);

        //PROXIMITY
        proximity = new Proximity(context);

        try {
            light = new Light(context);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        accelerometer = new Accelerometer(context);

        magnetometer = new Magnetometer(context, NUM_BLOCKS_WIDE, numBlocksHigh);

        newGame();

    }

    @Override
    public void run() {
        String points;
        int qtecolor=Color.WHITE;
        while (isPlaying) {
            isPaused = proximity.isPaused();
            if (isUpdateRequired() && !isPaused) {
                game.update();
                game.setUpgradeColor(accelerometer.getColor());
                game.getGameBoard().getSnake().setDirection(gyroscope.getDirection());
                game.setUpgradePosition(magnetometer.getRandX(), magnetometer.getRandY());

                //
                if(game.isQTEActive()){
                    game.checkQTE(accelerometer.getX_value(),accelerometer.getY_value());
                }

                android.provider.Settings.System.putInt(getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, light.getCurrentScreenBrightness());
                if (surfaceHolder.getSurface().isValid()) {
                    canvas = surfaceHolder.lockCanvas();
                    game.draw(canvas, surfaceHolder, paint, blockSize);
                    points = String.valueOf(game.getPoints());
                    paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    paint.setColor(Color.BLACK);
                    paint.setAlpha(60);
                    paint.setTextSize(100);
                    canvas.drawText(points, 20, 100, paint);
                    if(game.isQTEActive()){

                        if(qtecolor ==Color.WHITE) {
                            qtecolor=Color.RED;
                        }
                        else {
                            qtecolor=Color.WHITE;
                        }
                        paint.setColor(qtecolor);
                        paint.setAlpha(70);
                        paint.setTextSize(500);
                        canvas.drawText(game.getQte().getQTEDirection().toString(), screenX/2-100, screenY/2+100, paint);
                    }

                    surfaceHolder.unlockCanvasAndPost(canvas);
                }

                if(game.isDead())
                    newGame();
            }
        }
    }

    public void resume() {
        gyroscope.register();
        proximity.register();
        light.register();
        accelerometer.register();
        magnetometer.register();
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            gyroscope.unregister();
            proximity.unregister();
            light.unregister();
            accelerometer.unregister();
            magnetometer.unregister();
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

    private void newGame() {
        //gyroscope.setOrientation(Orientation.UP);

        game = new Game(NUM_BLOCKS_WIDE, numBlocksHigh, 5, gyroscope.getDirection());
        game.generateUpgrade();
        game.setDifficultyLevel(DifficultyLevel.EASY);
        game.createBorder();
        nextFrameTime = System.currentTimeMillis();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if(!isPaused) {
                    if (event.getX() >= screenX / 2 && (game.getGameBoard().getSnake().getDirection() == Direction.DOWN || game.getGameBoard().getSnake().getDirection() == Direction.UP)) {
                        gyroscope.setOrientation(Direction.RIGHT);
                        break;
                    } else if (event.getX() < screenX / 2 && (game.getGameBoard().getSnake().getDirection() == Direction.DOWN || game.getGameBoard().getSnake().getDirection() == Direction.UP)) {
                        gyroscope.setOrientation(Direction.LEFT);
                        break;
                    } else if (event.getY() >= screenY / 2 && (game.getGameBoard().getSnake().getDirection() == Direction.RIGHT || game.getGameBoard().getSnake().getDirection() == Direction.LEFT)) {
                        gyroscope.setOrientation(Direction.DOWN);
                        break;
                    } else if (event.getY() < screenY / 2 && (game.getGameBoard().getSnake().getDirection() == Direction.RIGHT || game.getGameBoard().getSnake().getDirection() == Direction.LEFT)) {
                        gyroscope.setOrientation(Direction.UP);
                        break;
                    }
                }
        }


        return true;
    }
}
