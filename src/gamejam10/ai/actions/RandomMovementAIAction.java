package gamejam10.ai.actions;

import gamejam10.ai.AIConstants;
import gamejam10.ai.Direction;
import gamejam10.ai.NotMovingCorrectionHelper;
import gamejam10.character.Character;

public class RandomMovementAIAction implements AIAction  {

	public static class Parameters {
		public double minX;
		public double maxX;
		public double minMovementDuration;
		public double maxMovementDuration;
	}
	
	private Character character;
	private double accumulatedTime = 0;
	private RandomMovementAIAction.Parameters parameters;
	private double movementDuration;
	private NotMovingCorrectionHelper notMovingCorrectionHelper;
	private Direction dir;
	
	public RandomMovementAIAction(Character c, RandomMovementAIAction.Parameters parameters) {
		character = c;
		this.parameters = parameters;
		notMovingCorrectionHelper = new NotMovingCorrectionHelper(character);
		
		setDirection(Math.random() > 0.5 ? Direction.LEFT : Direction.RIGHT);
	}
	
	public void doAction(double dt) {
		
		// |minX             spawn               maxX|
		
		accumulatedTime += dt;

		notMovingCorrectionHelper.notMovingCorrection(dt);
		
		if ( character.getX() <= parameters.minX ) {
			accumulatedTime = 0;
			setNewMovementDuration();
			setDirection(Direction.RIGHT);
		} else if ( character.getX() >= parameters.maxX ) {
			accumulatedTime = 0;
			setNewMovementDuration();
			setDirection(Direction.LEFT);
		}
		
		if ( accumulatedTime > movementDuration ) {
			switchDirection();
			accumulatedTime = 0;
			setNewMovementDuration();
		}
		
	}
	
	public void switchDirection() {
		setDirection(this.dir == Direction.LEFT ? Direction.RIGHT : Direction.LEFT);
	}
	
	public void setDirection(Direction dir) {
		
		notMovingCorrectionHelper.setDirection(dir);
		
		if ( dir == Direction.LEFT) {
			character.moveLeft(AIConstants.MOVEMENT_ACCELERATION_VALUE);
		} else if ( dir == Direction.RIGHT ) {
			character.moveRight(AIConstants.MOVEMENT_ACCELERATION_VALUE);
		}
		
		this.dir = dir;

	}
	
	private void setNewMovementDuration() {
		movementDuration = parameters.minMovementDuration + 
				Math.random() * (parameters.maxMovementDuration - parameters.minMovementDuration);
	}
	
}
