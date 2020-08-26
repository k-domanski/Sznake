package com.example.sznake.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.example.sznake.audio.AudioManager;
import com.example.sznake.dao.DatabaseHandler;
import com.example.sznake.gameCore.DifficultyLevel;
import com.example.sznake.gameCore.Game;
import com.example.sznake.view.GameView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * Represents game activity.
 */
public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    private DifficultyLevel difficultyLevel;

    /**
     * Creates game on a certain {@link DifficultyLevel} or resumes from database.
     * <p>
     * Setups display.
     * Creates {@link GameView} and listens to the game state.
     * If the game is over it saves the points gained.
     *
     * @see DatabaseHandler
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        int gatheredPoints = getIntent().getIntExtra("points",0);
        boolean resumeGame = getIntent().getBooleanExtra("resume",false);
        Game game = null;

        if(resumeGame){
            game = loadGameFromDatabase();
        }
        else {
            difficultyLevel = (DifficultyLevel) getIntent().getSerializableExtra("difficulty");
        }

        gameView = new GameView(this, size, game, difficultyLevel);

        PropertyChangeListener changeListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (gameView.isGameOver()) {
                    Intent intent = new Intent();
                    intent.putExtra("result", gameView.getGame().getPoints());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        };

        gameView.getChangeSupport().addPropertyChangeListener(changeListener);
        setContentView(gameView);
    }

    /**
     * Resumes the current {@link GameView}.
     * Starts music {@link AudioManager#onGameStart()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
        MainActivity.audioManager.onGameStart();

    }

    /**
     * Pauses the current {@link GameView}.
     * Stops current background music.
     * Saves game.
     */
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
        MainActivity.audioManager.getBackgroundMusic().pause();
        saveGameOnPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void saveGameOnPause() {
        DatabaseHandler databaseHandler= new DatabaseHandler(this);

        if(gameView.isGameOver()){
            databaseHandler.clearGames();
        }
        else {
            try {
                databaseHandler.saveGame(gameView.getGame());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Game loadGameFromDatabase() {
        Game game = null;
        DatabaseHandler databaseHandler= new DatabaseHandler(this);
        try {
            game = databaseHandler.getGame();
            databaseHandler.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return game;
    }
}
