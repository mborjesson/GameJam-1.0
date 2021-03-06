package gamejam10.enums;

public enum SoundType {
	JUMP("data/sounds/Jump.wav"),
	DEATH("data/sounds/dead_lava.wav"),
	WEEE("data/sounds/weee.ogg");
	
	final private String file;
	private SoundType(String file) {
		this.file = file;
	}
	
	public String getFile() {
		return file;
	}
}
