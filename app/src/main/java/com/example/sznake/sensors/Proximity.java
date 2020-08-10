package com.example.sznake.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Proximity extends SensorBase {

    boolean isPaused;

    public Proximity(Context context) {
        super(context, Sensor.TYPE_PROXIMITY, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onTranslation(float[] sensorEventValues) {
        float valX = sensorEventValues[0];
        if(valX < sensor.getMaximumRange()) {
            isPaused = !isPaused;
        }
    }

    public boolean isPaused()
    {
        return isPaused;
    }
}
