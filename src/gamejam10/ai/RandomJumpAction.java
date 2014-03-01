package gamejam10.ai;
import gamejam10.character.Character;

public class RandomJumpAction {

	private Character character;
	double accumulatedTime = 0;
	double maxTimeBetweenJumps;
	double minTimeBetweenJumps;
	double nextJumpTime = 0;
	
	public RandomJumpAction(Character c, double maxTimeBetweenJumps, double minTimeBetweenJumps) {
		character = c;
		this.maxTimeBetweenJumps = maxTimeBetweenJumps;
		this.minTimeBetweenJumps = minTimeBetweenJumps;
	}
	
	public void update(double dt) {
		
		accumulatedTime += dt;
		
		if ( accumulatedTime >= nextJumpTime ) {
			System.out.println("Jumping");
			character.jump();
			accumulatedTime -= nextJumpTime;
			nextJumpTime = minTimeBetweenJumps + (maxTimeBetweenJumps - minTimeBetweenJumps) * Math.random();
		}
	}
}
