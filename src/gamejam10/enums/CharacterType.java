package gamejam10.enums;

public enum CharacterType {
	PLAYER("player"),
	ENEMY_EASY("skurk01"),
	ENEMY_JUMPING("skurk02");
	
	final private String value;
	private CharacterType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	static public CharacterType getCharacterType(String value) {
		for (CharacterType ct : values()) {
			if (ct.value.equalsIgnoreCase(value)) {
				return ct;
			}
		}
		return null;
	}
}
