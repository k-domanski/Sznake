package com.example.sznake;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class GameField {
    protected int X;
    protected int Y;

    public GameField(int x, int y) {
        X = x;
        Y = y;
    }

    public GameField() {
    }

    public void setCoordinates(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return X;
    }

    public int getY() {

        return Y;
    }

    public void draw(Canvas canvas, SurfaceHolder surfaceHolder, Paint paint, int blockSize) {

    }
}
