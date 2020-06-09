package com.example.sznake;

public class GameField {
    protected GameField fieldUp;
    protected GameField fieldDown;
    protected GameField fieldRight;
    protected GameField fieldLeft;
    protected int X;
    protected int Y;


    public void setCoordinates(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void setFieldUp(GameField fieldUp) {
        this.fieldUp = fieldUp;
    }

    public void setFieldDown(GameField fieldDown) {
        this.fieldDown = fieldDown;
    }

    public void setFieldRight(GameField fieldRight) {
        this.fieldRight = fieldRight;
    }

    public void setFieldLeft(GameField fieldLeft) {
        this.fieldLeft = fieldLeft;
    }

    public GameField getFieldUp() {
        return fieldUp;
    }

    public GameField getFieldDown() {
        return fieldDown;
    }

    public GameField getFieldRight() {
        return fieldRight;
    }

    public GameField getFieldLeft() {
        return fieldLeft;
    }
}
