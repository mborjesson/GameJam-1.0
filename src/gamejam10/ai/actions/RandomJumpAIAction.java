package gamejam10.ai.actions;
import gamejam10.character.Character;

public class RandomJumpAIAction implements AIAction {

	public static class Parameters {
		public double minTimeBetweenJumps;
		public double maxTimeBetweenJumps;
	}
	
	private Character character;
	private double accumulatedTime = 0;
	private RandomJumpAIAction.Parameters parameters;
	private double nextJumpTime = 0;
	
	public RandomJumpAIAction(Character c, RandomJumpAIAction.Parameters parameters) {
		character = c;
		this.parameters = parameters;
	}
	
	public void doAction(double dt) {
		
		accumulatedTime += dt;
		
		if ( accumulatedTime >= nextJumpTime ) {
			character.jump();
			accumulatedTime -= nextJumpTime;
			nextJumpTime = parameters.minTimeBetweenJumps + (parameters.maxTimeBetweenJumps - parameters.minTimeBetweenJumps) * Math.random();
		}
	}
}
