package com.example.sznake.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.example.sznake.dao.DatabaseHandler;
import com.example.sznake.gameCore.Game;
import com.example.sznake.view.GameView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class GameActivity extends AppCompatActivity {

    GameView gameView;

    public PropertyChangeListener changeListener;


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
            DatabaseHandler databaseHandler= new DatabaseHandler(this);
            try {
                game = databaseHandler.getGame();
                databaseHandler.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        gameView = new GameView(this, size,game);
        changeListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(gameView.isGameOver()){
                    Intent intent= new Intent();
                    intent.putExtra("result",gameView.getGame().getPoints());
                    setResult(Activity.RESULT_OK,intent);
                    finish();

                }
            }
        };
        gameView.getChangeSupport().addPropertyChangeListener(changeListener);
        setContentView(gameView);


    }



    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
        MainActivity.audioManager.onGameStart();

    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
        MainActivity.audioManager.getBackgroundMusic().pause();

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

    @Override
    protected void onDestroy() {


        super.onDestroy();
    }
}
