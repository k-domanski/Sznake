package com.example.sznake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private GameBoard gameBoard;
    int points = 0;

    public Game(int sizeX, int sizeY, int snakeSize) {
        gameBoard = new GameBoard(sizeX, sizeY, snakeSize);
    }


    public void moveSnake() {
        Snake snake = gameBoard.getSnake();
        List<Integer> snakeX = new ArrayList<Integer>();
        List<Integer> snakeY = new ArrayList<Integer>();
        for (int i = 0; i < snake.getLength(); i++) {
            snakeX.add(snake.get(i).getX());
            snakeY.add(snake.get(i).getY());

        }

        for(int i=0;i<snake.getLength();i++) {

            if(i==0) {
                if(snake.getOrientation()==Orientation.UP) {
                    gameBoard.move(snake.get(i).getX(), snake.get(i).getY(), snake.get(i).getX(), snake.get(i).getY() + 1);
                }
                if(snake.getOrientation()==Orientation.DOWN) {
                    gameBoard.move(snake.get(i).getX(), snake.get(i).getY(), snake.get(i).getX(), snake.get(i).getY() - 1);
                }
                if(snake.getOrientation()==Orientation.LEFT) {
                    gameBoard.move(snake.get(i).getX(), snake.get(i).getY(), snake.get(i).getX()+1, snake.get(i).getY());
                }
                if(snake.getOrientation()==Orientation.RIGHT) {
                    gameBoard.move(snake.get(i).getX(), snake.get(i).getY(), snake.get(i).getX()-1, snake.get(i).getY() + 1);
                }
            }
            else {
                gameBoard.move(snake.get(i).getX(), snake.get(i).getY(), snakeX.get(i-1), snakeY.get(i-1));

            }

        }


    }

    public void generateUpgrade(PowerUp upgrade) {
        Random generator = new Random();
        int x = generator.nextInt(gameBoard.getSizeX());
        int y = generator.nextInt(gameBoard.getSizeY());
        if (gameBoard.get(x, y).getClass() == EmptyField.class) {
            gameBoard.set(x, y, upgrade);
        } else {
            generateUpgrade(upgrade);
        }

    }


    public void gameLoop() throws InterruptedException {
        generateUpgrade(new GrowUp());
        Display.displayBoard(gameBoard);
        while (true) {
            if (gameBoard.getSnake().nextField().getClass() == GrowUp.class) {
                points++;
                generateUpgrade(new GrowUp());
            } else if (gameBoard.getSnake().nextField().getClass() == BlockedField.class || gameBoard.getSnake().nextField().getClass() == SnakeBodyPart.class) {
                break;
            }
            if (gameBoard.getSnake().nextField().getClass() == GrowUp.class) {
                gameBoard.getSnake().setGrowing(true);
            }

            moveSnake();

            Display.displayBoard(gameBoard);
            System.out.println();
            Thread.sleep(1000);
        }
        System.out.println(points);

    }


}
