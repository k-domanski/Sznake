package com.example.sznake.gameCore;


import com.example.sznake.utils.Direction;
import com.example.sznake.gameCore.gameFields.BlockedField;
import com.example.sznake.gameCore.gameFields.EmptyField;
import com.example.sznake.gameCore.gameFields.GameField;

import java.io.Serializable;

/**
 * Represents game board. Contains all {@link GameField} and {@link Snake}.
 */
public class GameBoard implements Serializable {
    /**
     * Number of fields along the x-axis of the board.
     */
    private int sizeX;
    /**
     * Number of fields along the y-axis of the board.
     */
    private int sizeY;
    /**
     * Collection of all the board {@link GameField}.
     */
    private GameField[][] fields;
    /**
     * {@link Snake} located on the board.
     */
    private Snake snake;

    /**
     * Creates a new GameBoard with specified number of fields along X and Y axis,
     * size of the {@link Snake} and its initial direction; with specified {@link DifficultyLevel}.
     * <p>
     * Creates a new snake and all the fields, adds snake to the game board and depending
     * on the specified difficulty level creates a different type of border around the board.
     *
     * @param sizeX                  number of fields along x-axis of the board
     * @param sizeY                  number of fields along y-axis of the board
     * @param snakeSize              initial snake length
     * @param initialSnakeDirection  the in which snake begins the movement
     * @param difficultyLevel        desired difficulty level
     */
    public GameBoard(int sizeX, int sizeY, int snakeSize, Direction initialSnakeDirection,
                     DifficultyLevel difficultyLevel) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        snake = new Snake(snakeSize, initialSnakeDirection,sizeX / 2,sizeY / 2);
        fields= new GameField[sizeX][sizeY];

        createEmptyFields();
        addSnake();
        createBorder(difficultyLevel);

    }

    /**
     * Gets existing {@link GameField} from the board.
     *
     * @param x  index along x-axis of the board
     * @param y  index along y-axis of the board
     * @return   {@link GameField} from specified position on the board
     */
    public GameField get(int x, int y) {
        if (y < 0) {
            y = sizeY - y;
        }
        if (x < 0) {
            x = sizeY - x;
        }
        y %= sizeY;
        x %= sizeX;
        return fields[x][y];
    }

    /**
     * Calculates snakes next position on the board based on direction snake
     * is currently moving in.
     *
     * @return  {@link GameField} the {@link Snake} is supposed to move to
     *          based on its current direction.
     */
    public GameField getSnakeNextLocation() {
        int snakeX = snake.get(0).getX();
        int snakeY = snake.get(0).getY();
        if (snake.getDirection() == Direction.UP) {
            if (snakeY == 0) {
                snakeY = sizeY;
            }
            return fields[snakeX][snakeY - 1];
        }
        if (snake.getDirection() == Direction.DOWN) {
            return fields[snakeX][(snakeY + 1) % sizeY];
        }
        if (snake.getDirection() == Direction.LEFT) {
            if (snakeX == 0) {
                snakeX = sizeX;
            }
            return fields[snakeX - 1][snakeY];
        }
        if (snake.getDirection() == Direction.RIGHT) {
            return fields[(snakeX + 1) % sizeX][snakeY];
        }
        return null;
    }

    /**
     * Checks if given field is on the edge of the board.
     *
     * @param field {@link GameField} that is being checked
     * @return      <code>true</code> if the given {@link GameField} is on the edge of the board,
     *              <code>false</code> otherwise
     */
    public boolean isEdge(GameField field) {
        int x = field.getX();
        int y = field.getY();
        return x == 0 || y == 0 || x == sizeX - 1 || y == sizeY - 1;
    }

    /**
     * Checks if given field belongs to the corner of the board.
     * <p>
     * Corner is not a single field, it is the edge of the boarder of specific size
     * in both directions that starts form the corner.
     *
     * @param field {@link GameField} that is being checked
     * @return      <code>true</code> if the given {@link GameField} belongs to the corner
     *              of the board, <code>false</code> otherwise
     */
    public boolean isCorner(GameField field) {
        int x = field.getX();
        int y = field.getY();
        int cornerSize = 5;
        return !((x > (sizeX / 2) + cornerSize || x < (sizeX / 2) - cornerSize)
                && (y > (sizeY / 2) + cornerSize || y < (sizeY / 2) - cornerSize));
    }

    /**
     * Creates a border around the board depending on the difficulty level.
     * <p>
     * Replaces {@link EmptyField} in specific position that depends on the game difficulty
     * with {@link BlockedField}.
     * <p>
     * <ul>
     * <li>EASY - no borders</li>
     * <li>MEDIUM - border around the corners</li>
     * <li>HARD - border around the entire board</li>
     * </ul>
     *
     * @param difficultyLevel games current difficulty level
     */
    public void createBorder(DifficultyLevel difficultyLevel) {
        for (GameField[] fields : getFields())
        {
            for (GameField field : fields)
            {
                boolean isEdge = isEdge(field);
                boolean isCorner = isCorner(field);
                if (isEdge && (difficultyLevel == DifficultyLevel.HARD
                        || (difficultyLevel == DifficultyLevel.MEDIUM && !isCorner)))
                {
                    setBlocked(field.getX(), field.getY());
                }
            }
        }
    }

    /**
     * Fills the board with {@link EmptyField}.
     */
    private void createEmptyFields() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                fields[i][j] = new EmptyField(i,j);
            }
        }
    }

    /**
     * Adds snake to the board by changing the field type.
     */
    private void addSnake() {
        for(int i = 0; i < snake.getLength(); i++) {
            int snakeX = snake.get(i).getX();
            int snakeY = snake.get(i).getY();
            fields[snakeX][snakeY] = snake.get(i);
        }
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
