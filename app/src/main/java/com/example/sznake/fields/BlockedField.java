package com.example.sznake.fields;

import android.graphics.Color;

import androidx.annotation.NonNull;

public class BlockedField extends GameField {
    public BlockedField(int x, int y)
    {
        super(x, y, Color.argb(255,0,255,0));
    }

    @NonNull
    @Override
    public String toString() {
        return "#";
    }
}
