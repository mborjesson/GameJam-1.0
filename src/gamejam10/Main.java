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
import java.lang.reflect.*;

import org.lwjgl.opengl.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;



/**
 *
 * @author gregof
 */
public class Main extends StateBasedGame {
	public static void addDir(String s) throws IOException {
	    try {
	        // This enables the java.library.path to be modified at runtime
	        // From a Sun engineer at http://forums.sun.com/thread.jspa?threadID=707176
	        //
	        Field field = ClassLoader.class.getDeclaredField("usr_paths");
	        field.setAccessible(true);
	        String[] paths = (String[])field.get(null);
	        for (int i = 0; i < paths.length; i++) {
	            if (s.equals(paths[i])) {
	                return;
	            }
	        }
	        String[] tmp = new String[paths.length+1];
	        System.arraycopy(paths,0,tmp,0,paths.length);
	        tmp[paths.length] = s;
	        field.set(null,tmp);
	        System.setProperty("java.library.path", System.getProperty("java.library.path") + File.pathSeparator + s);
	    } catch (IllegalAccessException e) {
	        throw new IOException("Failed to get permissions to set library path");
	    } catch (NoSuchFieldException e) {
	        throw new IOException("Failed to get field handle to set library path");
	    }
	}
	
    public static void main(String[] args) throws SlickException {
    	try {
			addDir("./natives");
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
