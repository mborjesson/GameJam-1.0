package gamejam10.level;

import java.io.*;
import java.util.*;

import org.newdawn.slick.util.*;

public class LevelOrder {
	static final LevelOrder instance = new LevelOrder();
	public static LevelOrder getInstance() {
		return instance;
	}
	
	private final List<String> levelList = new ArrayList<String>();
	private int currentLevel = 0;
	private String currentLevelName = null;

	public void initialize() throws IOException {
		InputStream in = ResourceLoader.getResourceAsStream("data/maps/map_order.list");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		String s = null;
		while ((s = br.readLine()) != null) {
			levelList.add(s);
		}
		
		br.close();
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public String getNextLevel() {
		if (currentLevel >= levelList.size()) {
			currentLevelName = null;
		} else {
			currentLevelName = levelList.get(currentLevel++);
		}
		return currentLevelName;
	}
	
	public void reset() {
		currentLevel = 0;
	}
	
	public String getFirstLevel() {
		return levelList.get(0);
	}
	
	public String getCurrentLevel() {
		return currentLevelName;
	}
}
