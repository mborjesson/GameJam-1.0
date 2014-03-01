package gamejam10.ai;

import gamejam10.character.Character;
import gamejam10.character.Player;

public class BasicAI {

	double accumulatedTime = 0;
	RandomJumpAction randomJumpAction;
	RandomMovementAction randomMovementAction;
	Character character;
	Player player;
	
	public BasicAI(Character c, Player p) {
		player = p;
		character = c;
		randomJumpAction = new RandomJumpAction(c, 1000, 5000);
		randomMovementAction = new RandomMovementAction(c, 100, 500, 100, 1000);
		
	}
	
	boolean movingLeft = false;
	boolean moving = false;
	
	public void update(Character character, double dt) {
		
//		accumulatedTime += dt;
		
//		if ( !moving ) {
//			character.moveRight(50);
//			moving = true;
//		}
			
//			if ( player.getX() - character.getX() < 0) {
//				if ( !character.isMoving() || !movingLeft ) {
//					character.moveLeft(1000);
//					movingLeft = true;
//					System.out.println("Changing to left direction");
//				}
//			} else if ( player.getX() - character.getX() > 0 ){
//				if ( !character.isMoving() || movingLeft ) {
//					character.moveRight(1000);
//					movingLeft = false;
//					System.out.println("Changing to right direction");
//				}
//				
//			}

		randomJumpAction.update(dt);
		randomMovementAction.update(dt);
		
	}
	
	
}
