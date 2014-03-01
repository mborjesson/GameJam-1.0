package gamejam10.character;

import gamejam10.ai.BasicAI;
import gamejam10.ai.RandomMovementAIAction;
import gamejam10.physics.AABoundingRect;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Brainemy extends AIEnemy 
{
	public Brainemy(float x, float y, int minMoveX, int maxMoveX) throws SlickException
	{
		super(x, y);

        int sizeX = 22;
		int sizeY = 27;
		
		setSprite(new Image("data/images/characters/player/enemystill.png"));
	    setAnimation("data/images/characters/player/enemyanim.png", sizeX, sizeY, 3);
	    
        boundingShape = new AABoundingRect(x, y, sizeX, sizeY);
        
        accelerationSpeed = 0.001f;
        maximumSpeed = 0.15f; //0.15f;
        maximumFallSpeed = 0.2f;
        decelerationSpeed = 0.02f;
        
        // AI
		RandomMovementAIAction.Parameters moveParameters = new RandomMovementAIAction.Parameters();
		moveParameters.maxMovementDuration = 1000;
		moveParameters.minMovementDuration = 100;
		moveParameters.minX = x - minMoveX;
		moveParameters.maxX = x + maxMoveX;
		
		BasicAI ai = new BasicAI(this, null);
		ai.addAIAction(new RandomMovementAIAction(this, moveParameters));
		this.setAI(ai);
	}
}
