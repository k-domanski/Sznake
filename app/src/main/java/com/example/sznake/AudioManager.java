package com.example.sznake;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioManager implements AudioListener {
    private MediaPlayer backgroundMusic;
    private MediaPlayer pickUp;
    private MediaPlayer qte;
    private MediaPlayer gameOver;
    private boolean isMuted = false;

    public AudioManager(Context context){
        pickUp = MediaPlayer.create(context, R.raw.pick_up);
        backgroundMusic = MediaPlayer.create(context, R.raw.vandetta);
        backgroundMusic.setLooping(true);
        gameOver = MediaPlayer.create(context, R.raw.game_over);
        gameOver.setLooping(false);

    }
    public AudioManager(MediaPlayer pick, MediaPlayer bonus) {
        pickUp = pick;
        pickUp.setLooping(false);

        qte = bonus;
        qte.setLooping(false);
    }

    @Override
    public void onGrowUpPicked() {
        if (isMuted) {
            return;
        }
        pickUp.start();
    }

    @Override
    public void onQTESuccess() {
        qte.start();
    }

    @Override
    public void onGameOver() {
        if (isMuted) {
            return;
        }
        if (gameOver.isPlaying()) {
            return;
        }
        else {
            gameOver.start();
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

    public MediaPlayer getGameOver() {
        return gameOver;
    }
}
