/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10;

import gamejam10.audio.*;
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
		Installer i = new Installer();
		try {
			i.install();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	Display.setLocation(0, 0);
    	Main main = new Main(Constants.GAME_NAME);
        AppGameContainer app = new AppGameContainer(main);
        Options options = Options.getInstance();
        if (options.isFullscreen()) {
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
	
    public Main(String title) {
        super(title);
        
        AudioPlayer.getInstance().setEnabled(Options.getInstance().isSoundEnabled());
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
}
