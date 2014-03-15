package gamejam10.enums;

public enum SoundType {
	JUMP("data/sounds/Jump.wav"),
	DEATH("data/sounds/dead_lava.wav"),
	WEEE("data/sounds/weee.ogg"),
	COIN("data/sounds/coin.wav"),
	TELEPORT("data/sounds/teleport.wav");
	
	final private String file;
	private SoundType(String file) {
		this.file = file;
	}
	
	public String getFile() {
		return file;
	}
}
