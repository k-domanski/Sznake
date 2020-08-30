package com.example.sznake.audio;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.sznake.R;

import java.util.ArrayList;

/**
 * Responsible for controlling audio behaviour.
 * <p>
 * Contains all the sound effects and music, implements different behaviour
 * of specific game events that require sound.
 */
public class AudioManager implements AudioListener {
    private ArrayList<Integer> backgroundPlaylist = new ArrayList<>();
    private MediaPlayer backgroundMusic;
    private MediaPlayer pickUpMusic;
    private MediaPlayer gameOverMusic;
    private boolean isMuted = false;
    Context m_context;
    int trackNumber = 0;

    /**
     * Creates a new AudioManager.
     * <p>
     * Initializes all of {@link MediaPlayer} fields with specified audio files from
     * resources. Creates a list of background music.
     *
     * @param context  specified {@link Context}
     */
    public AudioManager(Context context) {
        m_context = context;
        pickUpMusic = MediaPlayer.create(context, R.raw.pick_up);
        backgroundPlaylist.add(R.raw.vandetta);
        backgroundPlaylist.add(R.raw.blues);
        backgroundPlaylist.add(R.raw.spaceships);
        backgroundMusic = MediaPlayer.create(context, backgroundPlaylist.get(trackNumber));
        backgroundMusic.setLooping(true);
        gameOverMusic = MediaPlayer.create(context, R.raw.game_over);
        gameOverMusic.setLooping(false);

    }

    /**
     * Unless muted, plays pick up sound whenever {@link com.example.sznake.gameCore.Snake}
     * picks {@link com.example.sznake.gameCore.gameFields.GrowUpField}.
     */
    @Override
    public void onGrowUpPicked() {
        if (isMuted) {
            return;
        }
        pickUpMusic.start();
    }

    /**
     * Unless muted or sound already playing, plays the game over sound.
     */
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

    /**
     * Plays background music on the start of a game.
     * <p>
     * If muted, pauses music.
     * If music already playing does nothing.
     */
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

    /**
     * Changes the background music.
     * <p>
     * Whenever called, changes current background music to next music inside the list
     * and starts it. If the trackNumber exceeds the size of the list, it resets to the beginning.
     */
    public void changeBackgroundMusic()
    {
        backgroundMusic.reset();
        trackNumber++;
        if (trackNumber >= backgroundPlaylist.size()) {
            trackNumber = 0;
        }
        backgroundMusic = MediaPlayer.create(m_context,backgroundPlaylist.get(trackNumber));
        backgroundMusic.setLooping(true);
        backgroundMusic.start();
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
}
