/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.audio;

import gamejam10.enums.*;

import java.util.*;

import org.newdawn.slick.*;


/**
 *
 * @author gregof
 */
public class AudioPlayer {
    
    public boolean startMenuMusic = false;
    public boolean startGameMusic = false;
    
    private static final AudioPlayer INSTANCE = new AudioPlayer();
    private Map<MusicType, Music> musicMap = new HashMap<MusicType, Music>();
    private Map<SoundType, Sound> soundMap = new HashMap<SoundType, Sound>();
    private boolean enabled = false;
    
    public static AudioPlayer getInstance() {
        return INSTANCE;
    }
    
    
    private AudioPlayer() {
        
    }
    
    public void initialize() {
    	if (!enabled) {
    		return;
    	}
        try {
        	for (MusicType mt : MusicType.values()) {
        		musicMap.put(mt, new Music(mt.getFile()));
        	}
        	for (SoundType st : SoundType.values()) {
        		soundMap.put(st, new Sound(st.getFile()));
        	}
        } catch (SlickException ex) {
            //Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     */
    private void muteAllMusic() {
    	if (!enabled) {
    		return;
    	}
        //menuMusic.fade(500, 0, true);
        //gameMusic.fade(500, 0, true);
//        menuMusic.stop();
//        gameMusic.stop();
    	for (Music m : musicMap.values()) {
    		m.stop();
    	}
    }
    
    public void playMusic(MusicType type) {
    	playMusic(type, 1);
    }
    
    public void playMusic(MusicType type, float volume) {
    	if (!enabled) {
    		return;
    	}
        muteAllMusic();
        musicMap.get(type).loop();
        musicMap.get(type).setVolume(volume);
    }
    
    public void playSound(SoundType type) {
    	playSound(type, 1, 1);
    }
    
    public void playSound(SoundType type, float volume) {
    	playSound(type, volume, 1);
    }
    
    public void playSound(SoundType type, float volume, float pitch) {
    	if (!enabled) {
    		return;
    	}
    	soundMap.get(type).play(pitch, volume);
    }
}
