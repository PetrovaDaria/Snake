package com.snake.main.model;

import com.snake.main.model.cell.*;

import java.util.HashMap;

public class CellFactory {
    private static HashMap<Character, CellConstructor> cellConstructors;
    static {
        cellConstructors = new HashMap<>();
        cellConstructors.put('#', Wall::new);
        cellConstructors.put('~', Empty::new);
        cellConstructors.put('$', SnakePart::new);
        cellConstructors.put('S', SnakeHead::new);
        cellConstructors.put('a', Apple::new);
    }

    @FunctionalInterface
	public interface CellConstructor{
        Cell invoke(Integer i, Integer j);
    }
    public static Cell fromMapSymbol(char symbol, int i, int j) {
        return cellConstructors.get(symbol).invoke(i, j);
    }
}
