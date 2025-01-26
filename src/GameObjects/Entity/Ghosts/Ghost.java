package GameObjects.Entity.Ghosts;

import GameObjects.Entity.Directions;
import GameObjects.Entity.Entity;
import GameObjects.Tile;
import Utilities.CollisionDetector;
import Utilities.CollisionType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static Utilities.CONSTANTS.SCREEN_COL;
import static Utilities.CONSTANTS.TILE_SIZE;

public class Ghost extends Entity {
    private static final Random RANDOM = new Random();

    public Ghost(Colors color, int x, int y, int width, int height, int speed, CollisionDetector collisionDetector) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.collisionDetector = collisionDetector;
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

    private void changeDirection(boolean isForced) {
        List<Directions> exits = getAvailableMoves();
        Directions opposite = Directions.oppositeDirection(this.direction);
        if (isForced) {
            if (exits.size() > 1) {
                exits.remove(opposite);
            }
            if (!exits.isEmpty()) {
                setDirection(exits.get(RANDOM.nextInt(exits.size())));
            }
        } else {
            if (exits.size() > 1) {
                exits.remove(opposite);
            }
            if (exits.size() > 1 && RANDOM.nextInt(100) <= 60) {
                setDirection(exits.get(RANDOM.nextInt(exits.size())));
            } else if (!exits.contains(this.direction)) {
                setDirection(exits.get(RANDOM.nextInt(exits.size())));
            }
        }

    }

    @Override
    public void portalManager() {
        if (collisionDetector.inPortalTiles(this)) {
            this.portal=true;
            if (this.x+TILE_SIZE < -TILE_SIZE){
                this.x=SCREEN_COL*TILE_SIZE;
            }
            else if (this.x+TILE_SIZE>=SCREEN_COL*TILE_SIZE+TILE_SIZE){
                this.x=-TILE_SIZE+1;
            }
        }
        else {
            this.portal=false;
        }

        if(portal){
            this.speed=speed/2;
        }
    }

    private boolean isBlocked(Tile tile) {
        return collisionDetector.collisionManager(tile, CollisionType.WALL);
    }

    private List<Directions> getAvailableMoves() {
        List<Directions> availableMoves = new ArrayList<>();
        for (Directions dir : Directions.values()) {
            Tile simulatedTile = getSimulatedTile(dir);
            if (!isBlocked(simulatedTile)) {
                availableMoves.add(dir);
            }
        }
        return availableMoves;
    }

    @Override
    public void setDirection(Directions direction) {
        this.direction = direction;
        updateVelocity();
    }

    @Override
    public void loadImages() {
    }

    @Override
    public void move() {
        if (isBlocked(getSimulatedTile(direction))) {
            changeDirection(true);
        } else {
            // Update the position
            this.x += this.velocityX;
            this.y += this.velocityY;
            if (collisionDetector.isAligned (this)) {
                changeDirection(false);
            }
            portalManager();
            System.out.println(this.x+" "+this.y);
        }
        spriteManager(2);
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = imageManager();
        g2.drawImage(image, x, y, width, height, null);
    }
}
