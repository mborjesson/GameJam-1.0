package gamejam10.enums;

public enum AspectRatio {
	VALUE1("16:9", 16.0/9.0),
	VALUE2("16:10", 16.0/10.0),
	VALUE3("4:3", 4.0/3.0);
	
	private final String name;
	private final double value;
	private AspectRatio(String name, double value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public double getValue() {
		return value;
	}
	
	static public Double getAspectRatio(String name) {
		for (AspectRatio ar : values()) {
			if (ar.name.equals(name)) {
				return ar.getValue();
			}
		}
		return null;
	}
	
	static public String[] getAspectRatioNames() {
		AspectRatio[] values = values();
		String[] names = new String[values.length];
		for (int i = 0; i < values.length; ++i) {
			names[i] = values[i].name;
		}
		return names;
	}
	
	static public String getDefaultAspectRatio() {
		return VALUE1.getName();
	}
}
