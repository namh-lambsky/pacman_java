package Map;

import Tiles.Entity.Colors;
import Tiles.Entity.Ghost;
import Tiles.Entity.Player;
import Tiles.Tile;
import Window.GamePanel;

import java.awt.*;
import java.util.HashSet;


public class PacMAP {
    GamePanel gamePanel;
    private Player player;
    HashSet<Tile> walls;
    HashSet<Ghost> ghosts;
    HashSet<Tile> points;
    final String[] tileMap = {
            "XXXXXXXXXXXXXXXXXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X                 X",
            "X XX X XXXXX X XX X",
            "X    X   X   X    X",
            "XXXX XXX X XXX XXXX",
            "___X X   r   X X___",
            "XXXX X XX_XX X XXXX",
            "____   XbpoX   ____",
            "XXXX X XXXXX X XXXX",
            "___X X   P   X X___",
            "XXXX X XXXXX X XXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X  X           X  X",
            "XX X X XXXXX X X XX",
            "X    X   X   X    X",
            "X XXXXXX X XXXXXX X",
            "X                 X",
            "XXXXXXXXXXXXXXXXXXX",
    };


    public PacMAP(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        loadMap();
    }


    public void loadMap() {
        walls = new HashSet<Tile>();
        ghosts = new HashSet<Ghost>();
        points = new HashSet<Tile>();
        for (int row = 0; row < this.gamePanel.maxRow; row++) {
            for (int col = 0; col < this.gamePanel.maxCol; col++) {
                String currentRow = tileMap[row];
                char tileChar = currentRow.charAt(col);
                int xDistance = col * this.gamePanel.tileSize;
                int yDistance = row * this.gamePanel.tileSize;
                createTile(tileChar, xDistance, yDistance);
            }
        }
    }

    private void createTile(char type, int x, int y) {
        switch (type) {
            case 'X':
                Tile wall = new Tile(x, y, gamePanel.tileSize, gamePanel.tileSize);
                walls.add(wall);
                break;
            case 'b':
                Ghost blueGhost = new Ghost(Colors.BLUE, x, y, gamePanel.tileSize, gamePanel.tileSize);
                ghosts.add(blueGhost);
                break;
            case 'r':
                Ghost redGhost = new Ghost(Colors.RED, x, y, gamePanel.tileSize, gamePanel.tileSize);
                ghosts.add(redGhost);
                break;
            case 'p':
                Ghost pinkGhost = new Ghost(Colors.PINK, x, y, gamePanel.tileSize, gamePanel.tileSize);
                ghosts.add(pinkGhost);
                break;
            case 'o':
                Ghost orangeGhost = new Ghost(Colors.ORANGE, x, y, gamePanel.tileSize, gamePanel.tileSize);
                ghosts.add(orangeGhost);
                break;
            case 'P':
                player = new Player(x, y, gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize / 8);
                break;
            case ' ':
                Tile dot = new Tile(x + 20, y + 20, 8, 8);
                points.add(dot);
                break;
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public void drawMap(Graphics2D g2) {
        for (Ghost ghost : ghosts) {
            ghost.draw(g2);
        }
        g2.setColor(Color.WHITE);
        for (Tile dot : points) {

            g2.fillRect(dot.x, dot.y, dot.width, dot.height);
        }
        g2.setColor(Color.BLUE);
        for (Tile wall : walls) {

            g2.fillRect(wall.x, wall.y, wall.width, wall.height);
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, this.gamePanel.screenWidth, this.gamePanel.screenHeight);
        drawMap(g2);
        player.draw(g2);
    }

    public HashSet<Tile> getPoints() {
        return this.points;
    }

    public HashSet<Tile> getWalls() {
        return this.walls;
    }

    public HashSet<Ghost> getGhosts() {
        return this.ghosts;
    }
}
