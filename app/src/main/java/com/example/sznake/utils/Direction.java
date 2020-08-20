package com.example.sznake.utils;

import androidx.annotation.NonNull;

import java.util.Random;

/**
 * Class represents directions in 2Ds space. Relative to the screen.
 * <p>
 * Used mainly to determine direction in which snake is supposed to be moving.
 */
public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    /**
     * Picks a random element from the Direction enum.
     *
     * @return      random direction
     */
    public static Direction getRandomElement() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }

    /**
     * Gets the string representation of direction.
     * <p>
     * Used as a simple way of indicating to user the direction in which the movement
     * needs to be performed in "quick time events".
     *
     * @return      {@link String} corresponding to a given direction
     * @see         com.example.sznake.gameCore.QTE
     */
    @NonNull
    @Override
    public String toString() {
        if (this.equals(UP)) {
            return "^";
        }
        if (this.equals(DOWN)) {
            return "V";
        }
        if (this.equals(LEFT)) {
            return "<";
        }
        if (this.equals(RIGHT)) {
            return ">";
        }
        return "";
    }

    /**
     * Checks if given direction is opposite.
     *
     * @param direction  the direction to check against
     * @return           <code>true</code> if the given direction is opposite;
     *                   <code>false</code> otherwise
     */
    public boolean isOpposite(Direction direction) {

        switch (this) {
            case UP:
                return direction == Direction.DOWN;

            case DOWN:
                return direction == Direction.UP;

            case LEFT:
                return direction == Direction.RIGHT;

            case RIGHT:
                return direction == Direction.LEFT;

            default:
                return false;
        }
    }
}
