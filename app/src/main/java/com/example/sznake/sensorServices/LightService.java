package com.example.sznake.sensorServices;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.provider.Settings;

/**
 * Represent the Light sensor.
 * <p>
 * Responsible for adjusting screen brightness based on light intensity
 * from monitored light sensor.
 */
public class LightService extends SensorService {

    /**
     * Set of constants representing different light intensity tresholds.
     */
    private static final float LIGHT_LOW = 0f;
    private static final float LIGHT_MEDIUM = 200f;
    private static final float LIGHT_HIGH = 1000f;

    /**
     * Represents current screen brightness value.
     */
    private int currentScreenBrightness;

    /**
     * Creates new LightService with light sensor type and game sensor delay.
     * Sets {@link #currentScreenBrightness} to devices screen brightness value.
     * <p>
     * Throws exception whenever app has no rights to change system settings.
     *
     * @param context specified {@link Context}
     * @throws Settings.SettingNotFoundException
     * @see SensorService
     */
    public LightService(Context context) throws Settings.SettingNotFoundException {
        super(context, Sensor.TYPE_LIGHT, SensorManager.SENSOR_DELAY_GAME);
        currentScreenBrightness = Settings.System.getInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS);
    }

    /**
     * Called whenever monitored sensor values changes.
     *
     * @param sensorEventValues values of monitored sensor.
     */
    @Override
    public void onTranslation(float[] sensorEventValues) {
        float valX = sensorEventValues[0];
        setCurrentScreenBrightness(valX);
    }

    /**
     * Sets {@link #currentScreenBrightness} value based on output from light sensor.
     *
     * @param value light intensity value from monitored sensor
     */
    private void setCurrentScreenBrightness(float value) {
        if (value > LIGHT_HIGH) {
            currentScreenBrightness = 255;
        }
        else if (value > LIGHT_LOW && value < LIGHT_MEDIUM) {
            currentScreenBrightness = 125;
        }
        else if (value == LIGHT_LOW) {
            currentScreenBrightness = 25;
        }

    }

    public int getCurrentScreenBrightness() {
        return currentScreenBrightness;
    }
}
