package com.example.sznake;

public class main {
    public static void main(String[] args) throws InterruptedException {
        Game game= new Game(20,10,4);
        game.gameLoop();
    }
}
