package gamejam10.options;

import gamejam10.enums.*;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import org.newdawn.slick.util.*;

public class Options {
	transient private static final String REF = "/data/options.props";
	
	private String aspectRatio = AspectRatio.getDefaultAspectRatio();
	private int width = 1280;
	transient private Integer height = null;
	
	private boolean fullscreen = false;
	private boolean vsync = true;
	private int targetFrameRate = 60;
	private boolean showFPS = false;
	private boolean soundEnabled = true;
	private int multiSample = 2;
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		if (height == null) {
			height = (int)(width/AspectRatio.getAspectRatio(aspectRatio));
		}
		return height.intValue();
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
	
	public void setMultiSample(int multiSample) {
		this.multiSample = multiSample;
	}
	
	public int getMultiSample() {
		return multiSample;
	}
	
	public void write() throws IOException {
		OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(".", REF)));
		Properties props = new Properties();

		for (Field f : getClass().getDeclaredFields()) {
			int mod = f.getModifiers();
			if (!Modifier.isTransient(mod)) {
				try {
					String name = f.getName();
					String value = String.valueOf(f.get(this));
					props.setProperty(name, value);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	
		try {
			props.store(out, null);
		} finally {
			out.close();
		}
	}
	
	public static Options read() throws IOException {
		if (!ResourceLoader.resourceExists(REF)) {
			// write defaults
			Options opt = new Options();
			opt.write();
			return opt;
		}
		InputStream in = ResourceLoader.getResourceAsStream(REF);
		
		try {
			Options opt = new Options();
			Properties props = new Properties();
			props.load(in);
			for (Field f : opt.getClass().getDeclaredFields()) {
				int mod = f.getModifiers();
				if (!Modifier.isTransient(mod)) {
					try {
						String name = f.getName();
						String value = props.getProperty(name);
						Class<?> type = f.getType();
						if (type == Integer.TYPE || type == Integer.class) {
							f.setInt(opt, Integer.valueOf(value));
						} else if (type == Boolean.TYPE || type == Boolean.class) {
							f.setBoolean(opt, Boolean.valueOf(value));
						} else if (type == Float.TYPE || type == Float.class) {
							f.setFloat(opt, Float.valueOf(value));
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			return opt;
		} finally {
			in.close();
		}
	}
}
