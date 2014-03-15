/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.level;

import gamejam10.physics.*;

/**
 *
 * @author gregof
 */
public abstract class LevelObject {
   
	protected String name;
    protected float x;
    protected float y;
    protected BoundingShape boundingShape;
 
    protected float    x_velocity = 0;
    protected float    y_velocity = 0;
    protected float    maximumFallSpeed = 1;
 
    protected boolean  onGround = true;
    
     public LevelObject(float x, float y){
        this.x = x;
        this.y = y;
 
        //default bounding shape is a 32 by 32 box
        boundingShape = new AABoundingRect(x,y,32,32);   
    }
 
    public LevelObject(String name, float x, float y, float width, float height) {
    	this.x = x;
    	this.y = y;
    	this.name = name;
    	
    	boundingShape = new AABoundingRect(x, y, width, height);
    }
     
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void applyGravity(float gravity){
        if(y_velocity < maximumFallSpeed){
            //accelerate
            y_velocity += gravity;
            if(y_velocity > maximumFallSpeed){
                //and if we exceed maximum speed, set it to maximum speed
                y_velocity = maximumFallSpeed;
            }
        }
    }
 
    public float getYVelocity() {
        return y_velocity;
    }
 
    public void setYVelocity(float f){
        y_velocity = f;
    }
 
    public float getXVelocity(){
        return x_velocity;
    }
 
    public void setXVelocity(float f){
        
        x_velocity = f;
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
 
    public boolean isOnGround(){
        return onGround;
    }
 
    public void setOnGround(boolean b){
        onGround = b;
    }
 
    public BoundingShape getBoundingShape(){
        return boundingShape;
    }

//    /**
//     * @param boundingShape the boundingShape to set
//     */
//    public void setBoundingShape(BoundingShape boundingShape) {
//        this.boundingShape = boundingShape;
//    }
// 
    
}
