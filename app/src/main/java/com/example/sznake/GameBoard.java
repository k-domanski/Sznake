package com.example.sznake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameBoard {
    private int sizeX;
    private int sizeY;
    private GameField[][] fields;
    private Snake snake;

    public GameBoard(int sizeX, int sizeY, int snakeSize) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        snake = new Snake(snakeSize,Orientation.UP,sizeX/2,sizeY/2);
        fields= new GameField[sizeX][sizeY];

        for(int i=0;i<sizeX;i++){
            for(int j=0;j<sizeY;j++){
                fields[i][j]=new EmptyField(i,j);
            }
        }

        for(int i =0;i<snake.getLength();i++){
            int snakeX=snake.get(i).getX();
            int snakeY=snake.get(i).getY();
            fields[snakeX][snakeY]=snake.get(i);
        }


    }


    public GameField get(int x, int y) {
        if(y<0){
            y=sizeY-y;
        }
        if(x<0){
            x=sizeY-x;
        }
        y%=sizeY;
        x%=sizeX;
        return fields[x][y];
    }

    public GameField getSnakeNextLocation(){
        int snakeX=snake.get(0).getX();
        int snakeY=snake.get(0).getY();
        if(snake.getOrientation()==Orientation.UP) {
            if(snakeY==0){
                snakeY=sizeY;
            }
            return fields[snakeX][snakeY-1];
        }
        if(snake.getOrientation()==Orientation.DOWN) {
            return fields[snakeX][(snakeY+1)%sizeY];
        }
        if(snake.getOrientation()==Orientation.LEFT) {
            if(snakeX==0){
                snakeX=sizeX;
            }
            return fields[snakeX-1][snakeY];
        }
        if(snake.getOrientation()==Orientation.RIGHT) {
            return fields[(snakeX+1)%sizeX][snakeY];
        }
        return null;
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
}
