package gamejam10.options;

import gamejam10.*;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

public class Options {
	transient static private Options options = null;
	transient private Options writableOptions = null;
	
	static public Options getInstance() {
		if (options == null) {
			try {
				options = read();
				try {
					options.writableOptions = (Options)options.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (options == null) {
				options = new Options();
			}
		}
		return options;
	}
	
	static public Options getWritableInstance() {
		return getInstance().writableOptions;
	}
	
	transient private int configVersion = 2;
	private int width = 1280;
	private int height = 720;
	private boolean fullscreen = false;
	private boolean vsync = true;
	private int targetFrameRate = 0;
	private boolean showFPS = false;
	private boolean soundEnabled = true;
	private float musicVolume = 1;
	private float soundVolume = 1;
	private int multiSample = 2;
	private boolean shadersEnabled = true;
	
	private Options() {}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getHeight() {
		return height;
	}

	public double getAspectRatio() {
		return width/(double)height;
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
	
	public void setShadersEnabled(boolean shadersEnabled) {
		this.shadersEnabled = shadersEnabled;
	}
	
	public boolean isShadersEnabled() {
		return shadersEnabled;
	}
	
	public void setMusicVolume(float musicVolume) {
		this.musicVolume = musicVolume;
	}
	
	public float getMusicVolume() {
		return musicVolume;
	}
	
	public void setSoundVolume(float soundVolume) {
		this.soundVolume = soundVolume;
	}
	
	public float getSoundVolume() {
		return soundVolume;
	}
	
	public void write() throws IOException {
		File file = Installer.OPTIONS_FILE;
		System.out.println("Writing config to " + file);
		OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		Properties props = new Properties();
		
		props.setProperty("configVersion", String.valueOf(configVersion));

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
		if (!Installer.OPTIONS_FILE.exists()) {
			// write defaults
			Options opt = new Options();
			opt.write();
			return opt;
		}
		InputStream in = new FileInputStream(Installer.OPTIONS_FILE);
		
		try {
			Options opt = new Options();
			Properties props = new Properties();
			props.load(in);
			String versionStr = props.getProperty("configVersion");
			int version = 0;
			if (versionStr != null) {
				try {
					version = Integer.valueOf(versionStr);
				} catch (NumberFormatException e) {
				}
			}
			if (version == opt.configVersion) {
				for (Field f : opt.getClass().getDeclaredFields()) {
					int mod = f.getModifiers();
					if (!Modifier.isTransient(mod)) {
						try {
							String name = f.getName();
							String value = props.getProperty(name);
							if (value == null) {
								continue;
							}
							Class<?> type = f.getType();
							if (type == Integer.TYPE || type == Integer.class) {
								try {
									f.setInt(opt, Integer.valueOf(value));
								} catch (NumberFormatException e) {
									System.out.println("Could not read value " + value);
								}
							} else if (type == Boolean.TYPE || type == Boolean.class) {
								f.setBoolean(opt, Boolean.valueOf(value));
							} else if (type == Float.TYPE || type == Float.class) {
								try {
									f.setFloat(opt, Float.valueOf(value));
								} catch (NumberFormatException e) {
									System.out.println("Could not read value " + value);
								}
							} else if (type == String.class) {
								f.set(opt, value);
							}
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				System.out.println("Wrong version, using default values");
				try {
					opt.write();
				} catch (IOException e) {
				}
			}
			return opt;
		} finally {
			in.close();
		}
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Options opt = new Options();
		for (Field f : opt.getClass().getDeclaredFields()) {
			int mod = f.getModifiers();
			if (!Modifier.isStatic(mod) && !Modifier.isFinal(mod)) {
				try {
					f.set(opt, f.get(this));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return opt;
	}
}
