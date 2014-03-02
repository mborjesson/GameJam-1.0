package gamejam10.states;

import javax.swing.JOptionPane;

import gamejam10.character.Player;
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
		g.drawString("You died " + Player.getDeathCounter() + " times.", 370, 250);

		System.out.println(Player.getDeathCounter());
		
		if (Player.getDeathCounter() <= 5) {
			JOptionPane.showMessageDialog(null, "FLAWLESS VICTORY!!!!");
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		
		handleKeyboardInput(container.getInput(), delta, game);
		
	}

	

	
	private void handleKeyboardInput(Input i, int delta, StateBasedGame game) {
	if (i.isKeyPressed(Input.KEY_ESCAPE) || isControllerPressed("b", i)) {
		game.enterState(States.MENU.getID(), new FadeOutTransition(
				Color.black, 50), new FadeInTransition(Color.black, 50));
		}
	}
	
	private boolean isControllerPressed(String btn, Input i)
	{
		for(int c = 0; c < i.getControllerCount(); c++)
		{
			if (btn == "b" && i.isButton2Pressed(c))
				return true;
		}
		
		return false;
	}

	@Override
	public int getID() {
		return States.LEVELCOMPLETED.getID();
	}

}
