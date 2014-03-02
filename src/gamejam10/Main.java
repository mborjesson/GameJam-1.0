/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10;

import java.io.*;

import gamejam10.audio.*;
import gamejam10.enums.*;
import gamejam10.level.*;
import gamejam10.options.Options;
import gamejam10.states.CreditsState;
import gamejam10.states.ExitState;
import gamejam10.states.GameState;
import gamejam10.states.LevelCompletedState;
import gamejam10.states.MenuState;

import org.lwjgl.opengl.*;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;



/**
 *
 * @author gregof
 */
public class Main extends StateBasedGame {
    public static void main(String[] args) throws SlickException {
    	Display.setLocation(100, 100);
    	Main main = new Main(Constants.GAME_NAME);
        AppGameContainer app = new AppGameContainer(main);
        Options options = Main.getOptions();
        if (options.isFullscreen()) {
        	options.setWidth(app.getScreenWidth());
        	options.setHeight(app.getScreenHeight());
        }
        app.setDisplayMode(options.getWidth(), options.getHeight(), options.isFullscreen());
        app.setAlwaysRender(true);
        app.setTargetFrameRate(options.getTargetFrameRate());
        app.setVSync(options.isVSync());
        app.setShowFPS(options.isShowFPS());
        app.start();
    }
	
	static private Options options = new Options();

    public Main(String title) {
        super(title);
        
        AudioPlayer.getInstance().setEnabled(options.isSoundEnabled());
        AudioPlayer.getInstance().initialize();
        
        try {
			LevelOrder.getInstance().initialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new MenuState());
        this.addState(new GameState());
        this.addState(new LevelCompletedState());
        this.addState(new ExitState());
        this.addState(new CreditsState());
        
        enterState(States.MENU.getID());
    }
    
    public static Options getOptions() {
		return options;
	}
}
