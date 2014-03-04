package gamejam10.ai.actions;

import gamejam10.ai.AIConstants;
import gamejam10.ai.Direction;
import gamejam10.ai.NotMovingCorrectionHelper;
import gamejam10.character.Character;

/**
 * Requires a parallel surface to patrol
 */
public class PatrollingAIAction implements AIAction  {

	public static class Parameters {
		public double minX;
		public double maxX;
	}
	
	private Character character;
	private PatrollingAIAction.Parameters parameters;
	private Direction dir;
	private NotMovingCorrectionHelper notMovingCorrectionHelper;
	
	public PatrollingAIAction(Character c, PatrollingAIAction.Parameters parameters) {
		character = c;
		this.parameters = parameters;
		notMovingCorrectionHelper = new NotMovingCorrectionHelper(character);
		
		setDirection(Math.random() > 0.5 ? Direction.LEFT : Direction.RIGHT);		
	}
	
	public void doAction(double dt) {
		
		// |minX             spawn               maxX|
		
		notMovingCorrectionHelper.notMovingCorrection(dt);
		
		if ( character.getX() <= parameters.minX ) {
			setDirection(Direction.RIGHT);
		}
		
		if ( character.getX() >= parameters.maxX ) {
			setDirection(Direction.LEFT);
		}
		
		
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
}
