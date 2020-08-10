package com.example.sznake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {


    private GameBoard gameBoard;
    private int points = 0;
    private boolean isDead;
    private DifficultyLevel m_difficultyLevel = DifficultyLevel.MEDIUM;
    private GrowUp upgrade = new GrowUp();

    public Game(int sizeX, int sizeY, int snakeSize) {
        gameBoard = new GameBoard(sizeX, sizeY, snakeSize);
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public boolean isDead() {
        return isDead;
    }

    public void moveSnake() {
        Snake snake = gameBoard.getSnake();
        GameField nextLocation = gameBoard.getSnakeNextLocation();
        int snakeX = nextLocation.getX();
        int snakeY = nextLocation.getY();
        if (!snake.isGrowing()) {
            int tailX = snake.getLast().getX();
            int tailY = snake.getLast().getY();
            gameBoard.getFields()[tailX][tailY] = new EmptyField(tailX, tailY);
        }
        SnakeBodyPart snakeBodyPart = new SnakeBodyPart(snakeX, snakeY);
        snake.move(snakeBodyPart);
        gameBoard.getFields()[snakeBodyPart.getX()][snakeBodyPart.getY()] = snakeBodyPart;
    }


    public void generateUpgrade() {
        Random generator = new Random();
        int x = generator.nextInt(gameBoard.getSizeX());
        int y = generator.nextInt(gameBoard.getSizeY());
        upgrade.setCoordinates(x, y);
        if (gameBoard.get(x, y).getClass() == EmptyField.class) {
            gameBoard.getFields()[x][y] = upgrade;
        } else {
            generateUpgrade();
        }
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        m_difficultyLevel = difficultyLevel;
    }

    public void createBorder() {
        int sizeX = gameBoard.getSizeX();
        int sizeY = gameBoard.getSizeY();
        int x;
        int y;
        for (GameField[] fields : gameBoard.getFields())
        {
            for (GameField field : fields)
            {
                x = field.X;
                y = field.Y;
                boolean isEdge = (x == 0 || y == 0 || x == sizeX - 1 || y == sizeY - 1);
                boolean isCorner = !((x > (sizeX / 2) + 5 || x < (sizeX / 2) - 5) && (y > (sizeY / 2) + 5 || y < (sizeY / 2) - 5));
                if (isEdge && (m_difficultyLevel == DifficultyLevel.HARD || (m_difficultyLevel == DifficultyLevel.MEDIUM && !isCorner)))
                {
                    gameBoard.setBlocked(x, y);
                }
            }
        }
    }

    public void update() {
        Class<? extends GameField> nextFieldType = gameBoard.getSnakeNextLocation().getClass();
        moveSnake();
        if (nextFieldType == GrowUp.class) {
            points++;
            gameBoard.getSnake().setGrowing(true);
            generateUpgrade();
        } else if (nextFieldType == BlockedField.class || nextFieldType == SnakeBodyPart.class) {
            isDead = true;
        }
    }

    public void draw(Canvas canvas, SurfaceHolder surfaceHolder, Paint paint, int blockSize) {

        canvas.drawColor(Color.argb(255, 26, 128, 182));

        for (GameField[] rows : gameBoard.getFields()) {
            for (GameField field : rows) {
                field.draw(canvas, surfaceHolder, paint, blockSize);
            }
        }
    }

    public int getPoints() {
        return points;
    }

    public void setUpgradeColor(int color)
    {
        upgrade.setColor(color);
    }
}
