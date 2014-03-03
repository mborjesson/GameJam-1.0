package gamejam10.menu;

public class MenuActionToggle extends MenuAction {
	static public interface Listener {
		public void toggle(MenuActionToggle toggle);
		public int getDefaultValue(MenuActionToggle toggle);
	}
	
	private final Listener listener;
	private final String[] stringValues;
	private final Object[] values;
	private final Object object;
	private int currentValue = 0;
	
	public MenuActionToggle(String[] stringValues, Object[] values, Object object, Listener listener) {
		if (stringValues.length != values.length) {
			throw new RuntimeException("stringValues and values must be of same length");
		}
		this.stringValues = new String[stringValues.length];
		this.values = new Object[values.length];
		this.listener = listener;
		this.object = object;
		System.arraycopy(stringValues, 0, this.stringValues, 0, stringValues.length);
		System.arraycopy(values, 0, this.values, 0, values.length);
		
		currentValue = listener.getDefaultValue(this);
	}
	
	public Listener getListener() {
		return listener;
	}
	
	public String getStringValue(int value) {
		return stringValues[value];
	}
	
	public Object getValue(int value) {
		return values[value];
	}
	
	public int getNumValues() {
		return stringValues.length;
	}
	
	public String getCurrentStringValue() {
		return getStringValue(currentValue);
	}
	
	public Object getCurrentValue() {
		return getValue(currentValue);
	}
	
	public Object getObject() {
		return object;
	}
	
	public int getValueNum(Object object) {
		for (int i = 0; i < values.length; ++i) {
			if (values[i].equals(object)) {
				return i;
			}
		}
		return 0;
	}
	
	public void toggle() {
		currentValue = (++currentValue)%stringValues.length;
		System.out.println("Toggle " + getCurrentValue());
		listener.toggle(this);
	}
}
