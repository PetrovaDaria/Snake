package com.snake.main.model.cell;

import com.snake.main.model.Game;
import com.snake.main.model.Snake;

import java.lang.reflect.InvocationTargetException;

public class Accelerator extends Food {
    private final static int duration = 50;

    public Accelerator(int x, int y) {
        super(x, y);
    }

    @Override
    public void makeEffect(Snake snake)
            throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        snake.setSpeed(Snake.SnakeSpeed.Fast, duration);
        reincarnate(Game.getInstance().getField());
    }
}
