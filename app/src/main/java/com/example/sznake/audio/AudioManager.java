package com.example.sznake.audio;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.sznake.R;

import java.util.ArrayList;

public class AudioManager implements AudioListener {
    private ArrayList<Integer> backgroundPlaylist = new ArrayList<>();
    private MediaPlayer backgroundMusic;
    private MediaPlayer pickUpMusic;
    private MediaPlayer qteMusic;
    private MediaPlayer gameOverMusic;
    private boolean isMuted = false;
    Context m_context;
    int trackNumber = 0;

    public AudioManager(Context context) {
        m_context = context;
        pickUpMusic = MediaPlayer.create(context, R.raw.pick_up);
        backgroundPlaylist.add(R.raw.vandetta);
        backgroundPlaylist.add(R.raw.blues);
        backgroundPlaylist.add(R.raw.snowflake);
        backgroundPlaylist.add(R.raw.spaceships);
        backgroundPlaylist.add(R.raw.sundays);
        backgroundMusic = MediaPlayer.create(context, backgroundPlaylist.get(trackNumber));
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

    public void changeBackgroundMusic()
    {
        backgroundMusic.reset();
        trackNumber++;
        if(trackNumber >= backgroundPlaylist.size())
            trackNumber = 0;
        backgroundMusic = MediaPlayer.create(m_context,backgroundPlaylist.get(trackNumber));
        backgroundMusic.setLooping(true);
        backgroundMusic.start();
    }
}
