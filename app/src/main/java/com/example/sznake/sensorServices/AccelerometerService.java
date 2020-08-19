package com.example.sznake.sensorServices;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;

public class AccelerometerService extends SensorService {

    private static final float ACC_TRESHOLD = 1.0f;
    int m_color = Color.GREEN;
    int x_value;
    int y_value;


    public AccelerometerService(Context context) {
        super(context, Sensor.TYPE_LINEAR_ACCELERATION, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onTranslation(float[] sensorEventValues) {
        float transX = sensorEventValues[0];
        float transY = sensorEventValues[1];
        if(transX > ACC_TRESHOLD) {
            m_color = Color.RED;
            x_value=1;
        }
        else if(transX < -ACC_TRESHOLD) {
            m_color = Color.GREEN;
            x_value=-1;
        }
        if(transY > ACC_TRESHOLD) {
            y_value=1;
        }
        else if(transY < -ACC_TRESHOLD) {
            y_value=-1;
        }
    }

    public int getColor() {
        return m_color;
    }

    public int getX_value() {
        return x_value;
    }

    public int getY_value() {
        return y_value;
    }
}

