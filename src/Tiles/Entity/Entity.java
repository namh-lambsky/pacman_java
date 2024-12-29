package Tiles.Entity;

import Tiles.Tile;

import java.awt.image.BufferedImage;

public class Entity extends Tile {
    public int speed;
    public int spriteCounter = 0;
    public int sprite=1;
    String direction;
    boolean movingUp = true;
    BufferedImage idle, up_1, down_1, left_1, right_1, up_2, down_2, left_2, right_2;
    BufferedImage[] up_list, down_list, left_list, right_list;
    public BufferedImage[] loadAnimation(BufferedImage... images){
        return images;
    }

    public void spriteManager(int spritesQuantity) {
        spriteCounter++;
        if (spriteCounter > 5) {//Interval that changes the sprite
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

    public BufferedImage swapImage(BufferedImage[] images) {
        return switch (sprite) {
            case 2 -> images[1];
            case 3 -> images[2];
            default -> images[0];
        };
    }

}
