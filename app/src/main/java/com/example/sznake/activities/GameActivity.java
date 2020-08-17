package com.example.sznake.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.sznake.R;
import com.example.sznake.gameCore.Game;
import com.example.sznake.view.GameView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameActivity extends AppCompatActivity {


    GameView gameView;
    public PropertyChangeListener changeListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        gameView = new GameView(this, size);
        changeListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(gameView.isGameOver()){
                    startActivity(new Intent(GameActivity.this, GameOverActivity.class));
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
    }

    @Override
    protected void onPause() {
        super.onPause();

        gameView.pause();
    }
}
