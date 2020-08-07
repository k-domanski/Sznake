package com.example.sznake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class SnakeBodyPart extends GameField {
    public SnakeBodyPart(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "o";
    }

    @Override
    public void draw(Canvas canvas, SurfaceHolder surfaceHolder, Paint paint, int blockSize) {

            paint.setColor(Color.argb(255,255,255,255));
            canvas.drawRect(getX() * blockSize, (getY() * blockSize),
                    (getX() *blockSize) + blockSize, (getY() * blockSize) + blockSize, paint);
    }
}
