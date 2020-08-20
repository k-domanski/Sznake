package com.example.sznake.gameCore.gameFields;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.Serializable;

/**
 * GameField is an abstract base class for all other types of fields.
 * It represents a single field on board.
 *
 * @see BlockedField
 * @see BonusField
 * @see EmptyField
 * @see GrowUpField
 * @see SnakeField
 */
public abstract class GameField implements Serializable {
    /**
     * Field coordinate along x axis.
     */
    protected int X;

    /**
     * Field coordinate along y axis.
     */
    protected int Y;

    /**
     * Field color.
     */
    protected int m_color;

    /**
     * Crates a new GameField with specified values of coordinates and color.
     *
     * @param x      position value along x axis
     * @param y      position value along y axis
     * @param color  field color
     */
    public GameField(int x, int y, int color) {
        X = x;
        Y = y;
        m_color = color;
    }

    /**
     * Draws a square at a field coordinates.
     *
     * @param canvas     the {@link Canvas} on which it draws
     * @param paint      the specified {@link Paint}
     * @param blockSize  size of square side in pixels
     */
    public void draw(Canvas canvas, Paint paint, int blockSize) {
        paint.setColor(m_color);
        canvas.drawRect(getX() * blockSize, (getY() * blockSize),
                (getX() *blockSize) + blockSize, (getY() * blockSize) + blockSize, paint);
    }

    public void setCoordinates(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public void setColor(int color)
    {
        m_color = color;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
}
