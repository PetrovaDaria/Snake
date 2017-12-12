package com.snake.tests;

import com.snake.main.model.Directions;
import com.snake.main.model.cell.Empty;
import com.snake.main.model.cell.SnakeHead;
import com.snake.main.model.cell.SnakePart;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.snake.main.model.FieldGenerator;
import com.snake.main.model.Field;
import com.snake.main.model.cell.Cell;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FieldGeneratorTest {
    static FieldGenerator generator;

    @BeforeAll
    static void initField() {
        generator = FieldGenerator.getInstance();
    }

    @Test
    void emptySizeSets() throws Exception{
        int width = 100;
        int height = 100;
        Field actualField = generator.generateEmpty(height, width);
        assertEquals(height, actualField.getHeight());
        assertEquals(width, actualField.getWidth());
    }

    @Test
    void createEmpty() throws Exception {
        int width = 25;
        int height = 30;
        int expectedEmptyCellsCount = width * height - 3;
        int actualEmptyCellsCount = 0;
        Field field = generator.generateEmpty(width, height);
        for (int i = 0; i < field.getWidth(); i++) {
            for (int j = 0; j < field.getHeight(); j++) {
                Cell cell = field.cellAt(i, j);
                if (cell instanceof Empty) {
                    actualEmptyCellsCount++;
                }
            }
        }
        assertEquals(expectedEmptyCellsCount, actualEmptyCellsCount);
    }

    @Test
    void snakeInEmpty() throws Exception {
        Field field = generator.generateEmpty();
        testSnake(field);
    }

    @Test
    void snakeInMaze() throws Exception {
        Field field = generator.generateMaze();
        testSnake(field);
    }

    private void testSnake(Field field) {
        int snakePartsCount = 0;
        int snakeHeadsCount = 0;
        for (int i = 0; i < field.getWidth(); i++) {
            for (int j = 0; j < field.getHeight(); j++) {
                Cell cell = field.cellAt(i, j);
                if (cell instanceof SnakePart) {
                    snakePartsCount++;
                }
                if (cell instanceof SnakeHead) {
                    snakeHeadsCount++;
                }
            }
        }
        assertEquals(3, snakePartsCount);
        assertEquals(1, snakeHeadsCount);
    }

    @Test
    void emptyCellBehindSnakeInEmpty() throws Exception {
        Field field = generator.generateEmpty();
        emptyCellBehindSnake(field);
    }

    @Test
    void emptyCellBehindSnakeInMaze(){
        Field field = generator.generateMaze();
        emptyCellBehindSnake(field);
    }

    private void emptyCellBehindSnake(Field field){
        SnakeHead head = new SnakeHead(0,0);
        for (int i = 0; i < field.getWidth(); i++) {
            for (int j = 0; j < field.getHeight(); j++) {
                Cell cell = field.cellAt(i, j);
                if (cell instanceof SnakeHead) {
                    head = (SnakeHead)cell;
                    break;
                }
            }
        }
        for (Directions dir : Directions.values()){
            Cell neighbour = field.getNeighbor(head, dir);
            if (neighbour instanceof SnakePart) {
                Directions oppDir = dir.opposite();
                Cell oppNeighbour = field.getNeighbor(head, oppDir);
                assertTrue(oppNeighbour instanceof Empty);
            }
        }
    }

    @Test
    void snakeIntegrity() {
        Field field = generator.generateMaze();
        SnakeHead head = new SnakeHead(0, 0);
        ArrayList<SnakePart> passed = new ArrayList<>();
        for (int i = 0; i < field.getWidth(); i++) {
            for (int j = 0; j < field.getHeight(); j++) {
                Cell cell = field.cellAt(i, j);
                if (cell instanceof SnakeHead) {
                    head = (SnakeHead) cell;
                    passed.add(head);
                    break;
                }
            }
        }
        SnakePart currentPart = head;
        for (int i = 0; i < 2; i++) {
            ArrayList<SnakePart> possibleNextParts = findPossibleNextParts(field, currentPart);
            int unpassedSnakePartsCount = 0;
            for (SnakePart part : possibleNextParts)
                if (!passed.contains(part)) {
                    unpassedSnakePartsCount++;
                    passed.add(part);
                    currentPart = part;
                }
            assertEquals(1, unpassedSnakePartsCount);
        }
    }

    private ArrayList<SnakePart> findPossibleNextParts(Field field, SnakePart snakePart){
        ArrayList<SnakePart> nextParts = new ArrayList<>();
        for (Directions dir : Directions.values()){
            Cell neighbour = field.getNeighbor(snakePart, dir);
            if (neighbour instanceof SnakePart)
                nextParts.add((SnakePart)neighbour);
        }
        return  nextParts;
    }
}
