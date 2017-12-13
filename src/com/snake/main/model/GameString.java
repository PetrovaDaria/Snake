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
    private static HashMap<String, CellConstructor> cellConstructors;
    static {
        cellConstructors = new HashMap<>();
        cellConstructors.put("Wall", Wall::new);
        cellConstructors.put("Empty", Empty::new);
        cellConstructors.put("Apple", Apple::new);
        cellConstructors.put("Accelerator", Accelerator::new);
        cellConstructors.put("Retarder", Retarder::new);
        cellConstructors.put("Reverser", Reverser::new);  
    }
    private static HashMap<String, SnakePartConstructor> snakePartConstructors;
    static {
    	snakePartConstructors = new HashMap<>();
    	snakePartConstructors.put("SnakeHead", SnakeHead::new);
    	snakePartConstructors.put("SnakePart", SnakePart::new);
    	snakePartConstructors.put("VirtualSnakePart", VirtualSnakePart::new);  
    }
    
    public static Cell fromMapCellString(String string, int i, int j) {
        return cellConstructors.get(string).invoke(i, j);
    }
    
    public static SnakePart fromMapSnakePieceString(String string, int i, int j, Directions d, int p) {
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
    
	static public String fieldToString(Game game) {
		String result = "";
        for (int i = 0; i < game.getField().getHeight(); i++) {
        	for (int j = 0; j < game.getField().getWidth(); j++) {
        		result += game.getField().getField()[j][i].toString() + '\n';
        	}
        }
        result += '#';
        for (int i = 0; i < game.getSnake().getLength(); i++) {
        	result += game.getSnake().getSnakeParts().get(i).toString() + '\n';
        }
		return result;
	}
	
	static public void stringToField(String stringGame, Field field) {
		String stringField = stringGame.split("#")[0];
		String[] stringFieldArray = stringField.split("\n");
		int count = 0;
		for (int i = 0; i < field.getHeight(); i++) {
			for (int j = 0; j < field.getWidth(); j++) {
				field.getField()[j][i] = strintToCell(stringFieldArray[count]);	
				if (strintToCell(stringFieldArray[count]) instanceof Apple) {
					field.apple = (Apple)strintToCell(stringFieldArray[count]);
				}
				count ++;
			}
		}
	}
	
	static public void stringToSnake(String stringGame, Snake snake, Field field) {
		String stringSnake = stringGame.split("#")[1];
		String[] stringFieldArray = stringSnake.split("\n");
		snake.getSnakeParts().clear();
		for (String stringSnakePiece: stringFieldArray) {
			if (strintToCell(stringSnakePiece) instanceof SnakeHead) {
				SnakeHead snakePart = (SnakeHead) strintToCell(stringSnakePiece);
				field.getField()[snakePart.getX()][snakePart.getY()] = snakePart;
				snake.getSnakeParts().add(snakePart);
			}
			else if (strintToCell(stringSnakePiece) instanceof VirtualSnakePart) {
				VirtualSnakePart snakePart = (VirtualSnakePart) strintToCell(stringSnakePiece);
				field.getField()[snakePart.getX()][snakePart.getY()] = snakePart;
				snake.getSnakeParts().add(snakePart);
			} else {
				SnakePart snakePart = (SnakePart) strintToCell(stringSnakePiece);
				field.getField()[snakePart.getX()][snakePart.getY()] = snakePart;
				snake.getSnakeParts().add(snakePart);
			}
		}
	}
	
	static private Cell strintToCell(String stringCell) {
		String strClass = stringCell.split(" ", 0)[0];
		if (strClass.equals("SnakeHead") || strClass.equals("SnakePart") || strClass.equals("VirtualSnakePart")) {
			return fromMapSnakePieceString(strClass, Integer.parseInt(stringCell.split(" ", 0)[1]), Integer.parseInt(stringCell.split(" ", 0)[2]), Directions.strToDirections(stringCell.split(" ", 0)[3]), Integer.parseInt(stringCell.split(" ", 0)[4]));
		} else if (strClass.equals("Apple")) {
			Apple apple = (Apple) fromMapCellString(strClass, Integer.parseInt(stringCell.split(" ", 0)[1]), Integer.parseInt(stringCell.split(" ", 0)[2]));
			apple.setTicks(Integer.parseInt(stringCell.split(" ", 0)[3]));
			return apple;
		}
		return fromMapCellString(strClass, Integer.parseInt(stringCell.split(" ", 0)[1]), Integer.parseInt(stringCell.split(" ", 0)[2]));
		
	}
	
	static public void undoStep(String stringGame, Field field, Snake snake) {
		GameString.stringToField(stringGame, field);
		GameString.stringToSnake(stringGame, snake, field);
	}
	
}
