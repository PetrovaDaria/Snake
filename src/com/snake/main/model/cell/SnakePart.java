package com.snake.main.model.cell;

import com.snake.main.model.Directions;

public class SnakePart extends Cell {
    protected Directions direction = null;
    protected int position = -1;

    public SnakePart(int x, int y) {
        super(x, y);
    }

    public SnakePart(int x, int y, Directions direction){
        super(x, y);
        isWalkable = false;
        this.direction = direction;
    }
    
    public SnakePart(int x, int y, Directions direction, int position){
        super(x, y);
        isWalkable = false;
        this.direction = direction;
        this.position = position;
    }

    public Directions getDirection() {
        return direction;
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ' ' + x + ' ' + y + ' ' + direction + ' ' + position;
    }
}
