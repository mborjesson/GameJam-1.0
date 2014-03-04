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
    private final Map<MusicType, Music> musicMap = new HashMap<MusicType, Music>();
    private final Map<SoundType, Sound> soundMap = new HashMap<SoundType, Sound>();

    private boolean enabled = true;
    private boolean initialized = false;
    
    private float musicVolume = 1;
    private float soundVolume = 1;
    
    public static AudioPlayer getInstance() {
        return INSTANCE;
    }
    
    
    private AudioPlayer() {
        
    }
    
    public void initialize() {
    	if (initialized) {
    		return;
    	}
		initialized = true;
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

    private void stopMusic() {
    	if (!enabled) {
    		return;
    	}
    	for (Music m : musicMap.values()) {
    		m.stop();
    	}
    }
    
    private void stopSound() {
    	if (!enabled) {
    		return;
    	}
    	
    	for (Sound s : soundMap.values()) {
    		s.stop();
    	}
    }
    
    private void stopAllSounds() {
    	stopMusic();
    	stopSound();
    }
    
    public void playMusic(MusicType type) {
    	playMusic(type, 1);
    }
    
    public void playMusic(MusicType type, float volume) {
    	if (!enabled) {
    		return;
    	}
        stopMusic();
        musicMap.get(type).loop();
        musicMap.get(type).setVolume(volume*musicVolume);
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
    	soundMap.get(type).play(pitch, volume*soundVolume);
    }
    
    public void setEnabled(boolean enabled) {
    	if (this.enabled && !enabled) {
    		stopAllSounds();
    	} else if (!this.enabled && enabled && !initialized) {
    		initialize();
    	}
		this.enabled = enabled;
	}
    
    public boolean isEnabled() {
		return enabled;
	}
    
    public void setSoundVolume(float soundVolume) {
		this.soundVolume = soundVolume;
	}
    
    public float getSoundVolume() {
		return soundVolume;
	}
    
    public void setMusicVolume(float musicVolume) {
		this.musicVolume = musicVolume;
		for (Music music : musicMap.values()) {
			if (music.playing()) {
				music.setVolume(musicVolume);
			}
		}
	}
    
    public float getMusicVolume() {
		return musicVolume;
	}
}
