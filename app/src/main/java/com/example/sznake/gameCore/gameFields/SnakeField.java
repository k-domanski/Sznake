package com.example.sznake.gameCore.gameFields;

import android.graphics.Color;

/**
 * Represents a type of {@link GameField} that is a part of
 * {@link com.example.sznake.gameCore.Snake}.
 */
public class SnakeField extends GameField {
    /**
     * Creates a new SnakeField with specified coordinates
     * and default color.
     * <p>
     * Uses base class constructor.
     *
     * @param x  coordinate along x axis
     * @param y  coordinate along y axis
     * @see GameField#GameField(int, int, int)
     */
    public SnakeField(int x, int y) {
        super(x, y, Color.argb(255,0,102,51));
    }
}
