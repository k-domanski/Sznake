package com.example.sznake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.example.sznake.fields.BlockedField;
import com.example.sznake.fields.EmptyField;
import com.example.sznake.fields.GameField;
import com.example.sznake.fields.GrowUpField;
import com.example.sznake.fields.SnakeField;

import java.util.Random;

public class Game {



    private GameBoard gameBoard;
    private int points = 0;
    private boolean isDead;
    private boolean failedQTE;



    private DifficultyLevel m_difficultyLevel = DifficultyLevel.MEDIUM;
    private GrowUpField upgrade;
    private QTE qte;
    private int QTEMultiplier;
    private int upgradeX;
    private int upgradeY;

    public Game(int sizeX, int sizeY, int snakeSize, Direction initialSnakeDirection) {
        gameBoard = new GameBoard(sizeX, sizeY, snakeSize, initialSnakeDirection);
        upgradeX = (int) (Math.random() * gameBoard.getSizeX());
        upgradeY = (int) (Math.random() * gameBoard.getSizeY());
        upgrade = new GrowUpField(upgradeX, upgradeY);
        QTEMultiplier=1;
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
        SnakeField snakeField = new SnakeField(snakeX, snakeY);
        snake.move(snakeField);
        gameBoard.getFields()[snakeField.getX()][snakeField.getY()] = snakeField;
    }


    public void generateUpgrade() {
        if (gameBoard.get(upgradeX, upgradeY).getClass() != EmptyField.class) {
            Random generator = new Random();
            upgradeX = generator.nextInt(gameBoard.getSizeX());
            upgradeY = generator.nextInt(gameBoard.getSizeY());
            generateUpgrade();
        }
        else
        {
            gameBoard.getFields()[upgradeX][upgradeY] = upgrade;
            upgrade.setCoordinates(upgradeX, upgradeY);
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
                x = field.getX();
                y = field.getY();
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
        if (nextFieldType == GrowUpField.class) {
            points++;
            gameBoard.getSnake().setGrowing(true);
            generateUpgrade();
        } else if (nextFieldType == BlockedField.class || nextFieldType == SnakeField.class) {
            isDead = true;
        }
        if(points!=0&&points%10==0&&!failedQTE&&qte==null){
            qte=new QTE(3000);
        }
    }
    public void checkQTE(int X,int Y){
        if(qte==null){
            return;
        }
        if(!qte.isQTEActive()){
            qte=null;
            failedQTE=true;
            return;
        }
        if(qte.checkQTE(X, Y)){
            addBonusPoints(10*QTEMultiplier-1);
            QTEMultiplier++;
            qte=null;

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

    public void setUpgradePosition(int newX, int newY)
    {
        upgradeX = newX;
        upgradeY = newY;
    }

    public void addBonusPoints(int amount){
        points += amount;
    }

    public boolean isQTEActive(){
        if(qte==null){
            return false;
        }
        else return qte.isQTEActive();
    }

    public QTE getQte() {
        return qte;
    }
}
