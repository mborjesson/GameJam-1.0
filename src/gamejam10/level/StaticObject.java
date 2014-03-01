package gamejam10.level;

import gamejam10.physics.AABoundingRect;
import gamejam10.physics.BoundingShape;

/**
*
* @author gregof
*/
public abstract class StaticObject {

	 protected float x;
	 protected float y;
	 protected BoundingShape boundingShape;
	    
	  public StaticObject(float x, float y, float w, float h){
	        this.x = x;
	        this.y = y;
	 
	        System.out.println("End of World: x:" + x + "y: " + y + ", w: " + w + ", h:" + h);
	        //default bounding shape is a 32 by 32 box
	        boundingShape = new AABoundingRect(x,y,w,h);
	        
	    }
	    
	 public float getX(){
	        return x;
	    }
	 
	    public float getY(){
	        return y;
	    }
	 
	    public void setX(float f){
	        x = f;
	        updateBoundingShape();
	    }
	 
	    public void setY(float f){
	        y = f;
	        updateBoundingShape();
	    }
	 
	    public void updateBoundingShape(){
	        boundingShape.updatePosition(x,y);
	    }
	 
	    public BoundingShape getBoundingShape(){
	        return boundingShape;
	    } 
	    
}