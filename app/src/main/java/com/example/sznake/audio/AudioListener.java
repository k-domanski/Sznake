package com.example.sznake.audio;

/**
 * Used for implementing different audio behaviours.
 */
public interface AudioListener {
    /**
     * Use this whenever {@link com.example.sznake.gameCore.gameFields.GrowUpField}
     * is picked.
     */
    public void onGrowUpPicked();

    /**
     * Use this whenever {@link com.example.sznake.gameCore.QTE} is successful.
     */
    public void onQTESuccess();

    /**
     * Use this whenever {@link com.example.sznake.gameCore.Game} starts.
     */
    public void onGameStart();

    /**
     * Use this whenever {@link com.example.sznake.gameCore.Game} is over.
     */
    public void onGameOver();
}
