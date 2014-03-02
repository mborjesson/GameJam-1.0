package gamejam10.states;

import gamejam10.*;
import gamejam10.audio.AudioPlayer;
import gamejam10.character.*;
import gamejam10.enums.*;
import gamejam10.level.*;

import javax.swing.*;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.*;

public class LevelCompletedState extends BasicGameState {
	
	private String nextLevel = null;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		if (nextLevel != null) {
			g.drawString("Grattis", 400, 200);
			g.drawString("You died " + Player.getDeathCounter() + " times.", 370, 250);
		} else {
			// if there's no levels left
			g.drawString("Grattis, there's nothing left to do!", 250, 200);
			g.drawString("You died " + Player.getDeathCounter() + " times.", 370, 250);
		}
		
		g.drawString("Grattis", 400, 200);
		string dc = Player.getDeathCounter() + " time";
		
		if ( dc == 1 )
			g.drawString("You died " + Player.getDeathCounter() + " time.", 370, 250);
		else
			g.drawString("You died " + Player.getDeathCounter() + " times.", 370, 250);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		
		handleInput(container.getInput(), delta, game);
		
	}

	

	
	private void handleInput(Input i, int delta, StateBasedGame game) throws SlickException {
		if (i.isKeyPressed(Input.KEY_ESCAPE) || isControllerPressed("b", i)) {
			AudioPlayer.getInstance().playMusic(MusicType.MENU);
			game.enterState(States.MENU.getID(), Constants.getDefaultLeaveTransition(), Constants.getDefaultEnterTransition());
		} else if (i.isKeyPressed(Input.KEY_ENTER)) {
			if (nextLevel != null) {
				GameState gs = (GameState)game.getState(States.GAME.getID());
				gs.initializeLevel(nextLevel);
				game.enterState(States.GAME.getID(), Constants.getDefaultLeaveTransition(), Constants.getDefaultEnterTransition());
			} else {
				game.enterState(States.MENU.getID(), Constants.getDefaultLeaveTransition(), Constants.getDefaultEnterTransition());
			}
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

	public void setNextLevel(String nextLevel) {
		this.nextLevel = nextLevel;
	}
}
