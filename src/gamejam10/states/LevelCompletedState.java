package gamejam10.states;

import gamejam10.enums.*;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class LevelCompletedState extends BasicGameState {

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		g.drawString("Grattis", 400, 200);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		//container.exit();
	}

	@Override
	public int getID() {
		return States.LEVELCOMPLETED.getID();
	}

}
