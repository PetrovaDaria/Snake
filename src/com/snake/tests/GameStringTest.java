/*
package com.snake.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.snake.main.model.Directions;
import com.snake.main.model.Game;
import com.snake.main.model.GameString;
import com.snake.main.model.cell.Apple;
import com.snake.main.model.cell.Cell;
import com.snake.main.model.cell.SnakePart;
import com.snake.main.model.cell.Wall;;

class GameStringTest {

	@Test
	void testNoMove() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		GameString gameString = new GameString();
		Game testGame = new Game(); 
		String startStatment = gameString.fieldToString(testGame);
		testGame.undoStep();
		assertEquals(startStatment, gameString.fieldToString(testGame));
	}
	
	@Test
	void testStringToCell() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		GameString gameString = new GameString();
		String testString = "Wall 1 2"; 
		Cell wall = gameString.stringToCell(testString);
		assertTrue(wall instanceof Wall);
		assertEquals(wall.getX(), 1);
	}
	
	@Test
	void testStringToAplle() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		GameString gameString = new GameString();
		String testString = "Apple 1 3 4"; 
		Cell cell = gameString.stringToCell(testString);
		Apple apple = (Apple) cell;
		assertEquals(apple.getTicks(), 4);
	}
	
	@Test
	void testStringToSnakePart() {
		GameString gameString = new GameString();
		String testString = "SnakePart 1 2 Left 3";
		Cell cell = gameString.stringToCell(testString);
		SnakePart snakePart = (SnakePart) cell;
		assertEquals(snakePart.getDirection(), Directions.Left);
	}

}
*/
