package com.example.sznake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;

public class GrowUp extends PowerUp {


    public GrowUp(int x, int y) {
        super(x, y);
    }

    public GrowUp() {
    }

    @NonNull
    @Override
    public String toString() {
        return "G";
    }

    @Override
    public void draw(Canvas canvas, SurfaceHolder surfaceHolder, Paint paint, int blockSize) {

        paint.setColor(m_color);
        canvas.drawRect(getX() * blockSize, (getY() * blockSize),
                (getX() *blockSize) + blockSize, (getY() * blockSize) + blockSize, paint);
    }
}
