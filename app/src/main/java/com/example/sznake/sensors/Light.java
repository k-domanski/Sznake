package com.example.sznake.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Light extends SensorBase {

    public Light(Context context) {
        super(context, Sensor.TYPE_LIGHT, SensorManager.SENSOR_DELAY_GAME);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (listener != null) {
                    listener.onTranslation(event.values[0], 0);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }
}
