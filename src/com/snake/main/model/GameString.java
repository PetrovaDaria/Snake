package com.snake.main.model;

import java.util.HashMap;
import com.snake.main.model.Directions;
import com.snake.main.model.cell.Accelerator;
import com.snake.main.model.cell.Apple;
import com.snake.main.model.cell.Cell;
import com.snake.main.model.cell.Empty;
import com.snake.main.model.cell.Retarder;
import com.snake.main.model.cell.Reverser;
import com.snake.main.model.cell.SnakeHead;
import com.snake.main.model.cell.SnakePart;
import com.snake.main.model.cell.VirtualSnakePart;
import com.snake.main.model.cell.Wall;

public class GameString {
    private HashMap<String, CellConstructor> cellConstructors;
	private static HashMap<String, SnakePartConstructor> snakePartConstructors;
	public GameString() {
		cellConstructors = new HashMap<>();
		cellConstructors.put("Wall", Wall::new);
		cellConstructors.put("Empty", Empty::new);
		cellConstructors.put("Apple", Apple::new);
		cellConstructors.put("Accelerator", Accelerator::new);
		cellConstructors.put("Retarder", Retarder::new);
		cellConstructors.put("Reverser", Reverser::new);
		snakePartConstructors = new HashMap<>();
		snakePartConstructors.put("SnakeHead", SnakeHead::new);
		snakePartConstructors.put("SnakePart", SnakePart::new);
		snakePartConstructors.put("VirtualSnakePart", VirtualSnakePart::new);
	}

    public Cell fromMapCellString(String string, int i, int j) {
        return cellConstructors.get(string).invoke(i, j);
    }
    
    public SnakePart fromMapSnakePieceString(String string, int i, int j, Directions d, int p) {
        return snakePartConstructors.get(string).invoke(i, j, d, p);
    }

    @FunctionalInterface
    private interface CellConstructor{
        Cell invoke(Integer i, Integer j);
    }
    
    @FunctionalInterface
    private interface SnakePartConstructor{
        SnakePart invoke(Integer i, Integer j, Directions d, Integer p);
    }
    
	public String fieldToString(Field field, Snake snake) {
		String result = "";
        for (int i = 0; i < field.getHeight(); i++)
        	for (int j = 0; j < field.getWidth(); j++)
        		result += field.getField()[j][i].toString() + '\n';
        result += '#';
        for (int i = 0; i < snake.getLength(); i++)
        	result += snake.getSnakeParts().get(i).toString() + '\n';
        result += String.format("#%s#%s#%s#%s", snake.getSpeed().toString(), snake.getScore(),
								snake.getTimeToNormal(), snake.getTicksMod6());
        return result;
	}
	
	public void stringToField(String stringGame, Field field) {
		String stringField = stringGame.split("#")[0];
		String[] stringFieldArray = stringField.split("\n");
		int count = 0;
		for (int i = 0; i < field.getHeight(); i++)
			for (int j = 0; j < field.getWidth(); j++) {
				field.getField()[j][i] = stringToCell(stringFieldArray[count]);	
				if (stringToCell(stringFieldArray[count]) instanceof Apple)
					field.apple = (Apple)stringToCell(stringFieldArray[count]);
				count ++;
			}
	}
	
	public void stringToSnake(String stringGame, Snake snake, Field field) {
		String stringSnake = stringGame.split("#")[1];
		String[] stringFieldArray = stringSnake.split("\n");
		SnakeHead snakeHead = (SnakeHead)snake.getSnakeParts().get(0);
		snake.getSnakeParts().clear();
		for (String stringSnakePiece: stringFieldArray) {
			SnakePart snakePart = (SnakePart) stringToCell(stringSnakePiece);
			if (snakePart instanceof SnakeHead) {
				snakeHead.setX(snakePart.getX());
				snakeHead.setY(snakePart.getY());
				field.setCellAt(snakePart.getX(), snakePart.getY(), snakeHead);
				snake.getSnakeParts().add(snakeHead);
			}
			else {
				field.getField()[snakePart.getX()][snakePart.getY()] = snakePart;
				snake.getSnakeParts().add(snakePart);
			}
		}
	}
	
	public Cell stringToCell(String stringCell) {
		String strClass = stringCell.split(" ", 0)[0];
		if (strClass.equals("SnakeHead") || strClass.equals("SnakePart") || strClass.equals("VirtualSnakePart")) {
			return fromMapSnakePieceString(strClass, Integer.parseInt(stringCell.split(" ", 0)[1]),
											Integer.parseInt(stringCell.split(" ", 0)[2]),
											Directions.strToDirections(stringCell.split(" ", 0)[3]),
											Integer.parseInt(stringCell.split(" ", 0)[4]));
		} else if (strClass.equals("Apple")) {
			Apple apple = (Apple) fromMapCellString(strClass, Integer.parseInt(stringCell.split(" ", 0)[1]),
													Integer.parseInt(stringCell.split(" ", 0)[2]));
			apple.setTicks(Integer.parseInt(stringCell.split(" ", 0)[3]));
			return apple;
		}
		return fromMapCellString(strClass, Integer.parseInt(stringCell.split(" ", 0)[1]),
									Integer.parseInt(stringCell.split(" ", 0)[2]));
		
	}
	
	public void setSnakeProperties(String stringGame, Snake snake) {
		Snake.SnakeSpeed speed = snake.stringToSpeed(stringGame.split("#", 0)[2]);
		int score = Integer.parseInt(stringGame.split("#", 0)[3]);
		int timeToNormal = Integer.parseInt(stringGame.split("#", 0)[4]);
		int tickMod6 = Integer.parseInt(stringGame.split("#", 0)[5]);
		snake.setSpeed(speed, timeToNormal);
		snake.setScore(score);
		snake.setTicksMod6(tickMod6);
	}
	/*
	public void undoStep(String stringGame, Game game) {
		this.stringToField(stringGame, game.getField());
		this.stringToSnake(stringGame, game.getSnake(), game.getField());
		this.setSnakeProperties(stringGame, game);
	}
	*/
	public void undoStep(String stringGame, Field field, Snake snake) {
		this.stringToField(stringGame, field);
		this.stringToSnake(stringGame, snake, field);
		this.setSnakeProperties(stringGame, snake);
	}
}
