package com.example.sznake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;

public class BlockedField extends GameField {

    public BlockedField(int x, int y)
    {
        super(x, y);
    }

    @NonNull
    @Override
    public String toString() {
        return "#";
    }

    @Override
    public void draw(Canvas canvas, SurfaceHolder surfaceHolder, Paint paint, int blockSize) {

        paint.setColor(Color.argb(255,0,255,0));
        canvas.drawRect(getX() * blockSize, (getY() * blockSize),
                (getX() *blockSize) + blockSize, (getY() * blockSize) + blockSize, paint);
    }
}
