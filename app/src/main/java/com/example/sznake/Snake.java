package com.example.sznake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Snake {
    private List<SnakeBodyPart> body;
    private Orientation orientation;
    private boolean isGrowing;

    public Snake(int length, Orientation orientation) {
        body = new ArrayList<>();
        body.add(new SnakeHead());
        for (int i = 1; i < length; i++) {
            SnakeTail tail = new SnakeTail();
            body.add(tail);
            body.get(i - 1).nextBodyPart = tail;
            tail.previousBodyPart = body.get(i - 1);
        }
        this.orientation = orientation;

    }

    public SnakeBodyPart get(int x) {
        return body.get(x);
    }

    public void set(int x, SnakeBodyPart value) {
        body.set(x, value);
    }

    public int getLength() {
        return body.size();
    }


    public Orientation getOrientation() {
        return orientation;
    }

    public SnakeTail addTail() {
        SnakeTail tail = new SnakeTail();
        tail.previousBodyPart = body.get(getLength() - 1);
        body.get(getLength() - 1).nextBodyPart = tail;
        body.add(tail);
        isGrowing=false;
        return tail;

    }

    public boolean isGrowing() {
        return isGrowing;
    }

    public void setGrowing(boolean growing) {
        isGrowing = growing;
    }

    public GameField nextField(){
        if(orientation==Orientation.UP){
            return body.get(0).fieldUp;
        }
        if(orientation==Orientation.DOWN){
            return body.get(0).fieldDown;
        }
        if(orientation==Orientation.LEFT){
            return body.get(0).getFieldLeft();
        }
        if(orientation==Orientation.RIGHT){
            return body.get(0).getFieldRight();
        }
        return null;

    }

}
