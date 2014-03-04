package gamejam10.enums;

public enum MusicType {
	MENU("data/music/menu.ogg"),
	GAME("data/music/game.ogg");
	
	
	final private String file;
	private MusicType(String file) {
		this.file = file;
	}
	
	public String getFile() {
		return file;
	}
}
