package com.snake.tests;

import com.snake.main.model.Game;
import com.snake.main.model.Snake;
import com.snake.main.model.Vector;
import com.snake.main.model.cell.Accelerator;
import com.snake.main.model.cell.SnakeHead;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.InvocationTargetException;

public class AcceleratorTest {
    private Game game;

    void putSnakeAndItem() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        game = Game.getNewInstance();
        Accelerator Accelerator = TestHelper.findAccelerator(game.getField());
        SnakeHead head = TestHelper.findSnakeHead(game.getField());
        game.getField().removeFood(Accelerator);
        Vector dir = head.getDirection().getVector();
        int x = head.getX() + dir.getX();
        int y = head.getY() + dir.getY();
        game.getField().setCellAt(x, y, new Accelerator(x, y));
    }

    @Test
    void testAcceleratorAppearsFirstTime() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        Game game = Game.getNewInstance();
        Accelerator ret = TestHelper.findAccelerator(game.getField());
        assertNotNull(ret);
    }

    @Test
    void testSpeedDecrease() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        putSnakeAndItem();
        TestHelper.makeMove(game);
        assertEquals(Snake.SnakeSpeed.Fast, game.getSnake().getSpeed());
    }

    @Test
    void testAcceleratorAppearsAfterEating() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        putSnakeAndItem();
        TestHelper.makeMove(game);
        Accelerator ret = TestHelper.findAccelerator(game.getField());
        assertNotNull(ret);
    }
}
