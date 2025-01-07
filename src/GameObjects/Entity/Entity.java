package GameObjects.Entity;

import GameObjects.GameObject;
import GameObjects.Tile;
import Utilities.CollisionDetector;
import Utilities.CollisionType;

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
    private boolean movingUp = true;

    public BufferedImage[] loadAnimation(BufferedImage... images) {
        return images;
    }

    void spriteManager(int spritesQuantity) {
        spriteCounter++;
        if (spriteCounter > 3) {//Interval that changes the sprite
            if (movingUp) {
                sprite++;
                if (sprite >= spritesQuantity) {
                    movingUp = false;
                }
            } else {
                sprite--;
                if (sprite <= 1) {
                    movingUp = true;
                }
            }
            spriteCounter = 0;
        }
    }

    BufferedImage swapImage(BufferedImage[] images) {
        return switch (sprite) {
            case 2 -> images[1];
            case 3 -> images[2];
            default -> images[0];
        };
    }

    public Tile getSimulatedTile(Directions direction) {
        int simulatedX = this.x;
        int simulatedY = this.y;

        switch (direction) {
            case UP:
                simulatedY -= this.speed;
                break;
            case DOWN:
                simulatedY += this.speed;
                break;
            case LEFT:
                simulatedX -= this.speed;
                break;
            case RIGHT:
                simulatedX += this.speed;
                break;
        }

        return new Tile(simulatedX, simulatedY, width, height);
    }

    public void setDirection(Directions direction) {
        this.prevDirection = this.direction;

        Tile simulatedTile = getSimulatedTile(direction);

        //If collision is true then direction will not change until collision is false
        if (!collisionDetector.collisionManager(simulatedTile, CollisionType.WALL)) {
            this.direction = direction;
        } else {
            if(this.direction == Directions.NONE) {
                this.direction = Directions.randomDirection();
            }
            else{
                this.direction = this.prevDirection;
            }
        }
        updateVelocity();
    }

    public void updateVelocity() {
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

    public abstract void loadImages();

    public abstract void draw(Graphics2D g2);

    public abstract void move();
}
