package com.example.sznake.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.Settings;

public class Light extends SensorBase {

    private int currentScreenBrightness;

    public Light(Context context) throws Settings.SettingNotFoundException {
        super(context, Sensor.TYPE_LIGHT, SensorManager.SENSOR_DELAY_GAME);
        currentScreenBrightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
    }

    @Override
    public void onTranslation(float[] sensorEventValues) {
        float valX = sensorEventValues[0];
        if (valX > 1000) {
            currentScreenBrightness = 255;
        }
    }

    public int getCurrentScreenBrightness() {
        return currentScreenBrightness;
    }
}
