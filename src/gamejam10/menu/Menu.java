package gamejam10.menu;

import java.util.*;

public class Menu {
	private final Map<String, MenuAction> menuMap = new TreeMap<String, MenuAction>();
	private final List<String> menuList = new ArrayList<String>();
	private final Menu parentMenu;
	private int selectedItem = 0;
	
	public Menu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}
	
	public void addMenuAction(String name, MenuAction action) {
		menuMap.put(name, action);
		menuList.add(name);
	}
	
	public int getNumItems() {
		return menuList.size();
	}
	
	public String getItemName(int item) {
		return menuList.get(item);
	}
	
	public void nextItem() {
		if (++selectedItem >= menuList.size()) {
			selectedItem = 0;
		}
	}
	
	public void previousItem() {
		if (--selectedItem < 0) {
			selectedItem = menuList.size()-1;
		}
	}
	
	public int getSelectedItem() {
		return selectedItem;
	}
	
	public MenuAction getAction(int item) {
		return menuMap.get(menuList.get(item));
	}
	
	public Menu getParentMenu() {
		return parentMenu;
	}
}
