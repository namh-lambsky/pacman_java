package Tiles.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    public Player(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.startX = x;
        this.startY = y;
        this.direction = "";
        this.speed = speed;
        this.loadPlayerImage();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void loadPlayerImage() {
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

    public void move() {
        switch (direction) {
            case "up":
                this.setY(this.y - this.speed);
                break;
            case "down":
                this.setY(this.y + this.speed);
                break;
            case "left":
                this.setX(this.x - this.speed);
                break;
            case "right":
                this.setX(this.x + this.speed);
                break;
            default:
                break;
        }
        spriteManager(3);
        //System.out.println(x + " " + y);
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = switch (direction) {
            case "up" -> swapImage(up_list);
            case "down" -> swapImage(down_list);
            case "left" -> swapImage(left_list);
            case "right" -> swapImage(right_list);
            default -> idle;
        };
        g2.drawImage(image, x, y, width, height, null);
    }

}
