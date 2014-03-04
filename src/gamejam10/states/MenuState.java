/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.states;


import java.io.*;
import java.util.*;

import gamejam10.*;
import gamejam10.audio.*;
import gamejam10.character.Player;
import gamejam10.enums.*;
import gamejam10.level.*;
import gamejam10.menu.*;
import gamejam10.options.*;

import org.lwjgl.*;
import org.lwjgl.opengl.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.*;

    
/**
 *
 * @author gregof
 */
public class MenuState extends BasicGameState {
    
    private AudioPlayer audioPlayer;
    
    private Menu currentMenu = null;
    private Menu mainMenu = null;
    private Menu optionsMenu = null;
    
    @Override
    public int getID() {
        return States.MENU.getID();
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    	// build menu
    	mainMenu = new Menu("main", null);
    	optionsMenu = new Menu("options", mainMenu);
    	mainMenu.addMenuAction("resume", new MenuActionEnterState(States.GAME.getID(), 1));
    	mainMenu.addMenuAction("restart level", new MenuActionEnterState(States.GAME.getID(), 2));
    	mainMenu.addMenuAction("new game", new MenuActionEnterState(States.GAME.getID(), 0));
    	mainMenu.addMenuAction("options", new MenuActionEnterMenu(optionsMenu));
    	mainMenu.addMenuAction("credits", new MenuActionEnterState(States.CREDITS.getID(), 0));
    	mainMenu.addMenuAction("exit", new MenuActionEnterState(States.EXIT.getID(), 0));
    	
    	mainMenu.setSelectedItem(2);
    	
    	optionsMenu.addMenuAction("VSync", new MenuActionToggle(new String[] { "on", "off" }, new Boolean[] { true, false }, container, new MenuActionToggle.Listener() {
			
			@Override
			public void toggle(MenuActionToggle toggle) {
				boolean b = (boolean)toggle.getCurrentValue();
				Options.getWritableInstance().setVSync(b);
				GameContainer gc = (GameContainer)toggle.getObject();
				gc.setVSync(b);
			}
			
			@Override
			public int getDefaultValue(MenuActionToggle toggle) {
				return toggle.getValueNum(Options.getInstance().isVSync());
			}
		}));
    	optionsMenu.addMenuAction("Show FPS", new MenuActionToggle(new String[] { "on", "off" }, new Boolean[] { true, false }, container, new MenuActionToggle.Listener() {
			
			@Override
			public void toggle(MenuActionToggle toggle) {
				boolean b = (boolean)toggle.getCurrentValue();
				Options.getWritableInstance().setShowFPS((Boolean)toggle.getCurrentValue());
				GameContainer gc = (GameContainer)toggle.getObject();
				gc.setShowFPS(b);
			}
			
			@Override
			public int getDefaultValue(MenuActionToggle toggle) {
				return toggle.getValueNum(Options.getInstance().isShowFPS());
			}
		}));
    	
    	optionsMenu.addMenuAction("Audio*", new MenuActionToggle(
    			new String[] { "on", "off" },
    			new Object[] { true, false },
    			null,
    			new MenuActionToggle.Listener() {
			
			@Override
			public void toggle(MenuActionToggle toggle) {
				Options.getWritableInstance().setSoundEnabled((Boolean)toggle.getCurrentValue());
			}

			@Override
			public int getDefaultValue(MenuActionToggle toggle) {
				return toggle.getValueNum(Options.getInstance().isSoundEnabled());
			}
		}));
    	try {
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			Map<String, Mode> modeMap = new HashMap<String, Mode>();
			for (DisplayMode mode : modes) {
				String name = mode.getWidth() +"x" + mode.getHeight();
				if (!modeMap.containsKey(name)) {
					modeMap.put(name, new Mode(name, mode.getWidth(), mode.getHeight()));
				}
			}

			List<Mode> sortedModes = new ArrayList<Mode>(modeMap.values());
			Collections.sort(sortedModes);
			String[] strModes = new String[sortedModes.size()];
			Mode[] availModes = new Mode[sortedModes.size()];
			for (int i = 0; i < strModes.length; ++i) {
				availModes[i] = sortedModes.get(i);
				strModes[i] = availModes[i].name;
			}
			
	    	optionsMenu.addMenuAction("Resolution*", new MenuActionToggle(strModes, availModes, null, new MenuActionToggle.Listener() {
				
				@Override
				public void toggle(MenuActionToggle toggle) {
					Mode mode = (Mode)toggle.getCurrentValue();
					Options.getWritableInstance().setWidth(mode.width);
					Options.getWritableInstance().setHeight(mode.height);
				}
				
				@Override
				public int getDefaultValue(MenuActionToggle toggle) {
					int w = Options.getInstance().getWidth();
					int h = Options.getInstance().getHeight();
					for (int i = 0; i < toggle.getNumValues(); ++i) {
						Mode mode = (Mode)toggle.getValue(i);
						if (mode.width == w && mode.height == h) {
							return i;
						}
					}
					return 0;
				}
			}));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
    	
    	optionsMenu.addMenuAction("Fullscreen*", new MenuActionToggle(new String[] { "on", "off" }, new Boolean[] { true, false }, null, new MenuActionToggle.Listener() {
			
			@Override
			public void toggle(MenuActionToggle toggle) {
				Options.getWritableInstance().setFullscreen((Boolean)toggle.getCurrentValue());
			}
			
			@Override
			public int getDefaultValue(MenuActionToggle toggle) {
				return toggle.getValueNum(Options.getInstance().isFullscreen());
			}
		}));
    	optionsMenu.addMenuAction("Shaders*", new MenuActionToggle(new String[] { "on", "off" }, new Boolean[] { true, false }, null, new MenuActionToggle.Listener() {
			
			@Override
			public void toggle(MenuActionToggle toggle) {
				Options.getWritableInstance().setShadersEnabled((Boolean)toggle.getCurrentValue());
			}
			
			@Override
			public int getDefaultValue(MenuActionToggle toggle) {
				return toggle.getValueNum(Options.getInstance().isShadersEnabled());
			}
		}));
    	optionsMenu.addMenuAction("back", new MenuActionBack());
    	
    	
    	currentMenu = mainMenu;
        
        audioPlayer = AudioPlayer.getInstance();
        audioPlayer.playMusic(MusicType.MENU, 1);
        
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setBackground(Color.black);
		g.clear();
		
		float screenWidth = Constants.MENU_WIDTH;
		Tools.setScale(g, screenWidth);
		
		Tools.drawStringCentered(g, screenWidth, 50, "Luleå GameJam 1.0");
		
		float y = 100;
		float height = g.getFont().getLineHeight();
		
		GameState gs = (GameState)game.getState(States.GAME.getID());
		
		if (currentMenu.isMenuId("main")) {
			currentMenu.getItem(currentMenu.getItemIndex("resume")).setEnabled(gs.isRunning());
			currentMenu.getItem(currentMenu.getItemIndex("restart level")).setEnabled(gs.isRunning());
		}

		currentMenu.render(g, screenWidth, y, height);
		
		g.setColor(Color.white);
		if (currentMenu.isMenuId("main")) {
			Tools.drawStringCentered(g, screenWidth, 300, "Menu music: Torture Super Sonic - Mister S.");
			Tools.drawStringCentered(g, screenWidth, 280, "Game music: Chris ZabriskieSuper - Oxygen Garden");
			Tools.drawStringCentered(g, screenWidth, 320, "Downloaded from http://freemusicarchive.org/");
		} else if (currentMenu.isMenuId("options")) {
			Tools.drawStringCentered(g, screenWidth, 320, "* Requires restart");
		}
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
        	if (currentMenu.getParentMenu() == null) {
        		exitGame(game);
        	} else {
        		currentMenu = currentMenu.getParentMenu();
        	}
        } else if (input.isKeyPressed(Input.KEY_DOWN) || isControllerPressed("down", input) ) {
        	currentMenu.nextItem();
        } else if (input.isKeyPressed(Input.KEY_UP) || isControllerPressed("up", input) ) {
        	currentMenu.previousItem();
        } else if (input.isKeyPressed(Input.KEY_SPACE) || input.isKeyPressed(Input.KEY_ENTER) || isControllerPressed("a", input) )  {
        	MenuItem i = currentMenu.getItem(currentMenu.getSelectedItem());
        	if (i.isEnabled()) {
	        	MenuAction a = i.getAction();
	        	if (a instanceof MenuActionEnterState) {
	        		MenuActionEnterState actionState = (MenuActionEnterState)a;
	        		if (actionState.getStateId() == States.GAME.getID()) {
	                    audioPlayer.playMusic(MusicType.GAME, 0.3f);
	                    
	                    // play game, initialize first level
	                    if (actionState.getType() == 0) {
	                    	currentMenu.setSelectedItem(1);
		                    GameState gs = (GameState)game.getState(States.GAME.getID());
		                    LevelOrder lo = LevelOrder.getInstance();
		                    lo.reset();
		                    Player.resetDeathCounter();
		                    gs.initializeLevel(lo.getNextLevel());
	                    } else if (actionState.getType() == 2) {
		                    GameState gs = (GameState)game.getState(States.GAME.getID());
		                    LevelOrder lo = LevelOrder.getInstance();
		                    gs.initializeLevel(lo.getCurrentLevel());
	                    }
	        		}
	                game.enterState(actionState.getStateId(), actionState.getLeaveTransition(), actionState.getEnterTransition());
	        	} else if (a instanceof MenuActionEnterMenu) {
	        		MenuActionEnterMenu menuState = (MenuActionEnterMenu)a;
	        		currentMenu = menuState.getMenu();
	        	} else if (a instanceof MenuActionBack) {
	        		currentMenu = currentMenu.getParentMenu();
	        	} else if (a instanceof MenuActionToggle) {
	        		((MenuActionToggle)a).toggle();
	        	}
        	}
        }
    }
    
    private boolean isControllerPressed(String btn, Input i)
	{
    	for(int c = 0; c < i.getControllerCount(); c++)
		{
			if (btn == "up" && i.isControlPressed(2, c))
				return true;
			else if (btn == "down" && i.isControlPressed(3, c))
				return true;
			else if (btn == "a" && i.isButton1Pressed(c))
				return true;
		}
    	
		return false;
	}
    
    private void exitGame(StateBasedGame game) {
    	game.enterState(States.EXIT.getID(), Constants.getDefaultLeaveTransition(), Constants.getDefaultEnterTransition());
    }
    
	static private class Mode implements Comparable<Mode> {
		String name;
		int width;
		int height;
		
		Mode(String name, int width, int height) {
			this.name = name;
			this.width = width;
			this.height = height;
		}

		public int compareTo(Mode o) {
			return name.compareTo(o.name);
		}
	}

}
