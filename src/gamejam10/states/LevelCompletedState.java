package gamejam10.states;

import gamejam10.Constants;
import gamejam10.*;

import gamejam10.character.Player;
import gamejam10.enums.MusicType;
import gamejam10.enums.States;
import gamejam10.states.GameState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import gamejam10.audio.*;
import gamejam10.character.*;
import gamejam10.enums.*;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class LevelCompletedState extends BasicGameState {
	
	private String nextLevel = null;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		String dc = Player.getDeathCounter() + "time";
		dc += Player.getDeathCounter() == 1 ? "." : "s.";
		
		if (nextLevel != null) {
			g.drawString("Grattis", 400, 200);
			g.drawString("You died " + dc, 370, 250);
		} else {
			// if there's no levels left
			g.drawString("Grattis, there's nothing left to do!", 250, 200);
			g.drawString("You died " + dc, 370, 250);
		}
		
		g.drawString("Press Space/Enter or A to continue...", 380, 300);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		
		handleInput(container.getInput(), delta, game);
		
	}

	

	
	private void handleInput(Input i, int delta, StateBasedGame game) throws SlickException {
		if (i.isKeyPressed(Input.KEY_ESCAPE) || isControllerPressed("b", i)) {
			AudioPlayer.getInstance().playMusic(MusicType.MENU);
			GameState gs = (GameState)game.getState(States.GAME.getID());
			if (nextLevel != null) {
				gs.initializeLevel(nextLevel);
			} else {
				gs.setRunning(false);
			}
			game.enterState(States.MENU.getID(), Constants.getDefaultLeaveTransition(), Constants.getDefaultEnterTransition());
		} else if (i.isKeyPressed(Input.KEY_ENTER) || i.isKeyPressed(Input.KEY_SPACE) || isControllerPressed("a", i)) {
			GameState gs = (GameState)game.getState(States.GAME.getID());
			if (nextLevel != null) {
				gs.initializeLevel(nextLevel);
				game.enterState(States.GAME.getID(), Constants.getDefaultLeaveTransition(), Constants.getDefaultEnterTransition());
			} else {
				gs.setRunning(false);
				game.enterState(States.CREDITS.getID(), Constants.getDefaultLeaveTransition(), Constants.getDefaultEnterTransition());
			}
		}
	}
	
	private boolean isControllerPressed(String btn, Input i)
	{
		for(int c = 0; c < i.getControllerCount(); c++)
		{
			if (btn == "a" && i.isButton1Pressed(c))
				return true;
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
