package com.example.sznake.utils;

import androidx.annotation.NonNull;

import java.util.Random;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public static Direction getRandomElement() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }

    @NonNull
    @Override
    public String toString() {
        if (this.equals(UP)) {
            return "^";
        }
        if (this.equals(DOWN)) {
            return "V";
        }
        if (this.equals(LEFT)) {
            return "<";
        }
        if (this.equals(RIGHT)) {
            return ">";
        }
        return "";
    }

    public boolean isOpposite(Direction direction) {

        switch (this) {
            case UP:
                return direction == Direction.DOWN;

            case DOWN:
                return direction == Direction.UP;

            case LEFT:
                return direction == Direction.RIGHT;

            case RIGHT:
                return direction == Direction.LEFT;

            default:
                return false;
        }
    }
}
