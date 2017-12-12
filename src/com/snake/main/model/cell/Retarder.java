package com.snake.main.model.cell;

import com.snake.main.model.Game;
import com.snake.main.model.Snake;

import java.lang.reflect.InvocationTargetException;

public class Retarder extends Food{
    private final static int duration = 50;

    public Retarder(int x, int y) {
        super(x, y);
    }

    @Override
    public void makeEffect(Snake snake)
            throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        snake.setSpeed(Snake.SnakeSpeed.Slow, duration);
        reincarnate(Game.getInstance().getField());
    }
}
