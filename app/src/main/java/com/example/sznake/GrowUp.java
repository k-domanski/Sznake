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
//        if (surfaceHolder.getSurface().isValid()) {
//            canvas = surfaceHolder.lockCanvas();
            paint.setColor(Color.argb(255,255,0,0));
            canvas.drawRect(getX() * blockSize, (getY() * blockSize), (getX() *blockSize) + blockSize, (getY() * blockSize) + blockSize, paint);
//            surfaceHolder.unlockCanvasAndPost(canvas);
//        }
    }
}
