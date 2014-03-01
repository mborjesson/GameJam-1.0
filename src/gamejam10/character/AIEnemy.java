package gamejam10.character;

import gamejam10.ai.BasicAI;

import org.newdawn.slick.SlickException;

public class AIEnemy extends Enemy {

	private BasicAI testBasicAI;
	
	public AIEnemy(float x, float y) throws SlickException {
		super(x, y);
	}

    public void setAI(BasicAI ai) {
  	  testBasicAI = ai;
    }
    
    public void updateAI(double dt) {
  	  testBasicAI.update(this, dt);
    }
}
