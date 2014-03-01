/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.physics;

import gamejam10.audio.AudioPlayer;
import gamejam10.character.Character;
import gamejam10.character.Enemy;
import gamejam10.character.Player;
import gamejam10.enums.*;
import gamejam10.level.Level;
import gamejam10.level.LevelObject;
import gamejam10.states.GameState;

import java.util.ArrayList;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;


/**
 *
 * @author gregof
 */
public class Physics {

    private final float gravity = 0.0015f;
    private GameState gameState;

    public Physics(GameState gameState) {
        this.gameState = gameState;
    }
    
    public void handlePhysics(Level level, int delta) {
        handleCharacters(level, delta);
        checkCollisionBetweenCharacters(level);
        checkCollisionBetweenCharacterAndTheEndOfTheUniverse(level);
    }

    private void handleCharacters(Level level, int delta) {
        for (Character c : level.getCharacters()) {
            //and now decelerate the character if he is not moving anymore
            if (!c.isMoving()) {
//                System.out.println("NOT MOVING");
                c.decelerate(delta);
            } else {
//                System.out.println("MOVING");
            }
//            System.out.println("physics handleCharacters c: " + c.getXVelocity());
            handleGameObject(c, level, delta);
        }
    }
    
    private void checkCollisionBetweenCharacters(Level level) {
    	// O(N^2) ftw :)
    	for (Character c : level.getCharacters() ) {
			if ( c instanceof Player ) {
				for (Character c2 : level.getCharacters()) {
					if ( c != c2 && c2 instanceof Enemy && 
							c.getBoundingShape().checkCollision(c2.getBoundingShape()) ) {
						killPlayer();
					}
				}
			}
		}
    }
    
    private void checkCollisionBetweenCharacterAndTheEndOfTheUniverse(Level level) {
    	// O(N^2) ftw :)
    	for (Character c : level.getCharacters() ) {
			if ( c instanceof Player ) {
				float y =((AABoundingRect)c.getBoundingShape()).getY(); 
							if (y > 1500) {
								killPlayer();
							}
			}
		}
    }
    
    private void checkIfEndOfWorld(Level level) {
    	//level.ge
    }
    
    
    
    
    private void killPlayer() {
        gameState.getPlayer().setX(50);
        gameState.getPlayer().setY(350);
        
        AudioPlayer ap = AudioPlayer.getInstance();
    	 ap.playSound(SoundType.DEATH, 0.2f);
    }

    private boolean checkCollision(LevelObject obj, Tile[][] mapTiles) {
        //get only the tiles that matter
        //System.out.println("check collision");
        ArrayList<Tile> tiles = obj.getBoundingShape().getTilesOccupying(mapTiles);
        for (Tile t : tiles) {
            //if this tile has a bounding shape
            
    
            if (t.isDeadly()) {
                
                 if (t.getBoundingShape().checkCollision(obj.getBoundingShape())) {
                     System.out.println("AAAAAAHHHHHHHHHHHH!!!!!!!!!!!");
                 	killPlayer();
                     return false;
                 }
            } 
            
            if (t.getBoundingShape() != null && t.isDeadly() != true) {
                
//                        System.out.println("((AABoundingRect)t.getBoundingShape()).x: " + ((AABoundingRect)t.getBoundingShape()).getX());
//            System.out.println("((AABoundingRect)t.getBoundingShape()).y: " + ((AABoundingRect)t.getBoundingShape()).getY());
//            System.out.println("((AABoundingRect)t.getBoundingShape()).width: " + ((AABoundingRect)t.getBoundingShape()).getWidth());
//            System.out.println("((AABoundingRect)t.getBoundingShape()).height: " + ((AABoundingRect)t.getBoundingShape()).getHeight());
//            
//            System.out.println("((AABoundingRect)obj.getBoundingShape()).x: " + ((AABoundingRect)obj.getBoundingShape()).getX());
//            System.out.println("((AABoundingRect)obj.getBoundingShape()).y: " + ((AABoundingRect)obj.getBoundingShape()).getY());
//            System.out.println("((AABoundingRect)obj.getBoundingShape()).width: " + ((AABoundingRect)obj.getBoundingShape()).getWidth());
//            System.out.println("((AABoundingRect)obj.getBoundingShape()).height: " + ((AABoundingRect)obj.getBoundingShape()).getHeight());
                
                if (t.getBoundingShape().checkCollision(obj.getBoundingShape())) {
                    // System.out.println("checkCollision true");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isOnGround(LevelObject obj, Tile[][] mapTiles) {
        //we get the tiles that are directly "underneath" the characters, also known as the ground tiles
        ArrayList<Tile> tiles = obj.getBoundingShape().getGroundTiles(mapTiles);
//System.out.println("tiles.size(): " + tiles.size());
        //we lower the the bounding object a bit so we can check if we are actually a bit above the ground
        obj.getBoundingShape().movePosition(0, 1);

        for (Tile t : tiles) {
//            System.out.println("tile: X: " + t.getX() + ", Y:" + t.getY() + ", Solid: " + t.isSolid());
            
            //not every tile has a bounding shape (air tiles for example)
            if (t.getBoundingShape() != null && t.isDeadly() != true) {
                //if the ground and the lowered object collide, then we are on the ground
                if (t.getBoundingShape().checkCollision(obj.getBoundingShape())) {
                    //don't forget to move the object back up even if we are on the ground!
                    obj.getBoundingShape().movePosition(0, -1);
                    
                    return true;
                }
            }
        }

        //and obviously we have to move the object back up if we don't hit the ground
        obj.getBoundingShape().movePosition(0, -1);

        return false;
    }

    
    private void handleGameObject(LevelObject obj, Level level, int delta) {

        //first update the onGround of the object
        obj.setOnGround(isOnGround(obj, level.getTiles()));
        
//        System.out.println("handleGameObject isOnGround(obj, level.getTiles()): " + isOnGround(obj, level.getTiles()));
        
        //now apply gravitational force if we are not on the ground or when we are about to jump
        if (!obj.isOnGround() || obj.getYVelocity() < 0) {
//            System.out.println("Applying gravity: " + gravity);            
            obj.applyGravity(gravity * delta);
        } else {
            obj.setYVelocity(0);
        }

        //calculate how much we actually have to move
        float x_movement = obj.getXVelocity() * delta;
        float y_movement = obj.getYVelocity() * delta;
//System.out.println("obj.getXVelocity(): " + obj.getXVelocity());
//System.out.println("delta: " + delta);
        //we have to calculate the step we have to take
        float step_y = 0;
        float step_x = 0;

        if (x_movement != 0) {
            step_y = Math.abs(y_movement) / Math.abs(x_movement);
            if (y_movement < 0) {
                step_y = -step_y;
            }

            if (x_movement > 0) {
                step_x = 1;
            } else {
                step_x = -1;
            }

            if ((step_y > 1 || step_y < -1) && step_y != 0) {
                step_x = Math.abs(step_x) / Math.abs(step_y);
                if (x_movement < 0) {
                    step_x = -step_x;
                }
                if (y_movement < 0) {
                    step_y = -1;
                } else {
                    step_y = 1;
                }
            }
        } else if (y_movement != 0) {
            //if we only have vertical movement, we can just use a step of 1
            if (y_movement > 0) {
                step_y = 1;
            } else {
                step_y = -1;
            }
        }
        
        //and then do little steps until we are done moving
//        System.out.println("x_movement: " + x_movement);
        while (x_movement != 0 || y_movement != 0) {
//System.out.println("in physics 111");
            //we first move in the x direction
            if (x_movement != 0) {
                //when we do a step, we have to update the amount we have to move after this
                if ((x_movement > 0 && x_movement < step_x) || (x_movement > step_x && x_movement < 0)) {
                    step_x = x_movement;
                    x_movement = 0;
                } else {
                    x_movement -= step_x;
                }

                //then we move the object one step
                obj.setX(obj.getX() + step_x);
 //System.out.println("in physics 222");
                //if we collide with any of the bounding shapes of the tiles we have to revert to our original position
                if (checkCollision(obj, level.getTiles())) {
//                    System.out.println("COL!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    //undo our step, and set the velocity and amount we still have to move to 0, because we can't move in that direction
                    obj.setX(obj.getX() - step_x);
                    obj.setXVelocity(0);
                    x_movement = 0;
                }

            }
            //same thing for the vertical, or y movement
            if (y_movement != 0) {
                if ((y_movement > 0 && y_movement < step_y) || (y_movement > step_y && y_movement < 0)) {
                    step_y = y_movement;
                    y_movement = 0;
                } else {
                    y_movement -= step_y;
                }

//                System.out.println("obj.getY(): " + obj.getY());
//                System.out.println("step_y: " + step_y);
                
                obj.setY(obj.getY() + step_y);

                if (checkCollision(obj, level.getTiles())) {
                    obj.setY(obj.getY() - step_y);
                    obj.setYVelocity(0);
                    y_movement = 0;
                    break;
                }
            }
        }
    }
}
