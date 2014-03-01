package gamejam10.menu;

public class MenuActionEnterMenu extends MenuAction {
	private final Menu menu;
	public MenuActionEnterMenu(Menu menu) {
		this.menu = menu;
	}
	
	public Menu getMenu() {
		return menu;
	}
}
