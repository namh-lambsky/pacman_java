package Window;

import GameObjects.Entity.Directions;
import GameObjects.Entity.Ghosts.Ghost;
import Utilities.CollisionType;
import Utilities.DynamicNumber;
import Utilities.KeyHandler;
import GameObjects.Entity.Player;
import Map.PacMAP;

import java.awt.*;
import java.util.HashSet;

import static Utilities.CONSTANTS.*;


public class GamePanel extends javax.swing.JPanel implements Runnable {

    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    PacMAP pacmap;
    Player player;
    HashSet<Ghost> ghosts;
    DynamicNumber scoreString;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        pacmap = new PacMAP();
        scoreString=pacmap.getScoreString();
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

        if (player.collisionDetector.collisionManager(player, CollisionType.POINT)){
            scoreString.add(100);
        }

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
