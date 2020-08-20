package com.example.sznake.gameCore.gameFields;

import android.graphics.Color;

/**
 * Represents {@link GameField} type that blocks the movement and upon colliding
 * with {@link com.example.sznake.gameCore.Snake} ends the game.
 * 
 * @see GameField
 * @see com.example.sznake.gameCore.Snake
 */
public class BlockedField extends GameField {
    /**
     * Creates a new BlockedField with specified coordinates
     * and default black color.
     * <p>
     * Uses base class constructor.
     *
     * @param x  field coordinate along x axis
     * @param y  field coordinate along y axis
     * @see GameField#GameField(int, int, int) 
     */
    public BlockedField(int x, int y)
    {
        super(x, y, Color.BLACK);
    }

}
