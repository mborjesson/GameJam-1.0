package gamejam10.ai;

import gamejam10.character.Character;

public class RandomMovementAIAction implements AIAction  {

	private Character character;
	double accumulatedTime = 0;
	RandomMovementAIAction.Parameters parameters;
	double movementDuration;

	Direction dir;
	
	double previousX = 0;
	double notMovingDelta = 0;
	
	enum Direction {
		LEFT, 
		RIGHT
	}
	
	public static class Parameters {
		public double minX;
		public double maxX;
		public double minMovementDuration;
		public double maxMovementDuration;
	}
	
	public RandomMovementAIAction(Character c, RandomMovementAIAction.Parameters parameters) {
		character = c;
		this.parameters = parameters;
		
		dir = Direction.LEFT;
	}
	
	public void doAction(double dt) {
		
		// |minX             spawn               maxX|
		
		accumulatedTime += dt;
		
		notMovingCorrection(dt);
		
		if ( character.getX() <= parameters.minX ) {
			accumulatedTime = 0;
			movementDuration = parameters.minMovementDuration + Math.random() * parameters.maxMovementDuration;
			character.moveRight(1000);
		}
		
		if ( character.getX() >= parameters.maxX ) {
			accumulatedTime = 0;
			movementDuration = parameters.minMovementDuration + Math.random() * parameters.maxMovementDuration;
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
