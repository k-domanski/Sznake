package com.example.sznake;

import com.example.sznake.fields.SnakeField;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private List<SnakeField> body;
    private Direction direction;
    private boolean isGrowing;

    public Snake(int length, Direction direction, int X, int Y) {
        body = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if(direction == Direction.UP){

                body.add(new SnakeField(X, Y+i ));
            }
            else if(direction == Direction.DOWN){
                body.add(new SnakeField(X, Y-i ));
            }
            else if(direction == Direction.LEFT){
                body.add(new SnakeField(X+i, Y ));
            }
            else if(direction == Direction.RIGHT){
                body.add(new SnakeField(X-i, Y ));
            }
        }
        this.direction = direction;

    }

    public SnakeField get(int x) {
        return body.get(x);
    }

    public SnakeField getLast() {
        return body.get(body.size() - 1);
    }


    public int getLength() {
        return body.size();
    }


    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void move(SnakeField snakeField) {

        body.add(0, snakeField);
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
