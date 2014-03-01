/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.audio;

import gamejam10.states.GameState;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;


/**
 *
 * @author gregof
 */
public class MusicPlayer {
    
    public boolean startMenuMusic = false;
    public boolean startGameMusic = false;
    
    private static final MusicPlayer INSTANCE = new MusicPlayer();
    private Music menuMusic;
    private Music gameMusic;
    private Music jump;
    
    public static MusicPlayer getInstance() {
        return INSTANCE;
    }
    
    
    private MusicPlayer() {
        try {
            
            menuMusic = new Music("/data/music/menu.ogg");
            randomGameMusic();
            jump = new Music("data/sounds/Jump.wav");
            
            
        } catch (SlickException ex) {
            //Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * 
     */
    private void muteAll() {
        //menuMusic.fade(500, 0, true);
        //gameMusic.fade(500, 0, true);
        menuMusic.stop();
        gameMusic.stop();
    }
    
    /*
     * 
     */
    public void playMenuMusic() {
        muteAll();        
        menuMusic.loop();
    }
    
    /*
     * 
     */
    public void playGameMusic() {
        muteAll();
        gameMusic.loop();
        gameMusic.setVolume(0.2f);
    }
    
    public void playJumpSound() {
        gameMusic.pause();
        
    }
    
    public void resumeGameMusic() {
        gameMusic.resume();
        gameMusic.setVolume(0.2f);
    }
    
    /**
     * 
     */
     private void randomGameMusic() {
        
        try {
            //openingMenuMusic = new Music("/data/modar/c2k-burt_reynolds.xm");
            //openingMenuMusic = new Music("/data/modar/c2k-helly_hansen.xm");
        	gameMusic = new Music("/data/music/game.ogg");
            //openingMenuMusic = new Music("/data/modar/kirby.ogg");
        } catch (SlickException ex) {
            //Logger.getLogger(GameState.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
 
    }
    
    
    
}
