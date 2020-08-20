package com.example.sznake.gameCore.gameFields;

/**
 * BonusField is an abstract class that represents
 * any type of GameField that influences game play.
 *
 * @see GameField
 * @see GrowUpField
 */
public abstract class BonusField extends GameField {
    /**
     * Creates a new BonusField with specified coordinates and color.
     * <p>
     * Uses base class constructor.
     * 
     * @param x      field coordinate along x axis
     * @param y      field coordinate along y axis
     * @param color  field color
     * @see GameField#GameField(int, int, int)
     */
    protected BonusField(int x, int y, int color) {
        super(x, y, color);
    }
}
