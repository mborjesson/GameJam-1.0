package gamejam10.options;

import gamejam10.enums.AspectRatio;

import java.io.Serializable;

public class Options implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String aspectRatio = AspectRatio.getDefaultAspectRatio();
	private int width = 900;
	private int height = (int)(width/AspectRatio.getAspectRatio(aspectRatio));
	private boolean fullscreen = false;
	private boolean vsync = false;
	private int targetFrameRate = 60;
	private boolean showFPS = false;
	private boolean soundEnabled = true;
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public void setAspectRatio(String aspectRatio) {
		this.aspectRatio = aspectRatio;
	}
	
	public double getAspectRatio() {
		return AspectRatio.getAspectRatio(aspectRatio);
	}

	public void setSoundEnabled(boolean soundEnabled) {
		this.soundEnabled = soundEnabled;
	}

	public boolean isSoundEnabled() {
		return soundEnabled;
	}
	
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
	
	public boolean isFullscreen() {
		return fullscreen;
	}
	
	public void setTargetFrameRate(int targetFrameRate) {
		this.targetFrameRate = targetFrameRate;
	}
	
	public int getTargetFrameRate() {
		return targetFrameRate;
	}
	
	public void setVSync(boolean vsync) {
		this.vsync = vsync;
	}
	
	public boolean isVSync() {
		return vsync;
	}
	
	public void setShowFPS(boolean showFPS) {
		this.showFPS = showFPS;
	}
	
	public boolean isShowFPS() {
		return showFPS;
	}
	
	public static void write() {
		
	}
	
	public static Options read() {
		return new Options();
	}
}
