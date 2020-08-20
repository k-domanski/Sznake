package com.example.sznake.gameCore.gameFields;

import android.graphics.Color;

/**
 * Represents an empty type of {@link GameField}.
 * 
 * @see GameField
 */
public class EmptyField extends GameField {
    /**
     * Creates a new EmptyField with specified coordinates
     * and default color.
     * <p>
     * Calls parent's constructor.
     * 
     * @param x  coordinate along x axis
     * @param y  coordinate along y axis
     * @see GameField#GameField(int, int, int) 
     */
    public EmptyField(int x, int y) {
        super(x, y, Color.argb(255,255,255,210));
    }

}
