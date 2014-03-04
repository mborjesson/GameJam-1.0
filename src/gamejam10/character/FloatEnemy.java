package gamejam10.character;

import gamejam10.ai.BasicAI;
import gamejam10.ai.actions.PatrollingAIAction;
import gamejam10.ai.actions.RandomMovementAIAction;
import gamejam10.physics.AABoundingRect;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class FloatEnemy extends AIEnemy {
	public FloatEnemy(float x, float y, int minMoveX, int maxMoveX) throws SlickException
	{
		super(x, y);
		
		int sizeX = 28;
		int sizeY = 27;
		
		setSprite(new Image("data/images/characters/player/floatenemystill.png"));
	    setAnimation("data/images/characters/player/floatenemyanim.png", sizeX, sizeY, 3);
	    
        boundingShape = new AABoundingRect(x, y, sizeX, sizeY);
        
        accelerationSpeed = 0.001f;
        maximumSpeed = 0.15f; //0.15f;
        maximumFallSpeed = 0.2f;
        decelerationSpeed = 0.02f;
        
        // AI
        BasicAI ai = new BasicAI();
		PatrollingAIAction.Parameters moveParameters = new PatrollingAIAction.Parameters();
		moveParameters.minX = 50;
		moveParameters.maxX = 150;
		ai.addAIAction(new PatrollingAIAction(this, moveParameters));
		this.setAI(ai);
	}
}
