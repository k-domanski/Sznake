package com.example.sznake.sensorServices;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.provider.Settings;

public class LightService extends SensorService {

    private static final float LIGHT_LOW = 0f;
    private static final float LIGHT_MEDIUM = 200f;
    private static final float LIGHT_HIGH = 1000f;
    private int currentScreenBrightness;

    public LightService(Context context) throws Settings.SettingNotFoundException {
        super(context, Sensor.TYPE_LIGHT, SensorManager.SENSOR_DELAY_GAME);
        currentScreenBrightness = Settings.System.getInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS);
    }

    @Override
    public void onTranslation(float[] sensorEventValues) {
        float valX = sensorEventValues[0];
        setCurrentScreenBrightness(valX);
    }

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
