package com.snake.main.model;

import com.snake.main.model.cell.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    private Field field;
    private Snake snake;
    private boolean isOver;
    private ArrayList<GameState> gameStates;

    private static Game instance;

    private Game() throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        createNewLevel();
    }

    public static Game getInstance() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        if (instance==null){
            instance = new Game();
        }
        return instance;
    }

    public static Game getNewInstance() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        instance = new Game();
        return instance;
    }

    public void makeStep() throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        snake.makeMove();
        isOver = snake.isDead();
        field.apple.increaseTicks();
        gameStates.add(new GameState(field, snake.getSnakeParts(), snake.isDead(), snake.getScore(),
                                     snake.getEatenApples(), snake.getSpeed(), snake.getTimeToNormal(),
                                     snake.getTicksMod6(), isOver));
    }

    public void createNewLevel()
            throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        field = FieldGenerator.getInstance().generateMaze();
        snake = new Snake(field);
        gameStates = new ArrayList<>();
        gameStates.add(new GameState(field, snake.getSnakeParts(), snake.isDead(), snake.getScore(),
                snake.getEatenApples(), snake.getSpeed(), snake.getTimeToNormal(),
                snake.getTicksMod6(), isOver));
        addFoods();
    }

    private void addFoods()
            throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        field.addFood(Apple.class);
        field.addFood(Accelerator.class);
        field.addFood(Retarder.class);
        field.addFood(Reverser.class);
    }

    public void undoStep(){
        GameState previousGameState = gameStates.remove(gameStates.size()-1);
        this.field = previousGameState.getField();
        this.snake = previousGameState.getSnake();
        this.isOver = previousGameState.getIsGameOver();
    }

    public Field getField() {
        return field;
    }

    public Snake getSnake() {
        return snake;
    }

    public boolean isOver() {
        return isOver;
    }

    public int getTicks() {
        return field.apple.getTicks();
    }

    public int getScore() {
        return snake.getScore();
    }

    public int getEatenApples() {
        return snake.getEatenApples();
    }

}
