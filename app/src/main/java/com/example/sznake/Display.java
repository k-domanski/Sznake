package com.example.sznake;

public class Display {

    public void printField(GameField field){
        System.out.print(field);
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
