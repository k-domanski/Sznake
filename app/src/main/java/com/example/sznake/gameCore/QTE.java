package com.example.sznake.gameCore;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.sznake.utils.Direction;

import java.io.Serializable;

public class QTE implements Serializable {
    private long stopTime;
    private Direction QTEDirection;
    private int color = Color.WHITE;

    public Direction getQTEDirection() {
        return QTEDirection;
    }

    public QTE(long timeInMillis) {
        this.stopTime = System.currentTimeMillis() + timeInMillis;
        QTEDirection= Direction.getRandomElement();
    }

    public QTE() {

        this.stopTime = System.currentTimeMillis() + 3000;
        QTEDirection= Direction.getRandomElement();
    }

    public boolean isQTEActive() {
        return System.currentTimeMillis() < this.stopTime;
    }

    public boolean checkQTE(Direction direction) {
        if (QTEDirection == direction) {
            return true;
        }
        return false;
    }

    public void draw(Canvas canvas, Paint paint, int screenX, int screenY ) {
        if(color == Color.WHITE) {
            color=Color.RED;
        }
        else {
            color=Color.WHITE;
        }
        paint.setColor(color);
        paint.setAlpha(70);
        paint.setTextSize(500);
        canvas.drawText(getQTEDirection().toString(),
                screenX/2-100, screenY/2+100, paint);
    }
}
