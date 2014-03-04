package gamejam10.menu;

import gamejam10.*;

import java.util.*;

import org.newdawn.slick.*;

public class Menu {
	private final Map<String, MenuItem> menuMap = new TreeMap<String, MenuItem>();
	private final List<MenuItem> menuList = new ArrayList<MenuItem>();
	private final Menu parentMenu;
	private int selectedItem = 0;
	private final String id;
	
	public Menu(String id, Menu parentMenu) {
		this.id = id;
		this.parentMenu = parentMenu;
	}
	
	public String getId() {
		return id;
	}
	
	public boolean isMenuId(String id) {
		return this.id.equalsIgnoreCase(id);
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
	
	public int getSelectedItemNum() {
		return selectedItem;
	}
	
	public MenuItem getSelectedItem() {
		return getItem(selectedItem);
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
	
	public void render(Graphics g, float screenWidth, float y, float lineHeight) {
		for (int i = 0; i < getNumItems(); ++i) {
			MenuItem item = getItem(i);
			String name = item.getName();
			if (item.getAction() instanceof MenuActionToggle) {
				MenuActionToggle toggle = (MenuActionToggle)item.getAction();
				name += " " + toggle.getCurrentStringValue();
			}
			if (getSelectedItemNum() == i) {
				name = name.toUpperCase();
				if (!item.isEnabled()) {
					g.setColor(Color.lightGray);
				} else {
					g.setColor(Color.white);
				}
			} else {
				name = name.toLowerCase();
				if (!item.isEnabled()) {
					g.setColor(Color.darkGray);
				} else {
					g.setColor(Color.lightGray);
				}
			}
			Tools.drawStringCentered(g, screenWidth, y+lineHeight*i, name);
		}

	}
}
