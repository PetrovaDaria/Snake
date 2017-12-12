package com.snake.main.model;

import com.snake.main.model.cell.Cell;
import com.snake.main.model.cell.SnakeHead;
import com.snake.main.model.cell.SnakePart;

import java.util.ArrayList;

public class GameState {
    private Field field;
    private Cell[][] fieldMatrix;
    private SnakeHead snakeHead;
    private ArrayList<SnakePart> snakeParts;
    private Directions snakeToBody;
    private boolean isGameOver;
    private boolean isSnakeDead;
    private int snakeScore;
    private int snakeEatenApples;
    private Snake.SnakeSpeed snakeSpeed;
    private int snakeTimeToNormal;
    private int snakeTicksMod6;
    private Snake snake;

    public GameState(Field field, ArrayList<SnakePart> snakeParts, boolean isSnakeDead,
                     int snakeScore, int snakeEatenApples, Snake.SnakeSpeed snakeSpeed, int snakeTimeToNormal,
                     int snakeTicksMod6, boolean isGameOver){
        this.field = field;
        fieldMatrix = field.getField();
        this.snakeParts = snakeParts;
        this.snakeHead = (SnakeHead)snakeParts.get(0);
        snakeToBody = snakeHead.getDirection().opposite();
        this.isSnakeDead = isSnakeDead;
        this.snakeScore = snakeScore;
        this.snakeEatenApples = snakeEatenApples;
        this.snakeSpeed = snakeSpeed;
        this.snakeTimeToNormal = snakeTimeToNormal;
        this.snakeTicksMod6 = snakeTicksMod6;
        this.isGameOver = isGameOver;
        setSnake();
    }

    private void setSnake(){
        this.snake = new Snake(field, snakeParts, isSnakeDead, snakeScore,
                               snakeEatenApples, snakeSpeed, snakeTimeToNormal,
                               snakeTicksMod6);
    }

    public Field getField() {
        return field;
    }

    public Cell[][] getFieldMatrix(){
        return fieldMatrix;
    }

    public Snake getSnake() {
        return snake;
    }

    public boolean getIsGameOver() {
        return isGameOver;
    }
}
