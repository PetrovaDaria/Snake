package com.snake.main.model;

import java.util.Random;

public enum Directions {
    Up(new Vector(0, -1)),
    Down(new Vector(0, 1)),
    Left(new Vector(-1, 0)),
    Right(new Vector(1, 0));

    private Vector vector;

    Directions(Vector vector){
        this.vector = vector;
    }

    public Vector getVector() {
        return vector;
    }

    public Directions opposite(){
        Directions direction = null;
        switch (this){
            case Up:
                direction = Directions.Down;
                break;
            case Down:
                direction = Directions.Up;
                break;
            case Left:
                direction = Directions.Right;
                break;
            case Right:
                direction = Directions.Left;
                break;
        }
        return direction;
    }
    
	public static Directions strToDirections(String string) {
		if (string == "Up") {
			return Directions.Up;
		}
		else if (string == "Left") {
			return Directions.Left;
		}
		else if (string == "Right") {
			return Directions.Right;
		}
		else{
			return Directions.Down;
		}
	}

    public boolean isOpposite(Directions other) {
        return this.opposite() == other;
    }

    public static Directions random() {
        Random rnd = new Random();
        int result = rnd.nextInt(4);
        return Directions.values()[result];
    }
}
