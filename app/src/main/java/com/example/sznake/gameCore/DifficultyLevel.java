package com.example.sznake.gameCore;

import android.graphics.Color;

/**
 * Represents the difficulty level of a game.
 */
public enum DifficultyLevel {
    /**
     * There are no {@link com.example.sznake.gameCore.gameFields.BlockedField}
     * on the {@link GameBoard}.
     */
    EASY,
    /**
     * There are some {@link com.example.sznake.gameCore.gameFields.BlockedField}
     * in the corners of the{@link GameBoard}.
     */
    MEDIUM,
    /**
     * All the edges of {@link GameBoard} are made with
     * {@link com.example.sznake.gameCore.gameFields.BlockedField}.
     */
    HARD;

    public DifficultyLevel getNextLevel() {
        DifficultyLevel[] values = DifficultyLevel.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(this)) {
                if (i == values.length -1) {
                    return values[0];
                }
                else {
                    return values[i +1];
                }
            }
        }
        return null;
    }

    public int getLevelColor() {
        if(this.equals(EASY)) {
            return Color.GREEN;
        }
        else if(this == MEDIUM) {
            return Color.YELLOW;
        }
        return Color.RED;
    }
}
