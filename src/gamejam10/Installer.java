package gamejam10;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.zip.*;

public class Installer {
	final static public String DATE = "2014-03-04";
	final static public String INSTALL_PROPS = "install.props";
	final static public File INSTALL_DIR = new File(System.getProperty("java.io.tmpdir"), "the_adventures_of_sven");
	final static public File OPTIONS_FILE = new File(INSTALL_DIR, "options.prop");
	final static public File NATIVES_DIR = new File(INSTALL_DIR, "natives");
	
	public void install() throws IOException {
		File propsFile = new File(INSTALL_DIR, INSTALL_PROPS);
		if (!INSTALL_DIR.exists()) {
			INSTALL_DIR.mkdirs();
		}
		Properties props = new Properties();

		if (propsFile.exists()) {
			try {
				InputStream in = new FileInputStream(propsFile);
				props.load(in);
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!DATE.equals(props.get("date"))) {
			System.out.println("Installing...");
			// perform install
			if (!NATIVES_DIR.exists()) {
				NATIVES_DIR.mkdirs();
			}
			InputStream in = getClass().getResourceAsStream("/natives/natives.zip");
			ZipInputStream zis = new ZipInputStream(in);
			ZipEntry entry = null;
			try {
				while ((entry = zis.getNextEntry()) != null) {
					File outFile = new File(NATIVES_DIR, entry.getName());
					System.out.println("Extracting " + entry.getName() + " to " + outFile);
					OutputStream out = null;
					try {
						out = new FileOutputStream(outFile);
						int read = 0;
						byte[] b = new byte[1<<16];
						while ((read = zis.read(b)) >= 0) {
							if (read > 0) {
								out.write(b, 0, read);
							}
						}
					} finally {
						if (out != null) {
							out.close();
						}
					}
				}
				
				props.setProperty("date", DATE);
				OutputStream out = new FileOutputStream(propsFile);
				props.store(out, null);
				out.close();
			} finally {
				try {
					zis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("Installation is up-to-date, will do nothing.");
		}
		
		addDir(NATIVES_DIR);
	}
	
	public static void addDir(File dir) throws IOException {
	    try {
	    	String s = dir.toString();
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
