package com.example.sznake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameBoard {
    private int sizeX;
    private int sizeY;
    private List<GameField> fields;
    private Snake snake;

    public GameBoard(int sizeX, int sizeY, int snakeSize) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        snake = new Snake(snakeSize,Orientation.UP);
        fields= new ArrayList<>();
        for(int i=0;i<sizeX*sizeY;i++){
            fields.add(new EmptyField());
        }
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                GameField selectedField = this.get(i, j);
                selectedField.setCoordinates(i, j);
                if (j + 1 == sizeY) {
                    selectedField.setFieldUp(this.get(i, 0));
                } else {
                    selectedField.setFieldUp(this.get(i, j + 1));
                }
                if (j == 0) {
                    selectedField.setFieldDown(this.get(i, sizeY - 1));
                } else {
                    selectedField.setFieldDown(this.get(i, j - 1));
                }
                if (i == 0) {
                    selectedField.setFieldLeft(this.get(sizeX - 1, j));
                } else {
                    selectedField.setFieldLeft(this.get(i - 1, j));
                }
                if (i + 1 == sizeY) {
                    selectedField.setFieldRight(this.get(0, j));
                } else {
                    selectedField.setFieldRight(this.get(i + 1, j));
                }
            }
        }

        set(sizeX/2,sizeY/2,snake.get(0));
        for(int i =1;i<snake.getLength();i++){
            set(snake.get(i-1).X,snake.get(i-1).Y-1,snake.get(i-1).nextBodyPart);
        }


    }

    public void set(int x, int y, GameField value) {
        GameField oldField = get(x, y);
        oldField.getFieldUp().setFieldDown(value);
        oldField.getFieldDown().setFieldUp(value);
        oldField.getFieldLeft().setFieldRight(value);
        oldField.getFieldRight().setFieldLeft(value);
        value.setFieldUp(oldField.getFieldUp());
        value.setFieldDown(oldField.getFieldUp());
        value.setFieldLeft(oldField.getFieldLeft());
        value.setFieldRight(oldField.getFieldRight());
        value.setCoordinates(x, y);
        fields.set(y * sizeX + x, value);

    }
    public void move(int FromX,int FromY,int ToX,int ToY){
        FromX%=sizeX;
        FromY%=sizeY;
        ToX%=sizeX;
        ToY%=sizeY;
        GameField movedField=get(FromX,FromY);
        GameField newField=new EmptyField();
        fields.set(ToY*sizeX+ToX,movedField);
        fields.set(FromY*sizeX+FromX,newField);

        get(ToX,ToY-1).setFieldUp(movedField);
        get(ToX,ToY+1).setFieldDown(movedField);
        get(ToX+1,ToY).setFieldLeft(movedField);
        get(ToX-1,ToY).setFieldRight(movedField);
        movedField.setFieldUp( get(ToX,ToY+1));
        movedField.setFieldDown( get(ToX,ToY-1));
        movedField.setFieldLeft( get(ToX-1,ToY));
        movedField.setFieldRight( get(ToX+1,ToY));
        movedField.setCoordinates(ToX,ToY);

        get(FromX,FromY-1).setFieldUp(newField);
        get(FromX,FromY+1).setFieldDown(newField);
        get(FromX+1,FromY).setFieldLeft(newField);
        get(FromX-1,FromY-1).setFieldRight(newField);
        newField.setFieldUp(get(FromX,FromY+1));
        newField.setFieldDown(get(FromX,FromY-1));
        newField.setFieldLeft(get(FromX-1,FromY));
        newField.setFieldRight(get(FromX+1,FromY));
        newField.setCoordinates(FromX,FromY);
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
        return fields.get(y * sizeX + x);
    }

    public Snake getSnake() {
        return snake;
    }

    public List<GameField> getFields() {
        return fields;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
