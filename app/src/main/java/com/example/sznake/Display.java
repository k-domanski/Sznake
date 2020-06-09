package com.example.sznake;

public class Display {

    public void printField(GameField field){
        if(field.getClass() == EmptyField.class){
            System.out.print(' ');
        }
        if(field.getClass() == SnakeHead.class){
            System.out.print('O');
        }
        if(field.getClass() == SnakeTail.class){
            System.out.print('o');
        }
        if(field.getClass() == GrowUp.class){
            System.out.print('G');
        }
        if(field.getClass() == BlockedField.class){
            System.out.print('#');
        }

    }



    public void displayBoard(GameBoard gameBoard){
        for(int i=0;i<gameBoard.getSizeX()+2;i++){
            System.out.print('-');
        }
        System.out.print('\n');
        for(int i =gameBoard.getSizeY()-1;i>=0;i--){
            System.out.print('|');
            for(int j=0;j<gameBoard.getSizeX();j++){
                printField(gameBoard.get(j,i));
            }
            System.out.print('|');
            System.out.print('\n');
        }
        for(int i=0;i<gameBoard.getSizeX()+2;i++){
            System.out.print('-');
        }
        System.out.print('\n');


    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
