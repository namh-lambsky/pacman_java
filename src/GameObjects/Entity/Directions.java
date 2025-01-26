package GameObjects.Entity;

import java.util.List;
import java.util.Random;

public enum Directions {
    UP, DOWN, LEFT, RIGHT;

    private static final Random RANDOM = new Random();

    public static Directions randomDirection() {
        /*Get a random direction except NONE(This direction is only used for
        pacman initialization)*/
        Directions[] directions = values();
        int randomIndex;
        randomIndex = RANDOM.nextInt(directions.length - 1); // Generate index within valid range (excluding NONE)
        return directions[randomIndex];
    }

    public static Directions randomDirection(List<Directions> directions) {
        return directions.get(RANDOM.nextInt(directions.size()));
    }

    public static Directions oppositeDirection(Directions direction) {
        return switch (direction) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }

}