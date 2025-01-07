package GameObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Objects;

public class Char_to_Img extends GameObject {
    private BufferedImage textImage, charImage;
    private HashMap<Character,Integer> mapPositions;

    public Char_to_Img(int x, int y,char c) {
        this.x = x;
        this.y = y;
        this.width = 16;
        this.height = 16;
        loadImage();
        loadCharacter(c);
    }

    void loadImage() {
        try {
            textImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/alphabet.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadPositions();
    }

    void loadPositions() {
        mapPositions = new HashMap<>();
        int index = 0;
        for (char c = 'A'; c <= 'Z'; c++) {
            mapPositions.put(c, index++);
        }

        for (char c = '0'; c <= '9'; c++) {
            mapPositions.put(c, index++);
        }
        mapPositions.put(':', index++);
        mapPositions.put('-', index++);

    }

    public void loadCharacter(char c){
        int index = mapPositions.get(c);
        int xChar=(index*width);
        charImage=textImage.getSubimage(xChar,0,width,height);
    }


    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(charImage, x, y, null);
    }
}
