package com.snake.tests;

import com.snake.main.model.Game;
import com.snake.main.model.Snake;
import com.snake.main.model.Vector;
import com.snake.main.model.cell.Retarder;
import com.snake.main.model.cell.SnakeHead;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.InvocationTargetException;

public class RetarderTest {
    private Game game;

    void putSnakeAndItem() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        game = Game.getNewInstance();
        Retarder retarder = TestHelper.findRetarder(game.getField());
        SnakeHead head = TestHelper.findSnakeHead(game.getField());
        game.getField().removeFood(retarder);
        Vector dir = head.getDirection().getVector();
        int x = (head.getX() + dir.getX()) % game.getField().getWidth();
        int y = (head.getY() + dir.getY()) % game.getField().getHeight();
        game.getField().setCellAt(x, y, new Retarder(x, y));
    }

    @Test
    void testRetarderAppearsFirstTime() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        Game game = Game.getNewInstance();
        Retarder ret = TestHelper.findRetarder(game.getField());
        assertNotNull(ret);
    }

    @Test
    void testSpeedDecrease() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        putSnakeAndItem();
        TestHelper.makeMove(game);
        assertEquals(Snake.SnakeSpeed.Slow, game.getSnake().getSpeed());
    }

    @Test
    void testRetarderAppearsAfterEating() throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        putSnakeAndItem();
        TestHelper.makeMove(game);
        Retarder ret = TestHelper.findRetarder(game.getField());
        assertNotNull(ret);
    }
}
