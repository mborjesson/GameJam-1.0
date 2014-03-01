package gamejam10.ai;
import gamejam10.character.Character;

public class RandomJumpAction extends AIAction {

	private Character character;
	double accumulatedTime = 0;
	RandomJumpAction.Parameters parameters;
	double nextJumpTime = 0;
	
	public static class Parameters {
		public double minTimeBetweenJumps;
		public double maxTimeBetweenJumps;
	}
	
	public RandomJumpAction(Character c, RandomJumpAction.Parameters parameters) {
		character = c;
		this.parameters = parameters;
	}
	
	public void doAction(double dt) {
		
		accumulatedTime += dt;
		
		if ( accumulatedTime >= nextJumpTime ) {
			System.out.println("Jumping");
			character.jump();
			accumulatedTime -= nextJumpTime;
			nextJumpTime = parameters.minTimeBetweenJumps + (parameters.maxTimeBetweenJumps - parameters.minTimeBetweenJumps) * Math.random();
		}
	}
}
