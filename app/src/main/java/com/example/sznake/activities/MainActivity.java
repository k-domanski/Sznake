package com.example.sznake.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.example.sznake.AudioManager;
import com.example.sznake.R;
import com.example.sznake.dao.DatabaseHandler;
import com.example.sznake.gameCore.DifficultyLevel;
import com.example.sznake.sensorServices.ProximityService;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    static public AudioManager audioManager;
    private DifficultyLevel difficultyLevel=DifficultyLevel.EASY;
    private TextView DifficultyView;
    private ProximityService proximity;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioManager = new AudioManager(MainActivity.this);
        DifficultyView = findViewById(R.id.difficulty);
        DifficultyView.setTextColor(Color.GREEN);
        DifficultyView.setText(difficultyLevel.toString());
        proximity = new ProximityService(this);

        findViewById(R.id.volumeCtrl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!audioManager.isMuted()) {
                    audioManager.setMuted(true);
                    audioManager.onGameStart();
                    v.setBackgroundResource(R.drawable.ic_baseline_volume_off_24);
                }
                else {
                    audioManager.setMuted(false);
                    audioManager.onGameStart();
                    v.setBackgroundResource(R.drawable.ic_baseline_volume_up_24);
                }

            }
        });

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=  new Intent(MainActivity.this,GameActivity.class);
                intent.putExtra("difficulty",difficultyLevel);
                startActivityForResult(intent,1);
            }
        });


        findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=  new Intent(MainActivity.this,GameActivity.class);
                intent.putExtra("resume",true);
                startActivityForResult(intent,1);
            }
        });

        if(!Settings.System.canWrite(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            this.startActivity(intent);
        }
        findViewById(R.id.exitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        proximity.setSensorEventListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                proximity.onTranslation(event.values);
                difficultyLevel = proximity.getDifficultyLevel();
                DifficultyView.setTextColor(difficultyLevel.getLevelColor());
                DifficultyView.setText(difficultyLevel.toString());
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            if(resultCode== Activity.RESULT_OK){
                int points = data.getIntExtra("result",0);
                Intent intent=  new Intent(MainActivity.this,GameOverActivity.class);
                intent.putExtra("points",points);
                this.startActivityForResult(intent,2);
            }
        }
        else if(requestCode==2){
            if(resultCode == Activity.RESULT_OK){
                boolean newGame = data.getBooleanExtra("tryAgain",false);
                if(newGame){
                    Intent intent=  new Intent(MainActivity.this,GameActivity.class);
                    intent.putExtra("difficulty",difficultyLevel);
                    startActivityForResult(intent,1);
                }

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        audioManager.onGameStart();
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        int highscore=0;
        highscore=databaseHandler.getHighestScore();
        TextView scoreView = findViewById(R.id.hpoints);
        scoreView.setText(String.valueOf(highscore));
        try {
            TextView textView = findViewById(R.id.load);
            if(databaseHandler.getGame()==null){
                textView.setVisibility(View.INVISIBLE);
            }
            else {
                textView.setVisibility(View.VISIBLE);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        proximity.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        audioManager.getBackgroundMusic().pause();
        proximity.unregister();
    }

}