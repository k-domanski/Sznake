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
        GameField nextLocation = gameBoard.getSnakeNextLocation();
        int snakeX = nextLocation.getX();
        int snakeY = nextLocation.getY();
        if(!snake.isGrowing()){
            int tailX=snake.getLast().getX();
            int tailY=snake.getLast().getY();
            gameBoard.getFields()[tailX][tailY]= new EmptyField(tailX,tailY);
        }
        SnakeBodyPart snakeBodyPart = new SnakeBodyPart(snakeX,snakeY);
        snake.move(snakeBodyPart);
        gameBoard.getFields()[snakeBodyPart.getX()][snakeBodyPart.getY()]=snakeBodyPart;
            }


    public void generateUpgrade(PowerUp upgrade) {
        Random generator = new Random();
        int x = generator.nextInt(gameBoard.getSizeX());
        int y = generator.nextInt(gameBoard.getSizeY());
        upgrade.setCoordinates(x,y);
        if (gameBoard.get(x, y).getClass() == EmptyField.class) {
            gameBoard.getFields()[x][y]=upgrade;
        } else {
            generateUpgrade(upgrade);
        }

    }


    public void gameLoop() throws InterruptedException {
        Random generator = new Random();
        generateUpgrade(new GrowUp());
        Display.displayBoard(gameBoard);
        while (true) {
            Class<? extends GameField> nextFieldType = gameBoard.getSnakeNextLocation().getClass();
            int x=generator.nextInt(2);
          /*  if(x==0){
                gameBoard.getSnake().setOrientation(Orientation.LEFT);
            }
            else {
                gameBoard.getSnake().setOrientation(Orientation.UP);
            } */
            if (nextFieldType == GrowUp.class) {
                points++;
                generateUpgrade(new GrowUp());
                gameBoard.getSnake().setGrowing(true);
            } else if (nextFieldType == BlockedField.class ||nextFieldType == SnakeBodyPart.class) {
                System.out.println(gameBoard.getSnakeNextLocation().getX());
                System.out.println(gameBoard.getSnakeNextLocation().getY());
                break;
            }
            
            moveSnake();

            Display.displayBoard(gameBoard);
            System.out.println();
            Thread.sleep(1000);
        }
        System.out.println(points);

    }


}
