package com.example.sznake.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.example.sznake.R;


public class MainActivity extends AppCompatActivity {

    MediaPlayer music;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        music = MediaPlayer.create(MainActivity.this, R.raw.vandetta);
        music.setLooping(true);
        findViewById(R.id.volumeCtrl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(music.isPlaying())
                {
                    music.pause();
                    v.setBackgroundResource(R.drawable.ic_baseline_volume_up_24);
                } else
                {
                    music.start();
                    v.setBackgroundResource(R.drawable.ic_baseline_volume_off_24);
                }
            }
        });

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=  new Intent(MainActivity.this,GameActivity.class);
                startActivityForResult(intent,1);
            }
        });

        if(!Settings.System.canWrite(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            this.startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        music.release();
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
                    startActivityForResult(intent,1);
                }

            }
        }
    }
}