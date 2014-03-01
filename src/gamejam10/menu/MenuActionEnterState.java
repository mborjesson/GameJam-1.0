package gamejam10.menu;

public class MenuActionEnterState extends MenuAction {
	private final int stateId;
	public MenuActionEnterState(int stateId) {
		this.stateId = stateId;
	}
	
	public int getStateId() {
		return stateId;
	}
}
