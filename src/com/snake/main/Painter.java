package com.snake.main;

import com.snake.main.model.Game;
import com.snake.main.model.cell.Apple;
import com.snake.main.model.cell.Cell;
import com.snake.main.model.cell.SnakePart;

import javax.swing.*;
import java.awt.*;

public class Painter {
    private final static int CELL_SIZE = 30;
    private final static int BORDER = 0;

    private final static Color SNAKE_COLOR = new Color(45,219,22);
    private final static Color SNAKE_HEAD_COLOR = new Color(0x26AB84);
    private final static Color BACKGROUND_COLOR1 = new Color(0xD9D2FF);
    private final static Color BACKGROUND_COLOR2 = new Color(0xD3CBF0);
    private final static Color APPLE_COLOR = new Color(0xFF2141);
    private final static Color APPLE_ROTTEN_COLOR = new Color(0x3E1E0D);
    private final static Color WALL_COLOR = new Color(0x023A4F);
    private final static Color ERROR_COLOR = new Color(0xFF07E6);

    private final static Image REVERSER_IMAGE = new ImageIcon("src\\com\\snake\\assets\\reverser.png").getImage();
    private final static Image ACCELERATOR_IMAGE = new ImageIcon("src\\com\\snake\\assets\\accelerator.png").getImage();
    private final static Image RETARDER_IMAGE = new ImageIcon("src\\com\\snake\\assets\\retarder.png").getImage();


    private Game game;

    public Painter(Game game) {
        this.game = game;
    }

    public void paintSnakePart(Cell cell, Graphics2D g2) {
        drawCell(g2, cell, getSnakeColor((SnakePart) cell));
    }

    public void paintSnakeHead(Cell cell, Graphics2D g2) {
        paintSnakePart(cell, g2);
    }

    public void paintVirtualSnakePart(Cell cell, Graphics2D g2) {
        paintEmpty(cell, g2);
    }

    public void paintWall(Cell cell, Graphics2D g2) {
        drawCell(g2, cell, WALL_COLOR);
    }

    public void paintEmpty(Cell cell, Graphics2D g2) {
        drawCell(g2, cell,
                (cell.getX() + cell.getY()) % 2 == 1 ? BACKGROUND_COLOR1 : BACKGROUND_COLOR2);
    }

    public void paintApple(Cell cell, Graphics2D g2) {
        drawCell(g2, cell, getAppleColor());
    }

    public void paintDefault(Cell cell, Graphics2D g2) {
        drawCell(g2, cell, ERROR_COLOR);
    }

    public void paintReverser(Cell cell, Graphics2D g2){
        drawImage(cell, g2, REVERSER_IMAGE);
    }

    public void paintAccelerator(Cell cell, Graphics2D g2){
        drawImage(cell, g2, ACCELERATOR_IMAGE);
    }

    public void paintRetarder(Cell cell, Graphics2D g2){
        drawImage(cell, g2, RETARDER_IMAGE);
    }

    private static void drawImage(Cell cell, Graphics2D g2, Image img){
        g2.drawImage(img, CELL_SIZE*cell.getX()+BORDER, CELL_SIZE*cell.getY()+BORDER, CELL_SIZE-BORDER, CELL_SIZE-BORDER, null);
    }

    private static void drawCell(Graphics2D g2, Cell cell, Color color) {
        g2.setColor(color);
        g2.fillRect(CELL_SIZE*cell.getX()+BORDER, CELL_SIZE*cell.getY()+BORDER,
                CELL_SIZE-BORDER, CELL_SIZE-BORDER);
    }

    public Color getSnakeColor(SnakePart snakePart) {
        int position = snakePart.getPosition();
        float[] headColors = new float[3];
        float[] tailColors = new float[3];
        SNAKE_COLOR.getRGBColorComponents(tailColors);
        SNAKE_HEAD_COLOR.getRGBColorComponents(headColors);
        return new Color(
                (tailColors[0] - headColors[0]) / game.getSnake().getLength() * position + headColors[0],
                (tailColors[1] - headColors[1]) / game.getSnake().getLength() * position + headColors[1],
                (tailColors[2] - headColors[2]) / game.getSnake().getLength() * position + headColors[2]
        );
    }

    public Color getAppleColor() {
        final int ticksCount = Apple.TICKS_TO_ROT;
        float[] rottenColors = new float[3];
        float[] normalColors = new float[3];
        APPLE_COLOR.getRGBColorComponents(normalColors);
        APPLE_ROTTEN_COLOR.getRGBColorComponents(rottenColors);
        return new Color(
                (rottenColors[0] - normalColors[0]) / ticksCount * game.getTicks() + normalColors[0],
                (rottenColors[1] - normalColors[1]) / ticksCount * game.getTicks() + normalColors[1],
                (rottenColors[2] - normalColors[2]) / ticksCount * game.getTicks() + normalColors[2]
        );
    }
}
