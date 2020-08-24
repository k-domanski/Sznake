package com.example.sznake.gameCore;

import com.example.sznake.utils.Direction;
import com.example.sznake.gameCore.gameFields.SnakeField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent the snake.
 * <p>
 * Snake consists of {@link SnakeField} and moves in specified direction.
 */
public class Snake implements Serializable {
    /**
     * List of Snakes body parts.
     */
    private List<SnakeField> body;
    /**
     * The {@link Direction} the snake is moving.
     */
    private Direction direction;
    /**
     * Tracks if the Snake growth.
     */
    private boolean isGrowing;

    /**
     * Creates an new Snake with specified length, direction and position on the board.
     *
     * @param length     the starting length of snake
     * @param direction  the direction in which snakes initially moves
     * @param X          initial position on the board along x-axis
     * @param Y          initial position on the board along y-axis
     */
    public Snake(int length, Direction direction, int X, int Y) {
        body = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if(direction == Direction.UP) {
                body.add(new SnakeField(X, Y + i ));
            }
            else if(direction == Direction.DOWN) {
                body.add(new SnakeField(X, Y - i ));
            }
            else if(direction == Direction.LEFT) {
                body.add(new SnakeField(X + i, Y ));
            }
            else if(direction == Direction.RIGHT) {
                body.add(new SnakeField(X - i, Y ));
            }
        }
        this.direction = direction;

    }

    /**
     * Moves Snake.
     * <p>
     * Adds a {@link SnakeField} to the beginning of Snake.
     * If Snake is not suppose to grow removes the last part of Snakes body.
     *
     * @param snakeField  new {@link SnakeField}
     */
    public void move(SnakeField snakeField) {
        body.add(0, snakeField);
        if (!isGrowing) {
            body.remove(body.size() - 1);
        }
        isGrowing = false;

    }

    /**
     * Gets the specified {@link SnakeField} from the Snakes body.
     *
     * @param x  index of the {@link SnakeField} element in the Snake body
     * @return   {@link SnakeField} from the Snake body list
     */
    public SnakeField get(int x) {
        return body.get(x);
    }

    /**
     * Returns the last Snakes body part.
     *
     * @return  the last element from the body list.
     */
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

    public boolean isGrowing() {
        return isGrowing;
    }

    public void setGrowing(boolean growing) {
        isGrowing = growing;
    }
}
