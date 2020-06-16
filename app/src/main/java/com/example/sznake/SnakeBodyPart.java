package com.example.sznake;

public class SnakeBodyPart extends GameField {
    public SnakeBodyPart(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "o";
    }
}
