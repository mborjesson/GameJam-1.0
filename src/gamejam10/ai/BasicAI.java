package gamejam10.ai;

import gamejam10.character.Character;
import gamejam10.character.Player;

import java.util.ArrayList;
import java.util.Collection;

public class BasicAI {

	double accumulatedTime = 0;
	RandomJumpAction randomJumpAction;
	RandomMovementAction randomMovementAction;
	Character character;
	Player player;
	
	Collection<AIAction> aiActions = new ArrayList<AIAction>();
	

	boolean movingLeft = false;
	boolean moving = false;
	
	
	public BasicAI(Character c, Player p, int minX, int maxX) {
		player = p;
		character = c;

//		randomJumpAction = new RandomJumpAction(c, jumpParameters);
//		randomMovementAction = new RandomMovementAction(c, moveParameters);
		
	}
	
	public void addAIAction(AIAction aiAction) {
		aiActions.add(aiAction);
	}
	
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

		for (AIAction aiAction : aiActions) {
			aiAction.doAction(dt);
		}

		
	}
	
	
}
