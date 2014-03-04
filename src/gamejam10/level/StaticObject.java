package gamejam10.level;

import gamejam10.physics.*;

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