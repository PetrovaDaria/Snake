package com.snake.main.model.cell;

import com.snake.main.model.Game;
import com.snake.main.model.Snake;

import java.lang.reflect.InvocationTargetException;

public class Reverser extends Food {

    public Reverser(int x, int y){
        super(x, y);
    }

    @Override
    public void makeEffect(Snake snake) throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        snake.reverse();
        reincarnate(Game.getInstance().getField());
    }
}
