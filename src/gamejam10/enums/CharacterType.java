package gamejam10.enums;

public enum CharacterType {
	PLAYER("player"),
	ENEMY_FLOAT_EASY("enemy_float_easy"),
	ENEMY_FLOAT_HARD("enemy_float_hard"),
	ENEMY_EASY("patroling_brain"),
	ENEMY_JUMPING("jumping_brain");
	
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
