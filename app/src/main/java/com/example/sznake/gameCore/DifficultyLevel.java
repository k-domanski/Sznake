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
    EASY(Color.GREEN),
    /**
     * There are some {@link com.example.sznake.gameCore.gameFields.BlockedField}
     * in the corners of the{@link GameBoard}.
     */
    MEDIUM(Color.YELLOW),
    /**
     * All the edges of {@link GameBoard} are made with
     * {@link com.example.sznake.gameCore.gameFields.BlockedField}.
     */
    HARD(Color.RED);

    /**
     * An array of available difficulty levels.
     */
    private static final  DifficultyLevel[] LEVELS = DifficultyLevel.values();
    /**
     * Corresponding color of given difficulty.
     */
    private int levelColor;

    /**
     * Creates DifficultyLevel with specified color.
     *
     * @param color difficulty level color
     */
    DifficultyLevel(int color) {
        levelColor = color;
    }

    /**
     * Returns the next difficulty level of the current one.
     *
     * @return  {@link DifficultyLevel#MEDIUM} if the current one is {@link DifficultyLevel#EASY},
     *          {@link DifficultyLevel#HARD} if {@link DifficultyLevel#MEDIUM} and
     *          {@link DifficultyLevel#EASY} if current one is {@link DifficultyLevel#HARD}
     */
    public DifficultyLevel getNextLevel() {
        for (int i = 0; i < LEVELS.length; i++) {
            if (LEVELS[i].equals(this)) {
                if (i == LEVELS.length -1) {
                    return LEVELS[0];
                }
                else {
                    return LEVELS[i +1];
                }
            }
        }
        return this;

    }

    /**
     * Returns corresponding color of the difficulty.
     *
     * @return  green if {@link DifficultyLevel#EASY};
     *          yellow if {@link DifficultyLevel#MEDIUM};
     *          red if {@link DifficultyLevel#HARD}
     */
    public int getLevelColor() {
        return levelColor;
    }
}
