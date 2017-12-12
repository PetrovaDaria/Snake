package com.snake.main.model;

import com.snake.main.model.cell.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class Field {
    private Cell[][] field;
    private int height;
    private int width;
    //убрать
    public boolean hasApple = true;
    public Apple apple;

    public Field(Cell[][] field){
        this.field = field;
        width = field.length;
        height = field[0].length;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Cell[][] getField() {
        return field;
    }

    public Cell getNeighbor(Cell cell, Directions direction){
        if (direction == null)
            return cell;
        int x = (cell.getX() + direction.getVector().getX() + width) % width;
        int y = (cell.getY() + direction.getVector().getY() + height) % height;
        return field[x][y];
    }

    public ArrayList<Cell> getEmptyCells() {
        ArrayList<Cell> emptyCells = new ArrayList<Cell>();
        for (Cell[] cellLine : field) {
            for (Cell cell : cellLine) {
                if (cell instanceof Empty)
                    emptyCells.add(cell);
            }
        }
        return emptyCells;
    }

    public Cell cellAt(int i, int j) {
        return field[i][j];
    }

    public void setCellAt(int i, int j, Cell value) {
        field[i][j] = value;
    }

    public void addFood(Class<? extends Food> foodClass)
            throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        ArrayList<Cell> emptyCells = getEmptyCells();
        Random random = new Random();
        int index = random.nextInt(emptyCells.size());
        int x = emptyCells.get(index).getX();
        int y = emptyCells.get(index).getY();
        Constructor<? extends Food> constructor = foodClass.getConstructor(int.class, int.class);
        setCellAt(x, y, constructor.newInstance(x,y));
        Food newFood = (Food) cellAt(x, y);
        if (newFood instanceof Apple){
            apple = (Apple)newFood;
            hasApple = true;
        }
    }

    public void removeFood(Food toRemove) {
        int x = toRemove.getX();
        int y = toRemove.getY();
        setCellAt(x, y, new Empty(x, y));
        if (toRemove instanceof Apple){
            hasApple = false;
        }
    }
}
