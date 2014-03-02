/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.states;


import gamejam10.*;
import gamejam10.audio.*;
import gamejam10.enums.*;
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
    	menu.addMenuAction("PLAY", new MenuActionEnterState(States.GAME.getID()));
    	menu.addMenuAction("CREDITS", new MenuActionEnterState(States.CREDITS.getID()));
    	menu.addMenuAction("EXIT", new MenuActionEnterState(States.EXIT.getID()));
        
        audioPlayer = AudioPlayer.getInstance();
        audioPlayer.playMusic(MusicType.MENU, 1);
        
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setBackground(Color.black);
		g.clear();
		
		float screenWidth = 640f;
		float screenHeight = (float)(screenWidth/Main.getOptions().getAspectRatio());
		float scaleX = Main.getOptions().getWidth()/screenWidth;
		float scaleY = Main.getOptions().getHeight()/screenHeight;
		g.scale(scaleX, scaleY);
		
		float x = 40;
		float y = 20;
		float height = 20;
		
		for (int i = 0; i < menu.getNumItems(); ++i) {
			if (menu.getSelectedItem() == i) {
				g.setColor(Color.white);
			} else {
				g.setColor(Color.gray);
			}
			g.drawString(menu.getItemName(i), x, y+height*i);
		}
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
        	MenuAction a = menu.getAction(menu.getSelectedItem());
        	if (a instanceof MenuActionEnterState) {
        		MenuActionEnterState actionState = (MenuActionEnterState)a;
        		if (actionState.getStateId() == States.GAME.getID()) {
                    audioPlayer.playMusic(MusicType.GAME, 0.3f);
        		}
                game.enterState(actionState.getStateId(), actionState.getLeaveTransition(), actionState.getEnterTransition());
        	} else if (a instanceof MenuActionEnterMenu) {
        		MenuActionEnterMenu menuState = (MenuActionEnterMenu)a;
        		menu = menuState.getMenu();
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
