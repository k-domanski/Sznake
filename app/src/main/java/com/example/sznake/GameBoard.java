package com.example.sznake;

import com.example.sznake.fields.BlockedField;
import com.example.sznake.fields.EmptyField;
import com.example.sznake.fields.GameField;

public class GameBoard {
    private int sizeX;
    private int sizeY;
    private GameField[][] fields;
    private Snake snake;

    public GameBoard(int sizeX, int sizeY, int snakeSize, Direction initialSnakeDirection) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        snake = new Snake(snakeSize, initialSnakeDirection,sizeX/2,sizeY/2);
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
        if(snake.getDirection()== Direction.UP) {
            if(snakeY==0){
                snakeY=sizeY;
            }
            return fields[snakeX][snakeY-1];
        }
        if(snake.getDirection()== Direction.DOWN) {
            return fields[snakeX][(snakeY+1)%sizeY];
        }
        if(snake.getDirection()== Direction.LEFT) {
            if(snakeX==0){
                snakeX=sizeX;
            }
            return fields[snakeX-1][snakeY];
        }
        if(snake.getDirection()== Direction.RIGHT) {
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

    public void setBlocked(int x, int y)
    {
        fields[x][y] = new BlockedField(x, y);
    }
}
