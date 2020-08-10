package com.example.sznake.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Accelerometer extends SensorBase {



    public Accelerometer(Context context) {
        super(context, Sensor.TYPE_LINEAR_ACCELERATION, SensorManager.SENSOR_DELAY_GAME);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(listener != null) {
                    listener.onTranslation(event.values[0], event.values[1]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

    }

}

