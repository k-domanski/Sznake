package com.example.sznake.sensorServices;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public abstract class SensorService {
    protected SensorManager sensorManager;
    protected Sensor sensor;
    protected SensorEventListener sensorEventListener;
    int m_sensorDelay;

    protected SensorService(Context context, int sensorType, int sensorDelay)
    {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(sensorType);
        m_sensorDelay = sensorDelay;
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                onTranslation(event.values);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    public void register() {
        sensorManager.registerListener(sensorEventListener, sensor, m_sensorDelay);
    }

    public void unregister() {
        sensorManager.unregisterListener(sensorEventListener);
    }

    public abstract void onTranslation(float[] sensorEventValues);
}