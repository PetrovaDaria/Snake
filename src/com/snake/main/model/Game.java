package com.snake.main.model;

import com.snake.main.model.cell.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    private Field field;
    private Snake snake;
    private boolean isOver;
    private TimeMachine timeMachine;

    private static Game instance;

    public Game() throws NoSuchMethodException, InstantiationException,
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
        timeMachine.addState(field, snake);
    }

    public void createNewLevel()
            throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        field = FieldGenerator.getInstance().generateMaze();
        snake = new Snake(field);
        timeMachine = new TimeMachine();
        timeMachine.addState(field, snake);
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
        timeMachine.setState(field, snake);
    }

    public String getCurrentState(){
        return timeMachine.getState();
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
