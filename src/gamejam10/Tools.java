package gamejam10;

import org.newdawn.slick.*;

public class Tools {
	static public void drawStringCentered(Graphics g, float screenWidth, float y, String str) {
		g.drawString(str, (screenWidth-g.getFont().getWidth(str))*0.5f, y);
	}

	static public void setScale(Graphics g, float screenWidth) {
		float screenHeight = (float)(screenWidth/Main.getOptions().getAspectRatio());
		float scaleX = Main.getOptions().getWidth()/screenWidth;
		float scaleY = Main.getOptions().getHeight()/screenHeight;
		g.scale(scaleX, scaleY);
	}
}
