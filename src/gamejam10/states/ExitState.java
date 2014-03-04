package gamejam10.states;

import gamejam10.enums.*;
import gamejam10.options.*;

import java.io.*;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class ExitState extends BasicGameState {

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		try {
			Options.getWritableInstance().write();
		} catch (IOException e) {
			e.printStackTrace();
		}
		container.exit();
	}

	@Override
	public int getID() {
		return States.EXIT.getID();
	}

}
