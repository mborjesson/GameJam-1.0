/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10;

import gamejam10.audio.*;
import gamejam10.character.Player;
import gamejam10.enums.*;
import gamejam10.level.*;
import gamejam10.options.*;
import gamejam10.states.*;
import gamejam10.states.GameState;

import java.io.*;

import org.lwjgl.opengl.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;



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
        	app.setMouseGrabbed(true);
        }
        app.setDisplayMode(options.getWidth(), options.getHeight(), options.isFullscreen());
        app.setAlwaysRender(true);
        app.setTargetFrameRate(options.getTargetFrameRate());
        app.setVSync(options.isVSync());
        app.setShowFPS(options.isShowFPS());
        app.setMultiSample(options.getMultiSample());
        app.start();
    }
	
	static private Options options = null;

    public Main(String title) {
        super(title);
        
        try {
			options = Options.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
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
