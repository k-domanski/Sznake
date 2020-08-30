package com.example.sznake.gameCore;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.sznake.utils.Direction;

import java.io.Serializable;

/**
 * Represents quick time event.
 * <p>
 * Quick time events are triggered when a specified condition is met.
 * Requires the player to move the device in specific direction.
 *
 * @see com.example.sznake.sensorServices.AccelerometerService
 */
public class QTE implements Serializable {
    /**
     * Time value when the quick time event becomes inactive.
     */
    private long stopTime;
    /**
     * Random {@link Direction} that has to be met by a player to successfully complete
     * quick time event.
     */
    private Direction QTEDirection;
    /**
     * Color of the gui element that indicates the quick time event direction.
     */
    private int color = Color.WHITE;

    /**
     * Creates a new quick time event with specified duration.
     * Sets up a random direction.
     *
     * @param timeInMillis  duration of quick time event in milliseconds.
     */
    public QTE(long timeInMillis) {
        this.stopTime = System.currentTimeMillis() + timeInMillis;
        QTEDirection= Direction.getRandomElement();
    }

    /**
     * Checks if quick time event is active.
     *
     * @return  <code>true</code> if the current time in milliseconds is lower than
     *          quick time event {@link #stopTime}, <code>false</code> if otherwise
     */
    public boolean isQTEActive() {
        return System.currentTimeMillis() < this.stopTime;
    }

    /**
     * Checks if given direction is equal to direction expected during quick time event.
     *
     * @param direction  the direction that is checked against quick time event direction
     * @return           <code>true</code> if given direction is equal to current direction
     *                   of quick time event, <code>false</code> if otherwise
     */
    public boolean checkQTE(Direction direction) {
        return QTEDirection == direction;
    }

    /**
     * Draws the graphic symbol of quick time event direction on to the screen.
     *
     * @param canvas   specified {@link Canvas}
     * @param paint    specified {@link Paint}
     * @param screenX  width of the screen in pixels
     * @param screenY  height of the screen in pixels
     */
    public void draw(Canvas canvas, Paint paint, int screenX, int screenY ) {
        if(color == Color.WHITE) {
            color = Color.RED;
        }
        else {
            color = Color.WHITE;
        }
        paint.setColor(color);
        paint.setAlpha(70);
        paint.setTextSize(500);
        canvas.drawText(getQTEDirection().toString(),
                (float)screenX/2-100, (float)screenY/2+100, paint);
    }

    public Direction getQTEDirection() {
        return QTEDirection;
    }
}
