package gamejam10.enums;

public enum States {
	MENU(0),
	GAME(1),
	LEVELCOMPLETED(2),
	EXIT(3);
	
	private final int id;
	private States(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
}
