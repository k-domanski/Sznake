package com.example.sznake.sensorServices;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

public class ProximityService extends SensorService {

    boolean isPaused;

    public ProximityService(Context context) {
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
