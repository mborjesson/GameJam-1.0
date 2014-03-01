/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.physics;


/**
 *
 * @author gregof
 */
public class Tile {
    
    private int x;
    private int y;
    private boolean solid = false;
    private boolean deadly = false;
    private BoundingShape boundingShape;
    
    public Tile(int x, int y, boolean solid, boolean deadly) {
        this.x = x;
        this.y = y;
        this.solid = solid;
        this.deadly = deadly;
         boundingShape = null;
        if (solid || deadly) {
            boundingShape = new AABoundingRect(x*32,y*32,32,32);
        }
    }
 
   
    
    public int getX(){
        return x;
    }
 
    public int getY(){
        return y;
    }

    /**
     * @return the boundingShape
     */
    public BoundingShape getBoundingShape() {
        return boundingShape;
    }

    /**
     * @param boundingShape the boundingShape to set
     */
    public void setBoundingShape(BoundingShape boundingShape) {
        this.boundingShape = boundingShape;
    }

    /**
     * @return the solid
     */
    public boolean isSolid() {
        return solid;
    }

    /**
     * @param solid the solid to set
     */
    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    /**
     * @return the deadly
     */
    public boolean isDeadly() {
        return deadly;
    }

    /**
     * @param deadly the deadly to set
     */
    public void setDeadly(boolean deadly) {
        this.deadly = deadly;
    }

    
 
}
