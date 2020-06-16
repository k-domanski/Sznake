package com.example.sznake;

public class GameField {
    protected int X;
    protected int Y;

    public GameField(int x, int y) {
        X = x;
        Y = y;
    }

    public GameField() {
    }

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


}
