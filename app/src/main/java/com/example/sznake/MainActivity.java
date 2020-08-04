package com.example.sznake;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.example.sznake.sensors.Accelerometer;
import com.example.sznake.sensors.Light;
import com.example.sznake.sensors.SensorBase;
import com.example.sznake.sensors.Proximity;


public class MainActivity extends AppCompatActivity {


    private Accelerometer accelerometer;
    private Proximity proximity;
    private Light light;
    private boolean isToogled = false;
    private int currentScreenBrightness;
    @RequiresApi(api = Build.VERSION_CODES.M)
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

        proximity = new Proximity(this);
        proximity.setListener(new SensorBase.Listener() {
            @Override
            public void onTranslation(float valX, float valY) {

                if (valX < proximity.getSensor().getMaximumRange()) {
                    if(isToogled == false) {
                        isToogled = true;
                    }
                    else {
                        isToogled = false;
                    }

                }

                if(isToogled) {
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                }
                else {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                }
            }
        });

        if(!Settings.System.canWrite(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            this.startActivity(intent);
        }

        try {
            currentScreenBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        light = new Light(this);
        light.setListener(new SensorBase.Listener() {
            @Override
            public void onTranslation(float valX, float valY) {
                if (valX > 1000) {
                    android.provider.Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 255);
                }
                else {
                    android.provider.Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, currentScreenBrightness);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        accelerometer.register();
        proximity.register();
        light.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        accelerometer.unregister();
        proximity.unregister();
        light.unregister();
    }
}