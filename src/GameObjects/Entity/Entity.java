package GameObjects.Entity;

import GameObjects.GameObject;
import GameObjects.Tile;
import Utilities.CollisionDetector;

import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class Entity extends GameObject {
    protected int startX, startY;
    protected int speed;
    protected int velocityX, velocityY;
    protected Directions direction, prevDirection;
    public CollisionDetector collisionDetector;
    public BufferedImage idle, up_1, down_1, left_1, right_1, up_2, down_2, left_2, right_2;
    public BufferedImage[] up_list, down_list, left_list, right_list;
    private int spriteCounter = 0;
    private int sprite = 1;
    private boolean startAnim = true;
    public boolean portal = false;

    protected BufferedImage[] loadAnimation(BufferedImage... images) {
        return images;
    }

    protected void spriteManager(int spritesQuantity) {
        spriteCounter++;
        if (spriteCounter > 3) {//Interval that changes the sprite
            if (startAnim) {
                sprite++;
                if (sprite >= spritesQuantity) {
                    startAnim = false;
                }
            } else {
                sprite--;
                if (sprite <= 1) {
                    startAnim = true;
                }
            }
            spriteCounter = 0;
        }
    }

    protected BufferedImage swapImage(BufferedImage[] images) {
        return switch (sprite) {
            case 2 -> images[1];
            case 3 -> images[2];
            default -> images[0];
        };
    }

    protected BufferedImage imageManager() {
        return switch (direction) {
            case UP -> swapImage(up_list);
            case DOWN -> swapImage(down_list);
            case LEFT -> swapImage(left_list);
            case RIGHT -> swapImage(right_list);
        };
    }

    protected Tile getSimulatedTile(Directions direction) {
        int simulatedX = this.x;
        int simulatedY = this.y;

        switch (direction) {
            case UP:
                simulatedY -= speed;
                break;
            case DOWN:
                simulatedY += speed;
                break;
            case LEFT:
                simulatedX -= speed;
                break;
            case RIGHT:
                simulatedX += speed;
                break;
        }

        return new Tile(simulatedX, simulatedY, width, height);
    }

    protected void updateVelocity() {
        switch (direction) {
            case UP:
                this.velocityX = 0;
                this.velocityY = -this.speed;
                break;
            case DOWN:
                this.velocityX = 0;
                this.velocityY = this.speed;
                break;
            case LEFT:
                this.velocityX = -this.speed;
                this.velocityY = 0;
                break;
            case RIGHT:
                this.velocityX = this.speed;
                this.velocityY = 0;
                break;
            default:
                break;
        }
    }

    protected abstract void setDirection(Directions direction);

    protected abstract void loadImages();

    public abstract void draw(Graphics2D g2);

    protected abstract void move();

    protected abstract void portalManager();
}
