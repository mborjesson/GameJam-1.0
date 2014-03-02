/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.character;

import gamejam10.enums.*;
import gamejam10.level.*;
import gamejam10.physics.*;

import java.util.*;

import org.newdawn.slick.*;


/**
 *
 * @author gregof
 */
public abstract class Character extends LevelObject {

//    protected float x;
//    protected float y;
    protected Image sprite;
    protected Facing facing;
    protected HashMap<Facing, Animation> movingAnimations;
    protected long lastTimeMoved;
    private HashMap<Facing, Image> sprites;
    protected boolean moving = false;
    protected float accelerationSpeed = 1;
    protected float decelerationSpeed = 1;
    protected float maximumSpeed = 1;
    
    private float jumpVelocity = -0.6f;
    
    protected boolean highlight = false;

    public Character(float x, float y) throws SlickException {
        super(x,y);
        this.x = x;
        this.y = y;
        // default sprite
        sprite = new Image("data/images/placeholder_sprite.png");
        facing = Facing.RIGHT;
    }

    protected void setSprite(Image i) {
    	i.setFilter(Image.FILTER_NEAREST);
        sprites = new HashMap<Facing, Image>();
        sprites.put(Facing.RIGHT, i);
        sprites.put(Facing.LEFT, i.getFlippedCopy(true, false));
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean b) {
        moving = b;
    }
    
    public void setMaximumSpeed(float maxSpeed) {
    	maximumSpeed = maxSpeed;
    }
    
    public void setMaximumFallSpeed(float maxSpeed) {
    	maximumFallSpeed = maxSpeed;
    }
    
    //move towards x_velocity = 0
    public void decelerate(int delta) {
        if (x_velocity > 0) {
            x_velocity -= decelerationSpeed * delta;
            if (x_velocity < 0) {
                x_velocity = 0;
            }
        } else if (x_velocity < 0) {
            x_velocity += decelerationSpeed * delta;
            if (x_velocity > 0) {
                x_velocity = 0;
            }
        }
    }
    
    public void setJumpVelocity(float jumpVelocity) {
    	this.jumpVelocity = jumpVelocity;
    }
    
    public float getJumpVelocity() {
    	return this.jumpVelocity;
    }
    
    public void jump() {
        if (onGround) {
            y_velocity = getJumpVelocity();
            showJumpingAnimation = true;
        }
    }

    public void moveLeft(int delta){
        //if we aren't already moving at maximum speed
        if(x_velocity > -maximumSpeed){
            //accelerate
            x_velocity -= accelerationSpeed*delta;
            if(x_velocity < -maximumSpeed){
                //and if we exceed maximum speed, set it to maximum speed
                x_velocity = -maximumSpeed;
            }
        }
        moving = true;
        facing = Facing.LEFT;
    }
 
    public void moveRight(int delta){
//        System.out.println("moveRight delta: " + delta);
        if(x_velocity < maximumSpeed){
            x_velocity += accelerationSpeed*delta;
            if(x_velocity > maximumSpeed){
                x_velocity = maximumSpeed;
            }
        }
        moving = true;
        facing = Facing.RIGHT;

    }
    
    
    /**
     *
     * @param images
     * @param frameDuration
     */
    protected void setMovingAnimation(Image[] images, int frameDuration) {
        movingAnimations = new HashMap<Facing, Animation>();

        //we can just put the right facing in with the default images
        movingAnimations.put(Facing.RIGHT, new Animation(images, frameDuration));

        Animation facingLeftAnimation = new Animation();
        for (Image i : images) {
            facingLeftAnimation.addFrame(i.getFlippedCopy(true, false), frameDuration);
        }
        movingAnimations.put(Facing.LEFT, facingLeftAnimation);

    }
    
    protected void setJumpAnimation(String img, int frameWidth, int frameHeight, int duration, int numberOfFrames) {
        SpriteSheet sheet = null;
        try {
            sheet = new SpriteSheet(img, frameWidth, frameHeight);
        } catch (SlickException ex) {
            ex.printStackTrace();
        }

        jumpAnimations = new HashMap<Facing, Animation>();
        
        Animation jumpAnimationRight = new Animation();
        
        for (int i = 0; i < numberOfFrames; i++) {
        	jumpAnimationRight.addFrame(sheet.getSprite(i, 0), duration);
        }
        
        Animation jumpAnimationLeft = new Animation();
        
        for (int i = 0; i < numberOfFrames; i++) {
        	jumpAnimationLeft.addFrame(sheet.getSprite(i, 0).getFlippedCopy(true, false), duration);
        }
        
        jumpAnimations.put(Facing.RIGHT, jumpAnimationRight);
        jumpAnimations.put(Facing.LEFT, jumpAnimationLeft);
    }

    protected void setAnimation(String img, int frameWidth, int frameHeight, int numberOfFrames) {
        movingAnimations = new HashMap<Facing, Animation>();

        SpriteSheet sheet = null;
        try {
//            sheet = new SpriteSheet("media/homeranim.png", 36, 65);
            //sheet = new SpriteSheet(img, 36, 65);
            sheet = new SpriteSheet(img, frameWidth, frameHeight);
            sheet.setFilter(Image.FILTER_NEAREST);
        } catch (SlickException ex) {
            ex.printStackTrace();
        }

        Animation facingRightAnimation = new Animation();
        Animation facingLeftAnimation = new Animation();

        for (int i = 0; i < numberOfFrames; i++) {
            facingRightAnimation.addFrame(sheet.getSprite(i, 0), 150);
        }
        movingAnimations.put(Facing.RIGHT, facingRightAnimation);

        for (int i = 0; i < numberOfFrames; i++) {
            facingLeftAnimation.addFrame(sheet.getSprite(i, 0).getFlippedCopy(true, false), 150);
        }
        movingAnimations.put(Facing.LEFT, facingLeftAnimation);

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    HashMap<Facing, Animation> jumpAnimations;
    boolean showJumpingAnimation;
    int currentIndex = 0;
    
    public void render(Graphics g, float offset_x, float offset_y){
    	
    	float xp = x-offset_x;
    	float yp = y-offset_y;
 
    	if ( jumpAnimations != null && showJumpingAnimation ) {
            
    		Animation anim = jumpAnimations.get(facing);
    		anim.draw(xp, yp);
    		
        	for (Animation a : jumpAnimations.values()) {
				a.setCurrentFrame(anim.getFrame());
			}
    		
    		// If we showed the last animation
            if ( anim.getFrame() == anim.getFrameCount()-1 ) {
            	showJumpingAnimation = false;
            	for (Animation a : jumpAnimations.values()) {
					a.restart();
				}
            	currentIndex = 0;
            }
            
    	} else {
	        //draw a moving animation if we have one and we moved within the last 150 miliseconds
	        if(movingAnimations != null && moving){
	            movingAnimations.get(facing).draw(xp, yp);                
	        }else{            
	            sprites.get(facing).draw(xp, yp);          
	        }
    	}
        
        if (highlight) {
    		g.setColor(Color.white);
    		AABoundingRect aab = (AABoundingRect) getBoundingShape();
    		float width = aab.getWidth();
    		float height = aab.getHeight();

    		g.drawRect(xp, yp, width, height);

        }
    }
    
    public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}
    
    public boolean isHighlight() {
		return highlight;
	}

	public abstract void handleCollision(Character c2);
	
}
