package com.example.sznake.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sznake.R;
import com.example.sznake.dao.DatabaseHandler;


public class GameOverActivity extends AppCompatActivity {
    MediaPlayer gameOverMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        gameOverMusic = MainActivity.audioManager.getGameOverMusic();



        int gatheredPoints = getIntent().getIntExtra("points",0);
        TextView pointsField = findViewById(R.id.points);
            pointsField.setText(String.valueOf(gatheredPoints));
        DatabaseHandler databaseHandler= new DatabaseHandler(this);
        databaseHandler.savePoints(gatheredPoints);
        databaseHandler.close();



        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("tryAgain",false);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
        findViewById(R.id.again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("tryAgain",true);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.audioManager.onGameOver();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
