package com.example.sznake.gameCore;

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
     * on the edges of the{@link GameBoard}.
     */
    MEDIUM,
    /**
     * All the edges of {@link GameBoard} are made with
     * {@link com.example.sznake.gameCore.gameFields.BlockedField}.
     */
    HARD
}
