package com.snake.main.model.cell;

import com.snake.main.model.Field;
import com.snake.main.model.Game;
import com.snake.main.model.Snake;

import java.lang.reflect.InvocationTargetException;

public class Apple extends Food {
    private int ticks = 0;
    public final static int TICKS_TO_ROT = 100;
    public Apple(int x, int y) {
        super(x, y);
    }

    @Override
    public void makeEffect(Snake snake)
            throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        snake.addPart();
        snake.changeScore(1);
        snake.changeEatenApples(1);
        reincarnate(Game.getInstance().getField());
    }

    public void fade(Snake snake)
            throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        snake.changeScore(-1);
        Field field = Game.getInstance().getField();
        field.removeFood(this);
        field.addFood(Apple.class);
    }

    public int getTicks() {
        return ticks;
    }
    
    public void setTicks(int value) {
        this.ticks = value;
    }

    public void increaseTicks() throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        ticks++;
        if (ticks >= TICKS_TO_ROT) {
            fade(Game.getInstance().getSnake());
        }
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ' ' + x + ' ' + y + ' ' + ticks;
    }
}
