package gamejam10.enums;

public enum States {
	MENU(0),
	GAME(1),
	LEVELCOMPLETED(2),
	CREDITS(3),
	EXIT(4);
	
	private final int id;
	private States(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
}
