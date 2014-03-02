package gamejam10.menu;

public class MenuItem {
	private final String name;
	private final MenuAction action;
	private boolean enabled = true;
	private int index = 0;
	
	public MenuItem(String name, MenuAction action) {
		this.name = name;
		this.action = action;
	}
	
	public String getName() {
		return name;
	}
	
	public MenuAction getAction() {
		return action;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MenuItem) {
			return ((MenuItem)obj).name.equalsIgnoreCase(name);
		}
		return false;
	}
}
