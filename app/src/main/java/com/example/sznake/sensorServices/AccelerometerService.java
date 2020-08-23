package com.example.sznake.sensorServices;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.example.sznake.utils.Direction;

public class AccelerometerService extends SensorService {

    private static final float ACC_TRESHOLD = 1.0f;
    private Direction direction;


    public AccelerometerService(Context context) {
        super(context, Sensor.TYPE_LINEAR_ACCELERATION, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onTranslation(float[] sensorEventValues) {
        float transX = sensorEventValues[0];
        float transY = sensorEventValues[1];
        setDirection(transX, transY);
    }

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

