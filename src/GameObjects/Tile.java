package GameObjects;

import java.awt.*;

public class Tile extends GameObject {

    public Tile(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.fillRect(x, y, width, height);
    }
}
