package gamejam10.enums;

public enum MusicType {
	MENU("/data/music/test3.ogg"),
	//GAME("/data/music/game.ogg");
	GAME("/data/music/test2.ogg");
	
	final private String file;
	private MusicType(String file) {
		this.file = file;
	}
	
	public String getFile() {
		return file;
	}
}
