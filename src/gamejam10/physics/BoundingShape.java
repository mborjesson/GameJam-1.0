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
public abstract class BoundingShape {

	
	
    public boolean checkCollision(BoundingShape bv) {
        if (bv instanceof AABoundingRect) {
            return checkCollision((AABoundingRect) bv);
        }
        return false;
    }

    public abstract boolean checkCollision(AABoundingRect box);

    public abstract void updatePosition(float newX, float newY);

    public abstract void movePosition(float x, float y);

    public abstract ArrayList<Tile> getTilesOccupying(Tile[][] tiles);

    public abstract ArrayList<Tile> getGroundTiles(Tile[][] tiles);
    
    public abstract void setCollisionState(boolean enabled);
    
}
