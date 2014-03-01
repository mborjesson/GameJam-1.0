package gamejam10.enums;

public enum States {
	MENU(0),
	GAME(1),
	GAMEOVER(2);
	
	private final int id;
	private States(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
}
