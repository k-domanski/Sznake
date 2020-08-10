package com.example.sznake.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.sznake.Orientation;

public class Gyroscope extends SensorBase {

    private Orientation m_orientation = Orientation.UP;

    public Gyroscope(Context context) {
        super(context, Sensor.TYPE_GYROSCOPE, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onTranslation(float[] sensorEventValues) {
        float valX = sensorEventValues[0];
        float valY = sensorEventValues[1];
        float SENSOR_TRESHOLD = 3.0f;
        if (valY > SENSOR_TRESHOLD && (m_orientation != Orientation.DOWN)) {
            m_orientation = Orientation.UP;
        } else if (valY < -SENSOR_TRESHOLD && (m_orientation != Orientation.UP)) {
            m_orientation = Orientation.DOWN;
        } else if (valX > SENSOR_TRESHOLD && (m_orientation != Orientation.LEFT)) {
            m_orientation = Orientation.RIGHT;
        } else if (valX < -SENSOR_TRESHOLD && (m_orientation != Orientation.RIGHT)) {
            m_orientation = Orientation.LEFT;
        }
    }

    public Orientation getOrientation() {
        return m_orientation;
    }

    public void setOrientation(Orientation orientation) {
        m_orientation = orientation;
    }
}
