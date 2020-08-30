package com.example.sznake.audio;

/**
 * Used for implementing different audio behaviours.
 */
public interface AudioListener {
    /**
     * Use this whenever {@link com.example.sznake.gameCore.gameFields.GrowUpField}
     * is picked.
     */
    void onGrowUpPicked();

    /**
     * Use this whenever {@link com.example.sznake.gameCore.Game} starts.
     */
    void onGameStart();

    /**
     * Use this whenever {@link com.example.sznake.gameCore.Game} is over.
     */
    void onGameOver();
}
