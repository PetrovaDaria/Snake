package com.snake.main;

import com.snake.main.model.Directions;
import com.snake.main.model.Game;
import com.snake.main.model.Snake;
import com.snake.main.model.cell.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;

public class GameForm extends JPanel{

    private final static int SPEED = 75;
    private final static int CELL_SIZE = 30;
    private final static int WIDTH_SHIFT = 18;
    private final static int HEIGHT_SHIFT = 47;

    private final static Color BACKGROUND_COLOR1 = new Color(0xD9D2FF);

    private Game game;
    private int fieldWidth;
    private int fieldHeight;
    private JFrame window;
    private Timer timer;
    private Directions nextSnakeDirection;
    private Painter painter;


    public GameForm(){
        try {
            game = Game.getNewInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        nextSnakeDirection = game.getSnake().getSnakeDirection();
        fieldWidth = game.getField().getWidth();
        fieldHeight = game.getField().getHeight();
        painter = new Painter(game);
        setBackground(BACKGROUND_COLOR1);
        RepaintAction action = new RepaintAction();
        timer = new Timer(SPEED, action);
        window = new JFrame("Snake");
        ImageIcon icon = new ImageIcon("src\\com\\snake\\assets\\snake.png");
        window.setIconImage(icon.getImage());
        window.addKeyListener(new Listener());
        window.setContentPane(this);
        window.setSize(CELL_SIZE*fieldWidth+WIDTH_SHIFT, CELL_SIZE*fieldHeight+ HEIGHT_SHIFT);
        window.setLocation(50,50);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.requestFocusInWindow();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i< game.getField().getWidth(); i++) {
            for (int j = 0; j < game.getField().getHeight(); j++) {
                Cell cell = game.getField().cellAt(i, j);
                String cellType = cell.getClass().getSimpleName();
                try {
                    painter.getClass().getDeclaredMethod(
                            "paint" + cellType, Cell.class, Graphics2D.class).invoke(painter, cell, g2);
                } catch (NoSuchMethodException e) {
                    painter.paintDefault(cell, g2);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class RepaintAction implements ActionListener{
        public void actionPerformed(ActionEvent evt) {
            game.getSnake().tryChangeHeadDirection(nextSnakeDirection);
            try {
                game.makeStep();
            } catch (Exception e) {
                e.printStackTrace();
            }
            window.setTitle("Score: " + game.getScore());
            if (game.isOver()) {
                JOptionPane.showMessageDialog(null,
                        "YOUR SNAKE IS DEAD\nSHAMEFUL DISPLAY\n YOUR SCORE IS: "+game.getScore(), "EPIC FAIL", JOptionPane.ERROR_MESSAGE);
                startNewGame();
            }
            repaint();
        }
    }

    private void startNewGame() {
        timer.stop();
        try {
            game = Game.getNewInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        painter = new Painter(game);
        nextSnakeDirection = game.getSnake().getSnakeDirection();
        repaint();
    }

    private class Listener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            Directions direction = null;

            if (!timer.isRunning()) {
                timer.start();
                if (key == KeyEvent.VK_F2)
                    return;
            }

            if (key == KeyEvent.VK_LEFT) {
                direction = Directions.Left;
            }
            else if (key == KeyEvent.VK_RIGHT) {
                 direction = Directions.Right;
            }
            else if (key == KeyEvent.VK_UP) {
                direction = Directions.Up;
            }
            else if (key == KeyEvent.VK_DOWN) {
                direction = Directions.Down;
            }
            else if (key == KeyEvent.VK_F1) {
                startNewGame();
                return;
            }
            else if (key == KeyEvent.VK_F2) {
                timer.stop();
                return;
            }
            else if (key == KeyEvent.VK_ESCAPE) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }
            else if (key == KeyEvent.VK_CONTROL){
                timer.stop();
                int count = 0;
                if (game.getSnake().getSpeed() == Snake.SnakeSpeed.Normal)
                    count = 3;
                else if (game.getSnake().getSpeed() == Snake.SnakeSpeed.Slow)
                    count = 6;
                else if (game.getSnake().getSpeed() == Snake.SnakeSpeed.Fast)
                    count = 2;
                for (int i=0; i<count; i++)
                    game.undoStep();
                repaint();
                return;
            }
            if (direction == null)
                return;
            nextSnakeDirection = direction;
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

}

