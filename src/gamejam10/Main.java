/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10;

import gamejam10.states.GameState;
import gamejam10.states.MenuState;

import java.io.File;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;



/**
 *
 * @author gregof
 */
public class Main extends StateBasedGame {

    //set the window width and then the height according to a aspect ratio
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGTH = WINDOW_WIDTH / 16 * 9;
    public static final boolean FULLSCREEN = false;
    //1280x720 is our base, we use 32x32 tiles but we want it to be 40x40 at 1280x720
    //so our base scale is not 1 but 1.25 actually
    public static final float SCALE = (float) (1.25 * ((double) WINDOW_WIDTH / 1280));
    public static final String GAME_NAME = "SlickScrollerTest";
    public static final int STATE_MENU = 0;
    public static final int STATE_GAME = 1;
    public static final int STATE_GAME_OVER = 2;
    
      
    public Main(String title) {
        super(title);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SlickException {
        
//        
//           System.out.println("Trying to play music");
//        //Music openingMenuMusic = new Music("/data/modar/c2k-commando2000theme.xm");
//        //Music openingMenuMusic = new Music("/data/modar/c2k-burt_reynolds.xm");
//        //Music openingMenuMusic = new Music("/data/modar/c2k-helly_hansen.xm");
//        Music openingMenuMusic = new Music("/data/modar/c2k-lock_and_load.xm");
//       // Music openingMenuMusic = new Music("/data/modar/kirby.ogg");
// 
//        openingMenuMusic.loop();
        
        
//        System.setProperty("java.library.path", "lib");
//        System.setProperty("org.lwjgl.librarypath", new File("lib/natives").getAbsolutePath());

        AppGameContainer app = new AppGameContainer(new Main("SlickScrollerTest"));
        app.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGTH, FULLSCREEN);
        app.setAlwaysRender(true);
        app.setTargetFrameRate(60);
        app.setShowFPS(false);
        app.start();
        
       
        
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new MenuState());
        this.addState(new GameState());
        // this.addState(new GameOverState());

    }
}
