package com.snake.main.model.cell;

import com.snake.main.model.Directions;

public class SnakeHead extends SnakePart {

    public SnakeHead(int x, int y) {
        super(x, y);
    }

    public SnakeHead(int x, int y, Directions direction){
        super(x, y, direction);
    }
    
    public SnakeHead(int x, int y, Directions direction, int position){
        super(x, y, direction, position);
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ' ' + x + ' ' + y + ' ' + direction + ' ' + 0;
    }
}
