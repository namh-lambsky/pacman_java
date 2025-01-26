package Utilities;

import GameObjects.Char_to_Img;

import java.awt.*;

public class DynamicNumber {

    private String current_score;
    private Char_to_Img[] images;
    private final int initialX;
    private final int initialY;
    private final int size;


    public DynamicNumber(String initialNumber,int initialX,int initialY,int size) {
        this.current_score = initialNumber;
        this.images = new Char_to_Img[current_score.length()];
        this.initialX = initialX;
        this.initialY = initialY;
        this.size = size;
        updateNumber();
    }

    public void add(int pointsAdded){
        int new_score = Integer.parseInt(current_score)+pointsAdded;
        current_score = String.format("%07d",new_score);
        updateNumber();
    }

    private void updateNumber() {
        int tempX = initialX;
        for (int i = 0; i < current_score.length(); i++) {
            char c = current_score.charAt(i);
            images[i]=new Char_to_Img(tempX,initialY,c);
            tempX+=size;
        }
    }

    public void draw(Graphics2D g2) {
        for(Char_to_Img img:images){
            img.draw(g2);
        }
    }


}
