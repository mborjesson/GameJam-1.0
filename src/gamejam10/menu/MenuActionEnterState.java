package gamejam10.menu;

import org.newdawn.slick.state.transition.*;

public class MenuActionEnterState extends MenuAction {
	private final int stateId;
	private final Transition outTransition;
	private final Transition inTransition;
	public MenuActionEnterState(int stateId, Transition outTransition, Transition inTransition) {
		this.stateId = stateId;
		this.outTransition = outTransition;
		this.inTransition = inTransition;
	}
	
	public int getStateId() {
		return stateId;
	}
	
	public Transition getOutTransition() {
		return outTransition;
	}
	
	public Transition getInTransition() {
		return inTransition;
	}
}
