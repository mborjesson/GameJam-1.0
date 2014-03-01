/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.character;

import gamejam10.audio.MusicPlayer;
import gamejam10.enums.Facing;
import gamejam10.level.LevelObject;

import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


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

    public Character(float x, float y) throws SlickException {
        super(x,y);
        this.x = x;
        this.y = y;
        // default sprite
        sprite = new Image("data/images/placeholder_sprite.png");
        facing = Facing.RIGHT;
    }

    protected void setSprite(Image i) {
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

    public void jump() {
        if (onGround) {
        	MusicPlayer mp = MusicPlayer.getInstance();
        	mp.playJumpSound();
            y_velocity = -0.6f;
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

    protected void setAnimation(String img) {
        movingAnimations = new HashMap<Facing, Animation>();

        SpriteSheet sheet = null;
        try {
//            sheet = new SpriteSheet("media/homeranim.png", 36, 65);
            //sheet = new SpriteSheet(img, 36, 65);
            sheet = new SpriteSheet(img, 32, 32);
        } catch (SlickException ex) {
            ex.printStackTrace();
        }

        Animation facingRightAnimation = new Animation();
        Animation facingLeftAnimation = new Animation();

        for (int i = 0; i < 8; i++) {
            facingRightAnimation.addFrame(sheet.getSprite(i, 0), 150);
        }
        movingAnimations.put(Facing.RIGHT, facingRightAnimation);

        for (int i = 0; i < 8; i++) {
            facingLeftAnimation.addFrame(sheet.getSprite(i, 0).getFlippedCopy(true, false), 150);
        }
        movingAnimations.put(Facing.LEFT, facingLeftAnimation);

    }

    protected void setfsdfAnimation(Image[] images, int frameDuration) {
        SpriteSheet sheet = null;
        try {
//            sheet = new SpriteSheet("media/homeranim.png", 36, 65);
            sheet = new SpriteSheet("media/animhamster.png", 32, 32);
        } catch (SlickException ex) {
            ex.printStackTrace();
        }
        Animation animation = new Animation();
        for (int i = 0; i < 8; i++) {
            animation.addFrame(sheet.getSprite(i, 0), 150);
        }


    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void render() {
        //   sprite.draw(x - 2, y - 2);

        //draw a moving animation if we have one and we moved within the last 150 miliseconds
        if (movingAnimations != null && moving) { //&& lastTimeMoved+150 > System.currentTimeMillis()){
            movingAnimations.get(facing).draw(x - 2, y - 2);
        } else {
            sprites.get(facing).draw(x - 2, y - 2);
        }
    }
    
    public void render(float offset_x, float offset_y){
 
        //draw a moving animation if we have one and we moved within the last 150 miliseconds
        if(movingAnimations != null && moving){
            movingAnimations.get(facing).draw(x-2-offset_x,y-2-offset_y);                
        }else{            
            sprites.get(facing).draw(x-2-offset_x, y-2-offset_y);          
        }
    }
}
