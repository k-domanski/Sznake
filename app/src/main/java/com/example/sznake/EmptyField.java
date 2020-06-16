package com.example.sznake;

import androidx.annotation.NonNull;

public class EmptyField extends GameField {
    public EmptyField(int x, int y) {
        super(x, y);
    }

    @NonNull
    @Override
    public String toString() {
        return " ";
    }
}
