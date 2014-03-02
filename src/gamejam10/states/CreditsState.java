package gamejam10.states;

import gamejam10.enums.*;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class CreditsState extends BasicGameState {

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		g.drawString("CREDITS", 400, 50);
		
		g.drawString("Adam Lärkeryd", 		370, 100);
		g.drawString("Erik Eliasson", 		370, 120);
		g.drawString("Henrik Olsson", 		370, 140);
		g.drawString("Magnus Lundmark", 	370, 160);
		g.drawString("Martin Börjesson", 	370, 180);
		g.drawString("Nicklas Gavelin", 	370, 200);
		
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
		return States.CREDITS.getID();
	}

}
