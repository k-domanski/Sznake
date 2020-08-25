package com.example.sznake.sensorServices;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Abstract base class represents different types of sensors and their functionality.
 */
public abstract class SensorService {
    /**
     * An instance of {@link SensorManager}.
     */
    protected SensorManager sensorManager;
    /**
     * An instance of {@link Sensor}.
     */
    protected Sensor sensor;
    /**
     * Sensor listener.
     *
     * @see SensorEventListener
     */
    protected SensorEventListener sensorEventListener;
    /**
     * The rate sensor events are delivered at.
     *
     * @see SensorManager#registerListener(SensorEventListener, Sensor, int)
     */
    int m_sensorDelay;

    /**
     * Creates specified SensorService.
     *
     * @param context       specified {@link Context}
     * @param sensorType    type of sensor
     * @param sensorDelay   the rate sensor events are delivered at
     * @see Context
     * @see SensorManager#getDefaultSensor(int)
     */
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

    /**
     * Registers listener.
     *
     * @see SensorManager#registerListener(SensorEventListener, Sensor, int)
     */
    public void register() {
        sensorManager.registerListener(sensorEventListener, sensor, m_sensorDelay);
    }

    /**
     * Unregisters listener.
     *
     * @see SensorManager#unregisterListener(SensorEventListener)
     */
    public void unregister() {
        sensorManager.unregisterListener(sensorEventListener);
    }

    /**
     * Used to implement specific behaviour whenever the values of sensor change.
     *
     * @param sensorEventValues values of monitored sensor.
     * @see SensorEvent#values
     */
    public abstract void onTranslation(float[] sensorEventValues);

    public void setSensorEventListener(SensorEventListener sensorEventListener) {
        this.sensorEventListener = sensorEventListener;
    }
}
