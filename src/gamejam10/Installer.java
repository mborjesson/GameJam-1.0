package gamejam10;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class Installer {
	final static public String DATE = "2014-03-04";
	final static public String INSTALL_PROPS = "install.props";
	final static public String NATIVES_DIR = "natives";
	final static public String INSTALL_DIR = ".";
	
	private final File installDir;
	
	public Installer() {
		installDir = new File(INSTALL_DIR);
	}
	
	public void install() {
		File f = new File(installDir, INSTALL_PROPS);
		Properties props = new Properties();

		if (f.exists()) {
			try {
				InputStream in = new FileInputStream(f);
				props.load(in);
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!DATE.equals(props.get("date"))) {
			// perform install
			
		}
	}
	
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
	

}
