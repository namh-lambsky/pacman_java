package Utilities;

import GameObjects.Collidable;

import java.util.HashSet;

public class CollisionDetector {

    private HashSet<Collidable> walls;
    private HashSet<Collidable> points;
    private HashSet<Collidable> ghosts;

    public CollisionDetector() {
    }

    public void setHashSet(HashSet<Collidable> walls, HashSet<Collidable> points, HashSet<Collidable> ghosts) {
        this.walls = walls;
        this.points = points;
        this.ghosts = ghosts;
    }

    public boolean collisionManager(Collidable tile, CollisionType collisionType) {
        return switch (collisionType) {
            case WALL -> collisionWithHashSet(tile, walls);
            case POINT -> collisionWithPoint(tile);
            case GHOST -> collisionWithHashSet(tile, ghosts);
        };
    }

    private boolean collisionWithHashSet(Collidable tile1, HashSet<Collidable> set) {
        for (Collidable tile : set) {
            if (collision(tile1, tile)) {
                return true;
            }
        }
        return false;
    }

    private boolean collisionWithPoint(Collidable tile1) {
        for (Collidable tile : points) {
            if (collision(tile1, tile)) {
                points.remove(tile);
                return true;
            }
        }
        return false;
    }

    private boolean collision(Collidable tileA, Collidable tileB) {
        return tileA.getX() < tileB.getX() + tileB.getWidth() &&
                tileA.getX() + tileA.getWidth() > tileB.getX() &&
                tileA.getY() < tileB.getY() + tileB.getHeight() &&
                tileA.getY() + tileA.getHeight() > tileB.getY();
    }
}
