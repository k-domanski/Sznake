package com.example.sznake.sensorServices;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.example.sznake.gameCore.DifficultyLevel;

/**
 * Represents the proximity sensor.
 * <p>
 * It is responsible for difficulty level change based on output from proximity sensor.
 */
public class ProximityService extends SensorService {
    /**
     * Difficulty level based on proximity sensor
     */
    private DifficultyLevel difficultyLevel = DifficultyLevel.EASY;

    /**
     * Creates a new ProximityService with Proximity sensor type and normal sampling period.
     *
     * @param context   specified {@link Context}
     * @see Sensor#TYPE_PROXIMITY
     * @see SensorManager#SENSOR_DELAY_NORMAL
     */
    public ProximityService(Context context) {
        super(context, Sensor.TYPE_PROXIMITY, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * Changes difficulty based on monitored sensor.
     * <p>
     * Whenever the value of proximity sensor is 0, difficulty switches to next one.
     *
     * @param sensorEventValues values of monitored sensor.
     * @see DifficultyLevel
     */
    @Override
    public void onTranslation(float[] sensorEventValues) {
        float valX = sensorEventValues[0];
        if (valX == 0) {
            difficultyLevel = difficultyLevel.getNextLevel();
        }
    }

    /**
     *
     * @return  current difficulty level
     */
    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

}
