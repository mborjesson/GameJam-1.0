package gamejam10.ai;

import gamejam10.character.Character;

/**
 * Requires a parallel surface to patrol
 */
public class PatrollingAIAction implements AIAction  {

	private Character character;
	double accumulatedTime = 0;
	PatrollingAIAction.Parameters parameters;

	Direction dir;
	
	enum Direction {
		LEFT, 
		RIGHT
	}
	
	public static class Parameters {
		public double minX;
		public double maxX;
	}
	
	public PatrollingAIAction(Character c, PatrollingAIAction.Parameters parameters) {
		character = c;
		this.parameters = parameters;
		
		dir = Math.random() > 0.5 ? Direction.LEFT : Direction.RIGHT;
	}
	
	public void doAction(double dt) {
		
		// |minX             spawn               maxX|
		
		if ( character.getX() <= parameters.minX ) {
			character.moveRight(1000);
		}
		
		if ( character.getX() >= parameters.maxX ) {
			character.moveLeft(1000);
		}
		
	}
}
