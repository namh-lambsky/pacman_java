package Map;

import GameObjects.Char_to_Img;
import GameObjects.Collidable;
import GameObjects.Entity.Ghosts.Colors;
import GameObjects.Entity.Ghosts.Ghost;
import GameObjects.Entity.Player;
import GameObjects.Tile;
import Utilities.CollisionDetector;
import Utilities.DynamicNumber;

import java.awt.*;
import java.util.HashSet;

import static Utilities.CONSTANTS.*;


public class PacMAP {
    private Player player;
    CollisionDetector collisionDetector = new CollisionDetector();
    HashSet<Collidable> walls;
    HashSet<Collidable> ghosts;
    HashSet<Collidable> points;
    HashSet<Char_to_Img> charToImages;
    DynamicNumber scoreString;
    final String[] tileMap = {
            "__S__0______________",
            "___________________",
            "XXXXXXXXXXXXXXXXXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X                 X",
            "X XX X XXXXX X XX X",
            "X    X   X   X    X",
            "XXXX XXX X XXX XXXX",
            "___X X___r___X X___",
            "XXXX X_XX_XX_X XXXX",
            "____  _XbpoX_  ____",
            "XXXX X_XXXXX_X XXXX",
            "___X X_______X X___",
            "XXXX X XXXXX X XXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X  X     P     X  X",
            "XX X X XXXXX X X XX",
            "X    X   X   X    X",
            "X XXXXXX X XXXXXX X",
            "X                 X",
            "XXXXXXXXXXXXXXXXXXX",
            "___________________",
            "___________________",
    };


    public PacMAP() {
        loadMap();
    }


    public void loadMap() {
        walls = new HashSet<>();
        ghosts = new HashSet<>();
        points = new HashSet<>();
        charToImages =new HashSet<>();
        for (int row = 0; row < SCREEN_ROW; row++) {
            for (int col = 0; col < SCREEN_COL; col++) {
                String currentRow = tileMap[row];
                char tileChar = currentRow.charAt(col);
                int xDistance = col * TILE_SIZE;
                int yDistance = row * TILE_SIZE;
                createTile(tileChar, xDistance, yDistance);
            }
        }
        collisionDetector.setHashSet(walls,points,ghosts);
    }

    private void createTile(char type, int x, int y) {
        switch (type) {
            case 'X':
                Tile wall = new Tile(x, y, TILE_SIZE, TILE_SIZE);
                walls.add(wall);
                break;
            case 'b':
                Ghost blueGhost = new Ghost(Colors.BLUE, x, y, TILE_SIZE, TILE_SIZE,5,collisionDetector);
                //ghosts.add(blueGhost);
                break;
            case 'r':
                Ghost redGhost = new Ghost(Colors.RED, x, y, TILE_SIZE, TILE_SIZE,5,collisionDetector);
                ghosts.add(redGhost);
                break;
            case 'p':
                Ghost pinkGhost = new Ghost(Colors.PINK, x, y, TILE_SIZE, TILE_SIZE,5,collisionDetector);
                //ghosts.add(pinkGhost);
                break;
            case 'o':
                Ghost orangeGhost = new Ghost(Colors.ORANGE, x, y, TILE_SIZE, TILE_SIZE,5,collisionDetector);
                //ghosts.add(orangeGhost);
                break;
            case 'P':
                player = new Player(x, y, TILE_SIZE, TILE_SIZE, TILE_SIZE / 8,collisionDetector);
                break;
            case ' ':
                Tile dot = new Tile(x + 14, y + 14, 4, 4);
                points.add(dot);
                break;
            case 'S':
                loadStrings("SCORE:",x,y,charToImages);
                break;
            case '0':
                scoreString=new DynamicNumber("0000000",x,y+5, TILE_SIZE /2);
                break;
        }
    }

    private void loadStrings(String s, int x, int y, HashSet<Char_to_Img> hashSet){
        int tempX=x;
        for(int i=0;i<s.length();i++){
            Char_to_Img char_to_img=new Char_to_Img(tempX,y+5,s.charAt(i));
            hashSet.add(char_to_img);
            tempX+= TILE_SIZE /2;
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public DynamicNumber getScoreString() {
        return scoreString;
    }

    public HashSet<Ghost> getGhosts() {
        HashSet<Ghost> ghosts = new HashSet<>();
        for (Collidable ghost : this.ghosts) {
            ghosts.add((Ghost) ghost);
        }
        return ghosts;
    }

    public HashSet<Collidable> getPoints() {
        return points;
    }

    private void drawMap(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        for (Collidable dot : points) {
            dot.draw(g2);
        }
        g2.setColor(Color.BLUE);
        for (Collidable wall : walls) {
            wall.draw(g2);
        }
        for (Collidable ghost : ghosts) {
            ghost.draw(g2);
        }
        for(Char_to_Img images : charToImages){
            images.draw(g2);
        }
        scoreString.draw(g2);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        drawMap(g2);
        player.draw(g2);
    }



}
