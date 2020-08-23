package com.example.sznake.gameCore;


import com.example.sznake.utils.Direction;
import com.example.sznake.gameCore.gameFields.BlockedField;
import com.example.sznake.gameCore.gameFields.EmptyField;
import com.example.sznake.gameCore.gameFields.GameField;

import java.io.Serializable;

public class GameBoard implements Serializable {
    private int sizeX;
    private int sizeY;
    private GameField[][] fields;
    private Snake snake;

    public GameBoard(int sizeX, int sizeY, int snakeSize, Direction initialSnakeDirection, DifficultyLevel difficultyLevel) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        snake = new Snake(snakeSize, initialSnakeDirection,sizeX / 2,sizeY / 2);
        fields= new GameField[sizeX][sizeY];

        createEmptyFields();
        addSnake();
        createBorder(difficultyLevel);

    }

    public GameField get(int x, int y) {
        if (y < 0) {
            y = sizeY - y;
        }
        if (x < 0) {
            x = sizeY - x;
        }
        y %= sizeY;
        x %= sizeX;
        return fields[x][y];
    }

    public GameField getSnakeNextLocation() {
        int snakeX = snake.get(0).getX();
        int snakeY = snake.get(0).getY();
        if (snake.getDirection() == Direction.UP) {
            if (snakeY == 0) {
                snakeY = sizeY;
            }
            return fields[snakeX][snakeY - 1];
        }
        if (snake.getDirection() == Direction.DOWN) {
            return fields[snakeX][(snakeY + 1) % sizeY];
        }
        if (snake.getDirection() == Direction.LEFT) {
            if (snakeX == 0) {
                snakeX = sizeX;
            }
            return fields[snakeX - 1][snakeY];
        }
        if (snake.getDirection() == Direction.RIGHT) {
            return fields[(snakeX + 1) % sizeX][snakeY];
        }
        return null;
    }

    public boolean isEdge(GameField field) {
        int x = field.getX();
        int y = field.getY();
        return x == 0 || y == 0 || x == sizeX - 1 || y == sizeY - 1;
    }

    public boolean isCorner(GameField field) {
        int x = field.getX();
        int y = field.getY();
        return !((x > (sizeX / 2) + 5 || x < (sizeX / 2) - 5)
                && (y > (sizeY / 2) + 5 || y < (sizeY / 2) - 5));
    }

    private void createEmptyFields() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                fields[i][j] = new EmptyField(i,j);
            }
        }
    }

    private void addSnake() {
        for(int i = 0; i < snake.getLength(); i++) {
            int snakeX = snake.get(i).getX();
            int snakeY = snake.get(i).getY();
            fields[snakeX][snakeY] = snake.get(i);
        }
    }

    public Snake getSnake() {
        return snake;
    }

    public GameField[][] getFields() {
        return fields;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setBlocked(int x, int y)
    {
        fields[x][y] = new BlockedField(x, y);
    }

    public void createBorder(DifficultyLevel difficultyLevel) {
        for (GameField[] fields : getFields())
        {
            for (GameField field : fields)
            {
                boolean isEdge = isEdge(field);
                boolean isCorner = isCorner(field);
                if (isEdge && (difficultyLevel == DifficultyLevel.HARD
                        || (difficultyLevel == DifficultyLevel.MEDIUM && !isCorner)))
                {
                    setBlocked(field.getX(), field.getY());
                }
            }
        }
    }
}
