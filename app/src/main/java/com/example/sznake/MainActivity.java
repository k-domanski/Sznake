package com.example.sznake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.sznake.sensors.Accelerometer;

public class MainActivity extends AppCompatActivity {


    private Accelerometer accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });

        accelerometer = new Accelerometer(this);

        accelerometer.setListener(new Accelerometer.Listener() {
            @Override
            public void onTranslation(float transX, float transY) {
                if(transX > 1.0f) {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                }
                else if(transX < -1.0f) {
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        accelerometer.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        accelerometer.unregister();
    }
}