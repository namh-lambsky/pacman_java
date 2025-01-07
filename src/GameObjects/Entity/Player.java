package GameObjects.Entity;

import GameObjects.Tile;
import Utilities.CollisionDetector;
import Utilities.CollisionType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    private int points;

    public Player(int x, int y, int width, int height, int speed, CollisionDetector collisionDetector) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.startX = x;
        this.startY = y;
        this.direction = Directions.NONE;
        this.speed = speed;
        this.velocityX = 0;
        this.velocityY = 0;
        this.collisionDetector = collisionDetector;
        this.points=0;
        this.loadImages();
    }

    @Override
    public void loadImages() {
        try {
            idle = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/closed.png")));
            right_1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/open_right.png")));
            right_2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/fopen_right.png")));
            left_1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/open_left.png")));
            left_2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/fopen_left.png")));
            up_1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/open_up.png")));
            up_2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/fopen_up.png")));
            down_1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/open_down.png")));
            down_2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/pacman/fopen_down.png")));
            right_list = loadAnimation(idle, right_1, right_2);
            left_list = loadAnimation(idle, left_1, left_2);
            up_list = loadAnimation(idle, up_1, up_2);
            down_list = loadAnimation(idle, down_1, down_2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPoints(){
        return points;
    }

    public void addPoints(){
        this.points = this.points + 1;
    }

    private void pointManager(){
        if (collisionDetector.collisionManager(this,CollisionType.POINT)){
            addPoints();
        }
    }

    @Override
    public void move() {
        Tile newTile = getSimulatedTile(direction);
        //move player if it is not colliding with a wall
        if (!collisionDetector.collisionManager(newTile, CollisionType.WALL)) {
            this.x = newTile.getX();
            this.y = newTile.getY();
        }

        //collection manager
        pointManager();
        spriteManager(3);
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = switch (direction) {
            case UP -> swapImage(up_list);
            case DOWN -> swapImage(down_list);
            case LEFT -> swapImage(left_list);
            case RIGHT -> swapImage(right_list);
            case NONE -> idle;
        };
        g2.drawImage(image, x, y, width, height, null);
    }

}
