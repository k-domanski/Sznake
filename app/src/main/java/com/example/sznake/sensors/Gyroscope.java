package com.example.sznake.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.example.sznake.Direction;

public class Gyroscope extends SensorBase {

    private Direction m_direction = Direction.UP;

    public Gyroscope(Context context) {
        super(context, Sensor.TYPE_GYROSCOPE, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onTranslation(float[] sensorEventValues) {
        float valX = sensorEventValues[0];
        float valY = sensorEventValues[1];
        float SENSOR_TRESHOLD = 3.0f;
        if (valY > SENSOR_TRESHOLD && (m_direction != Direction.DOWN)) {
            m_direction = Direction.UP;
        } else if (valY < -SENSOR_TRESHOLD && (m_direction != Direction.UP)) {
            m_direction = Direction.DOWN;
        } else if (valX > SENSOR_TRESHOLD && (m_direction != Direction.LEFT)) {
            m_direction = Direction.RIGHT;
        } else if (valX < -SENSOR_TRESHOLD && (m_direction != Direction.RIGHT)) {
            m_direction = Direction.LEFT;
        }
    }


    public Direction getDirection() {
        return m_direction;
    }

    public void setOrientation(Direction direction) {
        m_direction = direction;
    }
}
