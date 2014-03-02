package gamejam10.menu;

import gamejam10.*;

import org.newdawn.slick.state.transition.*;

public class MenuActionEnterState extends MenuAction {
	private final int stateId;
	private final int type;
	public MenuActionEnterState(int stateId, int type) {
		this.stateId = stateId;
		this.type = type;
	}
	
	public int getStateId() {
		return stateId;
	}

	public int getType() {
		return type;
	}
	
	public Transition getLeaveTransition() {
		return Constants.getDefaultLeaveTransition();
	}
	
	public Transition getEnterTransition() {
		return Constants.getDefaultEnterTransition();
	}
}
