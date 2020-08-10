package com.example.sznake.sensors;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Accelerometer extends SensorBase {

    int m_color = Color.GREEN;

    public Accelerometer(Context context) {
        super(context, Sensor.TYPE_LINEAR_ACCELERATION, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onTranslation(float[] sensorEventValues) {
        float transX = sensorEventValues[0];
        if(transX > 1.0f) {
            m_color = Color.YELLOW;
        }
        else if(transX < -1.0f) {
            m_color = Color.GREEN;
        }
    }

    public int getColor() {
        return m_color;
    }
}

