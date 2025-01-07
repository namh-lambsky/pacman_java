package GameObjects.Entity.Ghosts;

import GameObjects.Entity.Directions;
import GameObjects.Entity.Entity;
import GameObjects.Tile;
import Utilities.CollisionDetector;
import Utilities.CollisionType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Objects;

public class Ghost extends Entity {
    Colors color;
    public Ghost(Colors color, int x, int y, int width, int height, int speed, CollisionDetector collisionDetector) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.direction = Directions.NONE;
        this.speed=speed;
        this.collisionDetector=collisionDetector;
        this.color=color;
        switch (color) {
            case BLUE:
                loadImages("blue");
                break;
            case RED:
                loadImages("red");
                break;
            case PINK:
                loadImages("pink");
                break;
            case ORANGE:
                loadImages("orange");
                break;
        }
    }

    public void loadImages(String color) {
        try {
            right_1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(String.format("/ghosts/%s/%s_right_1.png", color, color))));
            right_2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(String.format("/ghosts/%s/%s_right_2.png", color, color))));
            left_1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(String.format("/ghosts/%s/%s_left_1.png", color, color))));
            left_2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(String.format("/ghosts/%s/%s_left_2.png", color, color))));
            up_1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(String.format("/ghosts/%s/%s_up_1.png", color, color))));
            up_2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(String.format("/ghosts/%s/%s_up_2.png", color, color))));
            down_1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(String.format("/ghosts/%s/%s_down_1.png", color, color))));
            down_2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(String.format("/ghosts/%s/%s_down_2.png", color, color))));
            right_list = loadAnimation(right_1, right_2);
            left_list = loadAnimation(left_1, left_2);
            up_list = loadAnimation(up_1, up_2);
            down_list = loadAnimation(down_1, down_2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadImages() {}

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(right_1, x, y, width, height, null);
    }

    @Override
    public void move() {
        System.out.println("Current: "+direction);
        Tile newTile = getSimulatedTile(direction);
        if (!collisionDetector.collisionManager(newTile, CollisionType.WALL)) {
            this.x = newTile.getX();
            this.y = newTile.getY();

        }
        else{
            System.out.println("Collision Detected");
            Directions newDirection = Directions.randomDirection();
            System.out.println("New: "+newDirection);
            this.setDirection(newDirection);
        }
        if (color==Colors.RED){
            //System.out.println("Color: "+this.color+" x:"+this.x+" y:"+this.y+" Direction:"+this.direction);
        }
    }
}
