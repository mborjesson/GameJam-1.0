package gamejam10.menu;

import java.util.*;

public class Menu {
	private final Map<String, MenuItem> menuMap = new TreeMap<String, MenuItem>();
	private final List<MenuItem> menuList = new ArrayList<MenuItem>();
	private final Menu parentMenu;
	private int selectedItem = 0;
	
	public Menu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}
	
	public void addMenuAction(String name, MenuAction action) {
		MenuItem item = new MenuItem(name, action);
		menuMap.put(name, item);
		item.setIndex(menuList.size());
		menuList.add(item);
	}
	
	public int getNumItems() {
		return menuList.size();
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
	
	public int getItemIndex(String name) {
		return menuMap.get(name).getIndex();
	}
	
	public MenuItem getItem(int item) {
		return menuList.get(item);
	}
	
	public Menu getParentMenu() {
		return parentMenu;
	}
	
	public void setSelectedItem(int selectedItem) {
		this.selectedItem = selectedItem;
	}
}
