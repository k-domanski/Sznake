package com.example.sznake.sensorServices;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.example.sznake.utils.Direction;

/**
 * Represents the accelerometer sensor.
 * <p>
 * Responsible for {@link Direction} change based on output from accelerometer sensor.
 * Used for quick time events.
 * 
 * @see com.example.sznake.gameCore.QTE
 */
public class AccelerometerService extends SensorService {

    /**
     * Constant represents sensitivity threshold of sensor values.
     */
    private static final float ACC_TRESHOLD = 1.0f;
    /**
     * Direction based on accelerometer output.
     */
    private Direction direction;

    /**
     * Creates new AccelerometerService with linear acceleration sensor type and
     * game sensor delay.
     *
     * @param context specified {@link Context}
     */
    public AccelerometerService(Context context) {
        super(context, Sensor.TYPE_LINEAR_ACCELERATION, SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * Called whenever accelerometer sensor values change.
     * Passes acceleration force along x and y axis to {@link #setDirection(float, float)}
     *
     * @param sensorEventValues values of monitored sensor.
     */
    @Override
    public void onTranslation(float[] sensorEventValues) {
        float transX = sensorEventValues[0];
        float transY = sensorEventValues[1];
        setDirection(transX, transY);
    }

    /**
     * Sets direction according to values of acceleration force.
     *
     * @param transX  acceleration force along x-axis, if the transX parameter is positive and
     *                greater than {@link #ACC_TRESHOLD} direction changes to RIGHT; if transX
     *                parameter is negative and smaller than negative value of
     *                {@link #ACC_TRESHOLD} direction changes to LEFT.
     * @param transY  acceleration force along y-axis, If the transY parameter is positive and
     *                greater than {@link #ACC_TRESHOLD} direction changes to UP; if transY
     *                parameter is negative and smaller than negative value of
     *                {@link #ACC_TRESHOLD} direction changes to DOWN.
     */
    private void setDirection(float transX, float transY) {
        if(transX > ACC_TRESHOLD) {
            direction = Direction.UP;
        }
        else if(transX < -ACC_TRESHOLD) {
            direction = Direction.DOWN;
        }
        if(transY > ACC_TRESHOLD) {
            direction = Direction.LEFT;
        }
        else if(transY < -ACC_TRESHOLD) {
            direction = Direction.RIGHT;
        }
    }

    public Direction getDirection() {
        return direction;
    }
}

