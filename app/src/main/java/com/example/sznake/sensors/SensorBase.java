package com.example.sznake.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public abstract class SensorBase {
    protected SensorManager sensorManager;
    protected Sensor sensor;
    protected SensorEventListener sensorEventListener;

    protected SensorBase(Context context, int sensorType)
    {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(sensorType);
    }

    public interface Listener {
        void onTranslation(float valX, float valY);
    }

    protected Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void register() {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void unregister() {
        sensorManager.unregisterListener(sensorEventListener);
    }
}
