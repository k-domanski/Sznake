package com.example.sznake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Snake {
    private List<SnakeBodyPart> body;
    private Orientation orientation;
    private boolean isGrowing;

    public Snake(int length, Orientation orientation, int X, int Y) {
        body = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            body.add(new SnakeBodyPart(X, Y + i));
        }
        this.orientation = orientation;

    }

    public SnakeBodyPart get(int x) {
        return body.get(x);
    }

    public SnakeBodyPart getLast() {
        return body.get(body.size() - 1);
    }


    public int getLength() {
        return body.size();
    }


    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void move(SnakeBodyPart snakeBodyPart) {

        body.add(0, snakeBodyPart);
        if (!isGrowing) {
            body.remove(body.size() - 1);
        }
        isGrowing = false;

    }

    public boolean isGrowing() {
        return isGrowing;
    }

    public void setGrowing(boolean growing) {
        isGrowing = growing;
    }


}
