package com.example.sznake;

import androidx.annotation.NonNull;

public class GrowUp extends PowerUp {
    public GrowUp(int x, int y) {
        super(x, y);
    }

    public GrowUp() {
    }

    @NonNull
    @Override
    public String toString() {
        return "G";
    }
}
