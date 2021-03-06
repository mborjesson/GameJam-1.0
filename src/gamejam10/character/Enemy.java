/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.character;

import gamejam10.physics.*;

import org.newdawn.slick.*;


/**
 *
 * @author gregof
 */
public class Enemy extends Character {
    
     public Enemy(float x, float y) throws SlickException {
        super(x,y);
        this.x = x;
        this.y = y;
        // default sprite
    //    sprite = new Image("data/images/placeholder_sprite.png");
        
       setSprite(new Image("data/images/characters/player/enemystill.png"));
       setAnimation("data/images/characters/player/enemyanim.png", 22, 27, 3);
       
       //default bounding shape is a 32 by 32 box
        boundingShape = new AABoundingRect(x,y,22,27);
        
        accelerationSpeed = 0.07f;
        maximumSpeed = 0.25f; //0.15f;
        maximumFallSpeed = 0.3f;
        decelerationSpeed = 0.05f;
    }
    
     
      public void updateBoundingShape(){
        
        boundingShape.updatePosition(x-3,y);
    }

	@Override
	public void handleCollision(Character c2) {
		//System.out.println(this + " colliding with " + c2);
	}
    
}
