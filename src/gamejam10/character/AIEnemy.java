package gamejam10.character;

import gamejam10.ai.*;

import org.newdawn.slick.*;

public class AIEnemy extends Enemy {

	private BasicAI basicAI;
	
	public AIEnemy(float x, float y) throws SlickException {
		super(x, y);
	}

    public void setAI(BasicAI ai) {
  	  basicAI = ai;
    }
    
    public void updateAI(double dt) {
  	  basicAI.update(this, dt);
    }
}
