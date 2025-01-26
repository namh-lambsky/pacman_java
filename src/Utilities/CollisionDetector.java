package Utilities;

import GameObjects.Collidable;
import GameObjects.Entity.Entity;

import java.util.HashSet;

import static Utilities.CONSTANTS.SCREEN_COL;
import static Utilities.CONSTANTS.TILE_SIZE;

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

    public boolean isAligned(Entity tile) {
        return (Math.abs(tile.getX() % TILE_SIZE) < 2) && (Math.abs(tile.getY() % TILE_SIZE) < 2);
    }

    public boolean collisionManager(Collidable tile, CollisionType collisionType) {
        return switch (collisionType) {
            case WALL -> collisionWithHashSet(tile, walls);
            case POINT -> collisionWithPoint(tile);
            case GHOST -> collisionWithHashSet(tile, ghosts);
        };
    }

    public boolean inPortalTiles(Entity tile) {
        //Checks if the entity is on the special tiles that will work as a portal on the map
        return ((Math.abs(tile.getX())>=-TILE_SIZE &&Math.abs(tile.getX())<=4*TILE_SIZE) || ((Math.abs(tile.getX())>=16*TILE_SIZE&&Math.abs(tile.getX())<=SCREEN_COL*TILE_SIZE))) && (Math.abs(tile.getY())>=11*TILE_SIZE && Math.abs(tile.getY())<=13*TILE_SIZE);
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
        final int tolerance = 2; // Small margin of error for alignment issues
        return tileA.getX() < tileB.getX() + tileB.getWidth() - tolerance &&
                tileA.getX() + tileA.getWidth() > tileB.getX() + tolerance &&
                tileA.getY() < tileB.getY() + tileB.getHeight() - tolerance &&
                tileA.getY() + tileA.getHeight() > tileB.getY() + tolerance;
    }
}
