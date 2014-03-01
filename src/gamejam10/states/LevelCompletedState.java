package gamejam10.states;

import gamejam10.enums.*;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

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
		
		
		handleKeyboardInput(container.getInput(), delta, game);
		
	}

	

	
	private void handleKeyboardInput(Input i, int delta, StateBasedGame game) {
	if (i.isKeyPressed(Input.KEY_ESCAPE)) {
		game.enterState(States.MENU.getID(), new FadeOutTransition(
				Color.black, 50), new FadeInTransition(Color.black, 50));
		}
	}

	@Override
	public int getID() {
		return States.LEVELCOMPLETED.getID();
	}

}
