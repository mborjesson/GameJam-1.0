/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10;

import gamejam10.audio.*;
import gamejam10.enums.*;
import gamejam10.options.Options;
import gamejam10.states.GameState;
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
    	Main main = new Main("SlickScrollerTest");
        AppGameContainer app = new AppGameContainer(main);
        Options options = Main.getOptions();
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
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new MenuState());
        this.addState(new GameState());
        enterState(States.MENU.getID());
        this.addState(new ExitState());
    }
    
    public static Options getOptions() {
		return options;
	}
}
