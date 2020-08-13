package com.example.sznake.gameCore.gameFields;

import android.graphics.Color;

public class SnakeField extends GameField {

    public SnakeField(int x, int y) {
        super(x, y, Color.argb(255,0,102,51));
    }

    @Override
    public String toString() {
        return "o";
    }
}
