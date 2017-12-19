package com.snake.main.model;

import java.util.ArrayList;

public class TimeMachine {
    private ArrayList<String> gameStates;
    private GameString gameString;

    public TimeMachine(){
        gameStates = new ArrayList<>();
        gameString = new GameString();
    }

    public void addState(Field field, Snake snake){
        gameStates.add(gameString.fieldToString(field, snake));
    }

    public void setState(Field field, Snake snake){
        if (gameStates.size() > 1) {
            String currentGameString = gameStates.remove(gameStates.size() - 1);
            gameString.undoStep(currentGameString, field, snake);
        }
    }

    public String getState(){
        return gameStates.get(gameStates.size()-1);
    }
}
