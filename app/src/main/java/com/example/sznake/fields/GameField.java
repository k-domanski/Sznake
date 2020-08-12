package com.example.sznake.fields;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public abstract class GameField {
    protected int X;
    protected int Y;
    protected int m_color;

    public GameField(int x, int y, int color) {
        X = x;
        Y = y;
        m_color = color;
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
        paint.setColor(m_color);
        canvas.drawRect(getX() * blockSize, (getY() * blockSize),
                (getX() *blockSize) + blockSize, (getY() * blockSize) + blockSize, paint);
    }

    public void setColor(int color)
    {
        m_color = color;
    }
}
