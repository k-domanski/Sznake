package com.example.sznake.gameFields;

import android.graphics.Color;

import androidx.annotation.NonNull;

public class BlockedField extends GameField {
    public BlockedField(int x, int y)
    {
        super(x, y, Color.BLACK);
    }

    @NonNull
    @Override
    public String toString() {
        return "#";
    }
}
