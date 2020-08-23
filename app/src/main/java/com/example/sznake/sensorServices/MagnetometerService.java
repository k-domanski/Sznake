package com.example.sznake.sensorServices;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import java.lang.Math;

/**
 * Represents the Magnetometer sensor.
 * <p>
 * Responsible for choosing random position values based on magnetometer values.
 * Positions are used to spawn {@link com.example.sznake.gameCore.gameFields.GrowUpField}
 * on the game board.
 */
public class MagnetometerService extends SensorService {
    /**
     * Position on the x-axis.
     */
    int m_randX = 0;
    /**
     * Position on the y-axis.
     */
    int m_randY = 0;
    /**
     * Screen width in pixels.
     */
    int m_screenWidth;
    /**
     * Screen height in pixels.
     */
    int m_screenHeight;

    /**
     * Creates new MagnetometerService with specified screen width and screen height;
     * sensor type is magnetic field and sensor delay normal.
     *
     * @param context       specified {@link Context}
     * @param screenWidth   screen width in pixels
     * @param screenHeight  screen height in pixels
     */
    public MagnetometerService(Context context, int screenWidth, int screenHeight) {
        super(context, Sensor.TYPE_MAGNETIC_FIELD, SensorManager.SENSOR_DELAY_NORMAL);
        m_screenHeight = screenHeight;
        m_screenWidth = screenWidth;
    }

    /**
     * Sets random position along x and y axis.
     * <p>
     * Based on magnetometer's values of ambient magnetic field in x and y axis and
     * current screen width and height.
     *
     * @param sensorEventValues values of monitored sensor.
     */
    @Override
    public void onTranslation(float[] sensorEventValues) {
        m_randX = (100 * Math.abs(Math.round(sensorEventValues[0]))) % m_screenWidth;
        m_randY = (100 * Math.abs(Math.round(sensorEventValues[1]))) % m_screenHeight;
    }

    public int getRandX() {
        return m_randX;
    }

    public int getRandY() {
        return m_randY;
    }
}
