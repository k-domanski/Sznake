package com.example.sznake;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<SnakeBodyPart> body;
    private Direction direction;
    private boolean isGrowing;

    public Snake(int length, Direction direction, int X, int Y) {
        body = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if(direction == Direction.UP){
                body.add(new SnakeBodyPart(X, Y+i ));
            }
            else if(direction == Direction.DOWN){
                body.add(new SnakeBodyPart(X, Y-i ));
            }
            else if(direction == Direction.LEFT){
                body.add(new SnakeBodyPart(X+i, Y ));
            }
            else if(direction == Direction.RIGHT){
                body.add(new SnakeBodyPart(X-i, Y ));
            }
        }
        this.direction = direction;

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


    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
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
