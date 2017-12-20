package com.snake.main.model;

import com.snake.main.model.cell.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    
    public ArrayList<String> getLastMoves() {
    	ArrayList<String> result = new ArrayList<String>();
    	ArrayList<String> lastMoves = this.timeMachine.getGameStates();
    	for (String line: lastMoves) {
    		result.add(line);
    	}
		return result;
    }
    
    public void recordLastMoves(ArrayList<String> moves) {
    	this.timeMachine.setGameStates(moves);
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
    
    public void writeLastMoves(File file) throws IOException {
	    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	    ArrayList<String> lastMoves = getLastMoves();
	    for (String line: lastMoves) {
	    	writer.write(line + '\n');
	    }
	    writer.close();
    }

    public void readLastMoves(File file) throws IOException {
    	BufferedReader reader = new BufferedReader(new FileReader(file));
	    ArrayList<String> newLines = new ArrayList<String>();
	    String line;
	    while ((line = reader.readLine()) != null) {
	    	newLines.add(line);
	    }
	    recordLastMoves(newLines);
    }
}
