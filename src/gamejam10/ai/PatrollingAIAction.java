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
		
		dir = Direction.LEFT;
	}
	
	public void doAction(double dt) {
		
		// |minX             spawn               maxX|
		
//		accumulatedTime += dt;
		
		if ( character.getX() <= parameters.minX ) {
//			accumulatedTime = 0;
			character.moveRight(1000);
		}
		
		if ( character.getX() >= parameters.maxX ) {
//			accumulatedTime = 0;
			character.moveLeft(1000);
		}
		
	}
}
