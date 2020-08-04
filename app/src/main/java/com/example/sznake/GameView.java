package com.example.sznake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.sznake.sensors.Accelerometer;
import com.example.sznake.sensors.Gyroscope;
import com.example.sznake.sensors.MotionSensor;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;

    private Context con;

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

//    private Accelerometer accelerometer;
    private Gyroscope gyroscope;
    private final float SENSOR_TRESHOLD = 3.0f;
    public GameView(Context context, Point size) {
        super(context);

        con = context;
        screenX = size.x;
        screenY = size.y;

        blockSize = screenX / NUM_BLOCKS_WIDE;
        numBlocksHigh = screenY / blockSize;

        surfaceHolder = getHolder();
        paint = new Paint();

        gyroscope = new Gyroscope(context);
        gyroscope.setListener(new MotionSensor.Listener() {
            @Override
            public void onTranslation(float valX, float valY) {
                if(valY > SENSOR_TRESHOLD && (game.getGameBoard().getSnake().getOrientation() != Orientation.DOWN)) {
                    game.getGameBoard().getSnake().setOrientation(Orientation.UP);
                }
                else if(valY < -SENSOR_TRESHOLD && (game.getGameBoard().getSnake().getOrientation() != Orientation.UP)) {
                    game.getGameBoard().getSnake().setOrientation(Orientation.DOWN);
                }
                else if(valX > SENSOR_TRESHOLD && (game.getGameBoard().getSnake().getOrientation() != Orientation.LEFT)) {
                    game.getGameBoard().getSnake().setOrientation(Orientation.RIGHT);
                }
                else if(valX < -SENSOR_TRESHOLD && (game.getGameBoard().getSnake().getOrientation() != Orientation.RIGHT)) {
                    game.getGameBoard().getSnake().setOrientation(Orientation.LEFT);
                }
            }
        });
//        accelerometer = new Accelerometer(context);
//        accelerometer.setListener(new Accelerometer.Listener() {
//            @Override
//            public void onTranslation(float transX, float transY, float transZ) {
//                if(transX > 1.0f && (game.getGameBoard().getSnake().getOrientation() != Orientation.DOWN)) {
//                    game.getGameBoard().getSnake().setOrientation(Orientation.UP);
//                }
//                else if(transX < -1.0f && (game.getGameBoard().getSnake().getOrientation() != Orientation.UP)) {
//                    game.getGameBoard().getSnake().setOrientation(Orientation.DOWN);
//                }
//                else if(transY > 1.0f && (game.getGameBoard().getSnake().getOrientation() != Orientation.RIGHT)) {
//                    game.getGameBoard().getSnake().setOrientation(Orientation.LEFT);
//                }
//                else if(transY < -1.0f && (game.getGameBoard().getSnake().getOrientation() != Orientation.LEFT)) {
//                    game.getGameBoard().getSnake().setOrientation(Orientation.RIGHT);
//                }
//            }
//        });

        newGame();

    }

    @Override
    public void run() {

        while (isPlaying) {

            if (isUpdateRequired()) {

                game.update();
                if (surfaceHolder.getSurface().isValid()) {
                    canvas = surfaceHolder.lockCanvas();
                    game.draw(canvas, surfaceHolder, paint, blockSize);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }

                if(game.isDead())
                    newGame();
            }
        }
    }

    public void resume() {
//        accelerometer.register();
        gyroscope.register();
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
//            accelerometer.unregister();
            gyroscope.unregister();
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
        game = new Game(NUM_BLOCKS_WIDE, numBlocksHigh, 2);
        game.generateUpgrade(new GrowUp());
        nextFrameTime = System.currentTimeMillis();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (event.getX() >= screenX / 2 && (game.getGameBoard().getSnake().getOrientation() == Orientation.DOWN || game.getGameBoard().getSnake().getOrientation() == Orientation.UP)) {
                    game.getGameBoard().getSnake().setOrientation(Orientation.RIGHT);
                    break;
                    }
                else if (event.getX() < screenX / 2 && (game.getGameBoard().getSnake().getOrientation() == Orientation.DOWN || game.getGameBoard().getSnake().getOrientation() == Orientation.UP)) {
                    game.getGameBoard().getSnake().setOrientation(Orientation.LEFT);
                    break;
                }
                else if (event.getY() >= screenY / 2 && (game.getGameBoard().getSnake().getOrientation() == Orientation.RIGHT || game.getGameBoard().getSnake().getOrientation() == Orientation.LEFT)) {
                    game.getGameBoard().getSnake().setOrientation(Orientation.DOWN);
                    break;
                }
                else if (event.getY() < screenY / 2 && (game.getGameBoard().getSnake().getOrientation() == Orientation.RIGHT || game.getGameBoard().getSnake().getOrientation() == Orientation.LEFT)) {
                    game.getGameBoard().getSnake().setOrientation(Orientation.UP);
                    break;
                }
        }


        return true;
    }
}
