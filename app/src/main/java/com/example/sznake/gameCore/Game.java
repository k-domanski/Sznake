package com.example.sznake.gameCore;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.sznake.activities.MainActivity;
import com.example.sznake.utils.Direction;
import com.example.sznake.gameCore.gameFields.BlockedField;
import com.example.sznake.gameCore.gameFields.EmptyField;
import com.example.sznake.gameCore.gameFields.GameField;
import com.example.sznake.gameCore.gameFields.GrowUpField;
import com.example.sznake.gameCore.gameFields.SnakeField;

import java.io.Serializable;
import java.util.Random;

/**
 * Is a class managing game logic
 */
public class Game implements Serializable {
    private GameBoard gameBoard;
    private int points = 0;
    private boolean isDead;
    private boolean failedQTE;
    private DifficultyLevel m_difficultyLevel;
    private GrowUpField upgrade;
    private QTE qte;
    private int QTEMultiplier;
    private int upgradeX;
    private int upgradeY;

    /**
     * Creates a new Game with specified dimensions, snake length and
     * initial direction the snake is facing.
     *
     * @param sizeX                 amount of fields along X axis the gameBoard is going to have
     * @param sizeY                 amount of fields along Y axis the gameBoard is going to have
     * @param snakeSize             amount of fields the snake is initially consisting of
     * @param initialSnakeDirection decides whether snake begins game facing up, down,
     *                              left or right
     */
    public Game(int sizeX, int sizeY, int snakeSize, Direction initialSnakeDirection,DifficultyLevel difficultyLevel) {
        m_difficultyLevel=difficultyLevel;
        gameBoard = new GameBoard(sizeX, sizeY, snakeSize, initialSnakeDirection,difficultyLevel);
        upgradeX = (int) (Math.random() * gameBoard.getSizeX());
        upgradeY = (int) (Math.random() * gameBoard.getSizeY());
        upgrade = new GrowUpField(upgradeX, upgradeY);
        QTEMultiplier = 1;

        generateUpgrade();
    }

    /**
     * Performs movement of {@link Snake}.
     * <p>
     * Replaces the {@link GameField} next to the snake in the direction that snake is facing
     * with {@link SnakeField} and replaces last of snakeFields with {@link EmptyField},
     * unless value of {@link Snake}'s isGrowing() method  is equal to true.
     */
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

    /**
     * Generates upgrade on the board.
     * <p>
     * Checks if field located under current upgradeX and upgradeY values is {@link EmptyField},
     * if so, then it changes it to {@link GrowUpField}, otherwise it generates new random values
     * for upgradeX and upgradeY fields, and recursively calls itself.
     */
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

    /**
     *
     */
    public void update() {
        Class<? extends GameField> nextFieldType = gameBoard.getSnakeNextLocation().getClass();
        moveSnake();
        if (nextFieldType == GrowUpField.class) {
            points++;
            MainActivity.audioManager.onGrowUpPicked();
            gameBoard.getSnake().setGrowing(true);
            generateUpgrade();

        } else if (nextFieldType == BlockedField.class || nextFieldType == SnakeField.class) {
            isDead = true;
        }
        if (shouldTriggerQTE()) {
            qte=new QTE(3000);
        }
    }

    /**
     * Increases points value by given amount.
     *
     * @param amount by which points will be increased.
     */
    public void addBonusPoints(int amount) {
        points += amount;
    }

    private boolean shouldTriggerQTE() {
        return points != 0 && points % 10 == 0 && !failedQTE && qte == null;
    }

    /**
     * Verifies if {@link QTE} is active, and if the move required to finish it has been done,
     * or if it has expired and needs to be deleted.
     *
     * @param direction current acceleration in specified direction
     */
    public void checkQTE(Direction direction){
        if (qte == null) {
            return;
        }
        if (!qte.isQTEActive()) {
            qte = null;
            failedQTE = true;
            return;
        }
        if (qte.checkQTE(direction)) {
            addBonusPoints(10 * QTEMultiplier - 1);
            QTEMultiplier++;
            qte = null;
        }
    }

    /**
     * Returns activity status of {@link QTE}.
     *
     * @return  true if QTE is still waiting to be completed,
     *          false if it has expired or doesn't exist
     */
    public boolean isQTEActive() {
        if (qte == null) {
            return false;
        }
        else return qte.isQTEActive();
    }

    public void draw(Canvas canvas, Paint paint, int blockSize) {

        canvas.drawColor(Color.argb(255, 26, 128, 182));

        for (GameField[] rows : gameBoard.getFields()) {
            for (GameField field : rows) {
                field.draw(canvas, paint, blockSize);
            }
        }
    }

    /**
     * Returns amount of points gathered during game.
     *
     * @return  points value
     */
    public int getPoints() {
        return points;
    }

    /**
     * Returns the object containing list of {@link GameField}s of current game.
     *
     * @return  {@link GameBoard} object of current game
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Checks if {@link Snake} died.
     *
     * @return true if {@link Snake} object hit an obstacle, otherwise false
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     *
     * @return
     */
    public QTE getQte() {
        return qte;
    }

    public void setUpgradeColor(int color)
    {
        upgrade.setColor(color);
    }

    /**
     *
     * @param newX
     * @param newY
     */
    public void setUpgradePosition(int newX, int newY)
    {
        upgradeX = newX;
        upgradeY = newY;
    }

    /**
     *
     * @param difficultyLevel
     */
    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        m_difficultyLevel = difficultyLevel;
    }

}
