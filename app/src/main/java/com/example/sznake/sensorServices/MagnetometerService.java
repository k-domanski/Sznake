package com.example.sznake.sensorServices;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import java.lang.Math;

public class MagnetometerService extends SensorService {

    int m_randX = 0;
    int m_randY = 0;
    int m_screenWidth;
    int m_screenHeight;

    public MagnetometerService(Context context, int screenWidth, int screenHeight) {
        super(context, Sensor.TYPE_MAGNETIC_FIELD, SensorManager.SENSOR_DELAY_NORMAL);
        m_screenHeight = screenHeight;
        m_screenWidth = screenWidth;
    }

    @Override
    public void onTranslation(float[] sensorEventValues) {
        m_randX = (100 * Math.abs(Math.round(sensorEventValues[0])))%m_screenWidth;
        m_randY = (100 * Math.abs(Math.round(sensorEventValues[1])))%m_screenHeight;
    }

    public int getRandX() {
        return m_randX;
    }

    public int getRandY() {
        return m_randY;
    }
}
