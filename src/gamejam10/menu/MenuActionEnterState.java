package gamejam10.menu;

import gamejam10.*;

import org.newdawn.slick.state.transition.*;

public class MenuActionEnterState extends MenuAction {
	private final int stateId;
	public MenuActionEnterState(int stateId) {
		this.stateId = stateId;
	}
	
	public int getStateId() {
		return stateId;
	}
	
	public Transition getLeaveTransition() {
		return Constants.getDefaultLeaveTransition();
	}
	
	public Transition getEnterTransition() {
		return Constants.getDefaultEnterTransition();
	}
}
