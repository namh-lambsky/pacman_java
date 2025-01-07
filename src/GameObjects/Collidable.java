package GameObjects;

import java.awt.*;

public interface Collidable {
    int getX();
    int getY();
    int getWidth();
    int getHeight();
    void draw(Graphics2D g2);
}
