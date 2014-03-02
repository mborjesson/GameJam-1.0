package gamejam10.states;

import gamejam10.*;
import gamejam10.audio.AudioPlayer;
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
		
		g.setBackground(Color.black);
		g.clear();
		g.setColor(Color.white);
		
		float screenWidth = Constants.MENU_WIDTH;
		Tools.setScale(g, screenWidth);
		
		Tools.drawStringCentered(g, screenWidth, 50, "CREDITS");
		
		int height = g.getFont().getLineHeight();

		int y = 100;
		for (int i = 0; i < Constants.AUTHORS.length; ++i) {
			String a = Constants.AUTHORS[i];
			Tools.drawStringCentered(g, screenWidth, y+i*height, a);
		}
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		
		handleKeyboardInput(container.getInput(), delta, game);
		
	}
	
	private void handleKeyboardInput(Input i, int delta, StateBasedGame game) {
	if (i.isKeyPressed(Input.KEY_ESCAPE) || isControllerPressed("b", i) || i.isKeyPressed(Input.KEY_SPACE) || i.isKeyPressed(Input.KEY_ENTER)) {
		i.clearControlPressedRecord();
		i.clearKeyPressedRecord();
		AudioPlayer.getInstance().playMusic(MusicType.MENU, 1f);
		game.enterState(States.MENU.getID(), new FadeOutTransition(
				Color.black, 150), new FadeInTransition(Color.black, 150));
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
