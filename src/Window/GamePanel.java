package Window;

import GameObjects.Collidable;
import GameObjects.Entity.Directions;
import GameObjects.Entity.Ghosts.Ghost;
import Utilities.KeyHandler;
import GameObjects.Entity.Player;
import Map.PacMAP;

import java.awt.*;
import java.util.HashSet;


public class GamePanel extends javax.swing.JPanel implements Runnable {
    final int originalTileSize = 16, scale = 2;
    public int tileSize = originalTileSize * scale;
    final public int screenRow = 25, screenCol = 19;//game 19cols 21rows
    public final int screenWidth = tileSize * screenCol;
    public final int screenHeight = tileSize * screenRow;
    final int FPS = 30;

    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    PacMAP pacmap;
    Player player;
    HashSet<Ghost> ghosts;
    HashSet<Collidable>points;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        pacmap = new PacMAP(this);
        loadGhosts();
        //System.out.println(screenWidth + " " + screenHeight);
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

    private void updatePlayerDirection() {
        player = pacmap.getPlayer();
        if (keyHandler.upPressed) {
            player.setDirection(Directions.UP);
        }
        if (keyHandler.downPressed) {
            player.setDirection(Directions.DOWN);
        }
        if (keyHandler.leftPressed) {
            player.setDirection(Directions.LEFT);
        }
        if (keyHandler.rightPressed) {
            player.setDirection(Directions.RIGHT);
        }
    }

    private void loadGhosts() {
        this.ghosts=pacmap.getGhosts();
        for (Ghost ghost : ghosts) {
            ghost.setDirection(Directions.randomDirection());
        }
    }

    private void moveGhosts() {
        for (Ghost ghost : new HashSet<Ghost>(ghosts)) {
            ghost.move();
        }
    }

    private void update() {
        moveGhosts();
        updatePlayerDirection();
        player.move();
        pacmap.setPlayerPointsPlaceHolder(String.valueOf(player.getPoints()));
        if(player.getPoints()==175){
            System.out.println("Game over");
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        pacmap.draw(g2);
        g2.dispose();
    }
}
