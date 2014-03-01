package gamejam10.ai;

import gamejam10.character.Character;

public class RandomMovementAction {

	private Character character;
	double accumulatedTime = 0;
	double minX;
	double maxX;
	double movementDuration;
	double minMovementDuration;
	double maxMovementDuration;
	Direction dir;
	
	double previousX = 0;
	double notMovingDelta = 0;
	
	enum Direction {
		LEFT, 
		RIGHT
	}
	
	public RandomMovementAction(Character c, double minX, double maxX, 
			double minMovementDuration, double maxMovementDuration) {
		character = c;
		this.minX = minX;
		this.maxX = maxX;
		this.maxMovementDuration = maxMovementDuration;
		this.minMovementDuration = minMovementDuration;
		dir = Direction.LEFT;
	}
	
	public void update(double dt) {
		
		// |minX             spawn               maxX|
		
		accumulatedTime += dt;
		
		notMovingCorrection(dt);
		
		if ( character.getX() <= minX ) {
			accumulatedTime = 0;
			movementDuration = 1000 + Math.random() * 2000;
			character.moveRight(1000);
		}
		
		if ( character.getX() >= maxX ) {
			accumulatedTime = 0;
			movementDuration = 1000 + Math.random() * 2000;
			character.moveLeft(1000);
		}
		
		if ( accumulatedTime > movementDuration ) {
			// Change direction
			if ( dir == Direction.LEFT ) {
				dir = Direction.RIGHT;
				character.moveRight(1000);
			} else {
				dir = Direction.LEFT;
				character.moveLeft(1000);
			}
			
			accumulatedTime = 0;
			movementDuration = 1000 + Math.random() * 2000;
		}
		
		
		
	}
	
	public void notMovingCorrection(double dt) {
		
		notMovingDelta  += dt;
		
		// Check if not moving
		if ( character.getX() == previousX  ) {
			notMovingDelta += dt;
			
			if ( notMovingDelta > 1000 ) {
				System.out.println("Not moving!!");
				character.jump();
				
				// Start moving again in the same direction
				if ( dir == Direction.LEFT ) {
					character.moveLeft(10000);
				} else {
					character.moveRight(10000);
				}
				
				notMovingDelta = 0;
			}
		}
		
		previousX = character.getX();
	}
	
}
