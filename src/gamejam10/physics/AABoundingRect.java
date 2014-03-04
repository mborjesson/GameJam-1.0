/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.physics;

import java.util.*;


/**
 *
 * @author gregof
 */
public class AABoundingRect extends BoundingShape {

    private float x;
    private float y;
    private float width;
    private float height;
    private boolean checkCollision = true;

    public AABoundingRect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean checkCollision(AABoundingRect rect) {
        if(!checkCollision)
        	return false;
    	
        return !(rect.x > this.x + width || rect.x + rect.width < this.x || rect.y > this.y + height || rect.y + rect.height < this.y);
        
    }

    public ArrayList<Tile> getTilesOccupying(Tile[][] tiles) {
        ArrayList<Tile> occupiedTiles = new ArrayList<Tile>();

        //we go from the left of the rect towards to right of the rect, making sure we round upwards to a multiple of 32 or we might miss a few tiles
        for (int i = (int) x; i <= x + width + (32 - width % 32); i += 32) {
            for (int j = (int) y; j <= y + height + (32 - height % 32); j += 32) {
            	if ((i / 32) < tiles.length && (j / 32)< tiles[0].length) {
            		occupiedTiles.add(tiles[i / 32][j / 32]);
            	}
            }
        }
        return occupiedTiles;
    }

    public ArrayList<Tile> getGroundTiles(Tile[][] tiles) {
        ArrayList<Tile> tilesUnderneath = new ArrayList<Tile>();
        int j = (int) (y + height + 1);
        
        for (int i = (int) x; i <= x + width + (32 - width % 32); i += 32) {
        	if ((i / 32) < tiles.length && (j / 32)< tiles[0].length) {
        		tilesUnderneath.add(tiles[i / 32][j / 32]);
        	}
        }

        return tilesUnderneath;
    }

    public void updatePosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
    }

    public void movePosition(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void setCollisionState(boolean enabled) {    	
    	checkCollision = enabled;
    }
    
    /**
     * @return the x
     */
    public float getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public float getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * @return the width
     */
    public float getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public float getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(float height) {
        this.height = height;
    }
    
}
