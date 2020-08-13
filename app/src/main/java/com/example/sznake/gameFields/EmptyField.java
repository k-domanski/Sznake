package com.example.sznake.gameFields;

import android.graphics.Color;

import androidx.annotation.NonNull;

public class EmptyField extends GameField {
    public EmptyField(int x, int y) {
        super(x, y, Color.argb(255,255,255,210));
    }

    @NonNull
    @Override
    public String toString() {
        return " ";
    }
}
