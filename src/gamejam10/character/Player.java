/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.character;

import gamejam10.audio.AudioPlayer;
import gamejam10.enums.*;
import gamejam10.physics.AABoundingRect;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;


/**
 *
 * @author gregof
 */
public class Player extends Character {
     
    public Player(float x, float y) throws SlickException{
        super(x,y);
        //sprite = new Image("data/img/characters/player/player.png");
         setSprite(new Image("data/images/characters/player/mainstill.png") /*new Image("data/images/characters/player/homerstill.png")*/);
//       setMovingAnimation(new Image[]{new Image("data/img/characters/player/player_1.png"),new Image("data/img/characters/player/player_2.png"),
//                                       new Image("data/img/characters/player/player_3.png"),new Image("data/img/characters/player/player_4.png")}
//                                       ,125); 
       
       
       // setAnimation("data/images/characters/player/homeranim.png");
       setAnimation(/*"data/images/characters/player/homeranimsmall.png"*/"data/images/characters/player/mainanim.png", 20, 43);
       
       //default bounding shape is a 32 by 32 box

        boundingShape = new AABoundingRect(x,y,/*24,30*/20, 39);
        
        accelerationSpeed = 0.09f;
        maximumSpeed = 0.23f;
        maximumFallSpeed = 1.0f;
        decelerationSpeed = 0.09f;
        
    }
    
    @Override
    public void jump() {
        if (onGround) {
        	AudioPlayer ap = AudioPlayer.getInstance();
        	ap.playSound(SoundType.JUMP, 0.3f);
            y_velocity = -0.6f;
        }
    }
    
    public void updateBoundingShape(){
        
        boundingShape.updatePosition(x,y+2);
    }

	@Override
	public void handleCollision(Character c2) {
		System.out.println(this + " colliding with " + c2);
	}
    
//    public void moveLeft(int delta){
//        facing = Facing.LEFT; 
//        x = x - (0.15f*delta);
//        lastTimeMoved = System.currentTimeMillis(); 
//    }
// 
//    public void moveRight(int delta){
//        facing = Facing.RIGHT;
//        x = x + (0.15f*delta);
//        lastTimeMoved = System.currentTimeMillis(); 
//    }
     
}
