package com.example.sznake.sensorServices;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.example.sznake.gameCore.DifficultyLevel;

public class ProximityService extends SensorService {

    boolean isPaused;
    private DifficultyLevel difficultyLevel = DifficultyLevel.EASY;

    public ProximityService(Context context) {
        super(context, Sensor.TYPE_PROXIMITY, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onTranslation(float[] sensorEventValues) {
        float valX = sensorEventValues[0];
//        if(valX < sensor.getMaximumRange()) {
//            isPaused = !isPaused;
//        }
        if (valX == 0) {
            difficultyLevel = difficultyLevel.getNextLevel();
        }
    }

    public boolean isPaused()
    {
        return isPaused;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

}
