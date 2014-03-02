package gamejam10.menu;

import org.newdawn.slick.*;
import org.newdawn.slick.state.transition.*;

public class MenuActionEnterState extends MenuAction {
	private final int stateId;
	public MenuActionEnterState(int stateId) {
		this.stateId = stateId;
	}
	
	public int getStateId() {
		return stateId;
	}
	
	public Transition getOutTransition() {
		return new FadeOutTransition(Color.black, 150);
	}
	
	public Transition getInTransition() {
		return new FadeInTransition(Color.black, 150);
	}
}
