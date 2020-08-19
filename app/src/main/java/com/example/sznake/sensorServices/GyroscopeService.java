package com.example.sznake.sensorServices;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.example.sznake.utils.Direction;

public class GyroscopeService extends SensorService {

    private static final float GYRO_TRESHOLD = 3.0f;
    private Direction m_direction = Direction.UP;

    public GyroscopeService(Context context) {
        super(context, Sensor.TYPE_GYROSCOPE, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onTranslation(float[] sensorEventValues) {
        float valX = sensorEventValues[0];
        float valY = sensorEventValues[1];
        if (valY > GYRO_TRESHOLD) {
            m_direction = Direction.UP;
        } else if (valY < -GYRO_TRESHOLD) {
            m_direction = Direction.DOWN;
        } else if (valX > GYRO_TRESHOLD) {
            m_direction = Direction.RIGHT;
        } else if (valX < -GYRO_TRESHOLD) {
            m_direction = Direction.LEFT;
        }
    }


    public Direction getDirection() {
        return m_direction;
    }

    public void setDirection(Direction direction) {
        m_direction = direction;
    }
}
