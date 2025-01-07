package GameObjects.Entity;

import java.util.Random;

public enum Directions {
    UP, DOWN, LEFT, RIGHT, NONE;

    private static final Random RANDOM = new Random();

    public static Directions randomDirection() {
        /*Get a random direction except NONE(This direction is only used for
        pacman initialization)*/
        Directions[] directions = values();
        int randomIndex;
        do {
            randomIndex = RANDOM.nextInt(directions.length - 1); // Generate index within valid range (excluding NONE)
        } while (directions[randomIndex] == Directions.NONE);
        return directions[randomIndex];
    }

}