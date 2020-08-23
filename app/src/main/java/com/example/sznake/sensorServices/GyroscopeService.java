package com.example.sznake.sensorServices;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.example.sznake.utils.Direction;

/**
 * Represents the gyroscope sensor.
 * <p>
 * It is responsible for changing {@link Direction}
 * based on output from gyroscope sensor.
 */
public class GyroscopeService extends SensorService {

    /**
     * Constant represents sensitivity treshold of sensor values.
     */
    private static final float GYRO_TRESHOLD = 3.0f;
    /**
     * Direction based on gyroscope output.
     */
    private Direction m_direction = Direction.UP;

    /**
     * Creates new GyroscopeService with gyroscope sensor type and game sensor delay.
     *
     * @param context  specified {@link Context}
     */
    public GyroscopeService(Context context) {
        super(context, Sensor.TYPE_GYROSCOPE, SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * Called whenever gyroscope sensor values change.
     * Passes sensor values to {@link #changeDirection(float, float)}.
     *
     * @param sensorEventValues values of monitored sensor.
     */
    @Override
    public void onTranslation(float[] sensorEventValues) {
        float valX = sensorEventValues[0];
        float valY = sensorEventValues[1];
        changeDirection(valX, valY);
    }

    /**
     * Changes direction based on gyroscope values.
     *
     * @param valX  angular speed around the x-axis, if the valX parameter is positive and
     *              greater than {@link #GYRO_TRESHOLD} direction changes to RIGHT; if valX
     *              parameter is negative and smaller than negative value of
     *              {@link #GYRO_TRESHOLD} direction changes to LEFT.
     * @param valY  angular speed around the y-axis, if the valY parameter is positive and
     *              greater than {@link #GYRO_TRESHOLD} direction changes to UP; if valY
     *              parameter is negative and smaller than negative value of
     *              {@link #GYRO_TRESHOLD} direction changes to DOWN.
     */
    private void changeDirection(float valX, float valY) {
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
