package Window;

import Tiles.Tile;
import Utilities.KeyHandler;
import Tiles.Entity.Player;
import Map.PacMAP;

import java.awt.*;


public class GamePanel extends javax.swing.JPanel implements Runnable {
    final int originalTileSize = 16, scale = 3;
    public int tileSize = originalTileSize * scale;
    final public int maxCol = 19, maxRow = 21;
    public final int screenWidth = tileSize * maxCol;
    public final int screenHeight = tileSize * maxRow;
    final int FPS = 30;

    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    PacMAP pacmap;
    Player player;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        pacmap = new PacMAP(this);
        System.out.println(screenWidth + " " + screenHeight);
    }


    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double deltaTime = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            deltaTime += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (deltaTime >= 1) {
                update();
                repaint();
                deltaTime--;
            }
        }
    }

    private void movePlayer() {
        player = pacmap.getPlayer();
        if (keyHandler.upPressed && !checkCollisions("up")) {
            player.setDirection("up");
            player.move();
        }
        if (keyHandler.downPressed && !checkCollisions("down")) {
            player.setDirection("down");
            player.move();
        }
        if (keyHandler.leftPressed && !checkCollisions("left")) {
            player.setDirection("left");
            player.move();
        }
        if (keyHandler.rightPressed && !checkCollisions("right")) {
            player.setDirection("right");
            player.move();
        }
    }

    private void update() {
        movePlayer();
    }

    private boolean checkCollisions(String direction) {
        // Simulates future player's position
        int futureX = player.getX();
        int futureY = player.getY();
        int speed = player.getSpeed();

        switch (direction) {
            case "up" -> futureY -= speed;
            case "down" -> futureY += speed;
            case "left" -> futureX -= speed;
            case "right" -> futureX += speed;
        }

        // Temp tile to check future collisions
        Tile futurePlayer = new Tile(futureX, futureY, player.getWidth(), player.getHeight());

        // Checks collision of player with a wall
        for (Tile wall : pacmap.getWalls()) {
            if (collision(futurePlayer, wall)) {
                System.out.println("Collision detected in direction: " + direction);
                return true; // Collision detected
            }
        }
        return false; // No collision
    }

    private boolean collision(Tile tileA, Tile tileB) {
        return tileA.x < tileB.x + tileB.width &&
                tileA.x + tileA.width > tileB.x &&
                tileA.y < tileB.y + tileB.height &&
                tileA.y + tileA.height > tileB.y;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        pacmap.draw(g2);
        g2.dispose();
    }
}
