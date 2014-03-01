/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.character;

import gamejam10.ai.BasicAI;
import gamejam10.physics.AABoundingRect;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


/**
 *
 * @author gregof
 */
public class Enemy extends Character {
    
	private BasicAI testBasicAI;
	
     public Enemy(float x, float y) throws SlickException {
        super(x,y);
        this.x = x;
        this.y = y;
        // default sprite
    //    sprite = new Image("data/images/placeholder_sprite.png");
        
               setSprite(new Image("data/images/characters/player/homerstill.png"));

       setAnimation("data/images/characters/player/homeranimsmall.png", 32, 32);
       
       //default bounding shape is a 32 by 32 box
        boundingShape = new AABoundingRect(x,y,24,30);
        
        accelerationSpeed = 0.001f;
        maximumSpeed = 0.05f; //0.15f;
        maximumFallSpeed = 0.3f;
        decelerationSpeed = 0.001f;
    }
     
      public void updateBoundingShape(){
        
        boundingShape.updatePosition(x+3,y);
    }
     
      public void setAI(BasicAI ai) {
    	  testBasicAI = ai;
      }
      
      public void updateAI(double dt) {
    	  testBasicAI.update(this, dt);
      }
     
     
    
}
