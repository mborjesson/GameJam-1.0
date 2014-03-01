package gamejam10.enums;

public enum LevelType {
	SPAWN("spawn"),
	;

	final private String value;
	private LevelType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	static public LevelType getLevelObject(String value) {
		for (LevelType lo : values()) {
			if (lo.value.equalsIgnoreCase(value)) {
				return lo;
			}
		}
		return null;
	}
}
