package com.example.sznake.fields;

import android.graphics.Color;

import androidx.annotation.NonNull;

public class GrowUpField extends BonusField {
    public GrowUpField(int x, int y) {
        super(x, y, Color.GREEN);
    }

    @NonNull
    @Override
    public String toString() {
        return "G";
    }
}
