package com.example.sznake;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioManager implements AudioListener {
    private MediaPlayer backgroundMusic;
    private MediaPlayer pickUpMusic;
    private MediaPlayer qteMusic;
    private MediaPlayer gameOverMusic;
    private boolean isMuted = false;

    public AudioManager(Context context) {
        pickUpMusic = MediaPlayer.create(context, R.raw.pick_up);
        backgroundMusic = MediaPlayer.create(context, R.raw.vandetta);
        backgroundMusic.setLooping(true);
        gameOverMusic = MediaPlayer.create(context, R.raw.game_over);
        gameOverMusic.setLooping(false);

    }

    @Override
    public void onGrowUpPicked() {
        if (isMuted) {
            return;
        }
        pickUpMusic.start();
    }

    @Override
    public void onQTESuccess() {
        qteMusic.start();
    }

    @Override
    public void onGameOver() {
        if (isMuted) {
            return;
        }
        if (gameOverMusic.isPlaying()) {
            return;
        }
        else {
            gameOverMusic.start();
        }
    }

    @Override
    public void onGameStart() {
        if(isMuted) {
            backgroundMusic.pause();
        }
        else if(backgroundMusic.isPlaying()) {
            return;
        }
        else {
            backgroundMusic.start();
        }
    }

    public boolean isMuted() {
        return isMuted;
    }


    public void setMuted(boolean muted) {
        isMuted = muted;
    }

    public MediaPlayer getBackgroundMusic() {
        return backgroundMusic;
    }

    public MediaPlayer getGameOverMusic() {
        return gameOverMusic;
    }
}
