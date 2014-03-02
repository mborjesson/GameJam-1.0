/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.states;


import gamejam10.*;
import gamejam10.audio.*;
import gamejam10.enums.*;
import gamejam10.level.*;
import gamejam10.menu.*;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.*;

    
/**
 *
 * @author gregof
 */
public class MenuState extends BasicGameState {
    
    private AudioPlayer audioPlayer;
    
    private Menu menu = null;
    
    @Override
    public int getID() {
        return States.MENU.getID();
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    	// build menu
    	menu = new Menu(null);
    	menu.addMenuAction("RESUME", new MenuActionEnterState(States.GAME.getID(), 1));
    	menu.addMenuAction("NEW GAME", new MenuActionEnterState(States.GAME.getID(), 0));
    	menu.addMenuAction("CREDITS", new MenuActionEnterState(States.CREDITS.getID(), 0));
    	menu.addMenuAction("EXIT", new MenuActionEnterState(States.EXIT.getID(), 0));
    	
    	menu.setSelectedItem(1);
        
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
		
		menu.getItem(menu.getItemIndex("RESUME")).setEnabled(gs.isRunning());
		
		for (int i = 0; i < menu.getNumItems(); ++i) {
			MenuItem item = menu.getItem(i);
			String name = item.getName();
			if (menu.getSelectedItem() == i) {
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
			Tools.drawStringCentered(g, screenWidth, y+height*i, name);
		}
		
		g.setColor(Color.white);
		Tools.drawStringCentered(g, screenWidth, 300, "Music copyright?");
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
        	exitGame(game);
        } else if (input.isKeyPressed(Input.KEY_DOWN) || isControllerPressed("down", input) ) {
        	menu.nextItem();
        } else if (input.isKeyPressed(Input.KEY_UP) || isControllerPressed("up", input) ) {
        	menu.previousItem();
        } else if (input.isKeyPressed(Input.KEY_SPACE) || input.isKeyPressed(Input.KEY_ENTER) || isControllerPressed("a", input) )  {
        	MenuItem i = menu.getItem(menu.getSelectedItem());
        	if (i.isEnabled()) {
	        	MenuAction a = i.getAction();
	        	if (a instanceof MenuActionEnterState) {
	        		MenuActionEnterState actionState = (MenuActionEnterState)a;
	        		if (actionState.getStateId() == States.GAME.getID()) {
	                    audioPlayer.playMusic(MusicType.GAME, 0.3f);
	                    
	                    // play game, initialize first level
	                    if (actionState.getType() == 0) {
		                    GameState gs = (GameState)game.getState(States.GAME.getID());
		                    LevelOrder lo = LevelOrder.getInstance();
		                    lo.reset();
		                    gs.initializeLevel(lo.getNextLevel());
	                    }
	        		}
	                game.enterState(actionState.getStateId(), actionState.getLeaveTransition(), actionState.getEnterTransition());
	        	} else if (a instanceof MenuActionEnterMenu) {
	        		MenuActionEnterMenu menuState = (MenuActionEnterMenu)a;
	        		menu = menuState.getMenu();
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
}
