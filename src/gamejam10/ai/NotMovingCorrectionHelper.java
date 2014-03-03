package gamejam10.ai;

import gamejam10.character.Character;

public class NotMovingCorrectionHelper {

	private Character character;
	private double previousX = 0;
	private double notMovingDelta = 0;
	private Direction dir;
	
	public NotMovingCorrectionHelper(Character character) {
		this.character = character;
	}
	
	public void setDirection(Direction dir) {
		this.dir = dir;
	}
	
	public void notMovingCorrection(double dt) {
		
		// Check if not moving
		if ( character.getX() == previousX  ) {
			notMovingDelta += dt;
			
			if ( notMovingDelta > AIConstants.STUCK_DURATION ) {
//				System.out.println("Not moving!!");
				character.jump();
				
				// Start moving again in the same direction
				if ( dir == Direction.LEFT ) {
					character.moveLeft(AIConstants.MOVEMENT_ACCELERATION_VALUE);
				} else {
					character.moveRight(AIConstants.MOVEMENT_ACCELERATION_VALUE);
				}
				
				notMovingDelta = 0;
			}
		}
		
		previousX = character.getX();
	}
	
}
