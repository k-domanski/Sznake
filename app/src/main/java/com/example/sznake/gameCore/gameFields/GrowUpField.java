package com.example.sznake.gameCore.gameFields;

import android.graphics.Color;

/**
 * Represents a type of {@link BonusField} that upon
 * colliding with {@link com.example.sznake.gameCore.Snake}
 * makes it bigger and adds points to player.
 *
 * @see BonusField
 * @see GameField
 * @see com.example.sznake.gameCore.Snake
 */
public class GrowUpField extends BonusField {
    /**
     * Creates a new GrowUpField with specified coordinates
     * and default green color.
     * <p>
     * Calls parent's constructor.
     *
     * @param x coordinate along x axis
     * @param y coordinate along y axis
     * @see BonusField#BonusField(int, int, int) 
     */
    public GrowUpField(int x, int y) {
        super(x, y, Color.GREEN);
    }

}
