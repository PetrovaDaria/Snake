package com.snake.tests;

import com.snake.main.model.Field;
import com.snake.main.model.Game;
import com.snake.main.model.cell.Accelerator;
import com.snake.main.model.cell.Retarder;
import com.snake.main.model.cell.Reverser;
import com.snake.main.model.cell.SnakeHead;

import java.lang.reflect.InvocationTargetException;

public class TestHelper {
    static Retarder findRetarder(Field field) {
        for (int i = 0; i < field.getWidth(); i++)
            for (int j = 0; j < field.getHeight(); j++)
                if (field.cellAt(i, j) instanceof Retarder) {
                    return (Retarder) field.cellAt(i, j);
                }
        return null;
    }

    static Accelerator findAccelerator(Field field) {
        for (int i = 0; i < field.getWidth(); i++)
            for (int j = 0; j < field.getHeight(); j++)
                if (field.cellAt(i, j) instanceof Accelerator) {
                    return (Accelerator) field.cellAt(i, j);
                }
        return null;
    }

    static Reverser findReverser(Field field) {
        for (int i = 0; i < field.getWidth(); i++)
            for (int j = 0; j < field.getHeight(); j++)
                if (field.cellAt(i, j) instanceof Reverser) {
                    return (Reverser) field.cellAt(i, j);
                }
        return null;
    }

    static SnakeHead findSnakeHead(Field field) {
        for (int i = 0; i < field.getWidth(); i++) {
            for (int j = 0; j < field.getHeight(); j++) {
                if (field.cellAt(i, j) instanceof SnakeHead) {
                    return (SnakeHead) field.cellAt(i, j);
                }
            }
        }
        return null;
    }

    static void makeMove(Game game) throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        game.makeStep();
        game.makeStep();
        game.makeStep();
    }
}
