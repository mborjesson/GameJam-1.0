package gamejam10;

import gamejam10.options.*;

import org.newdawn.slick.*;

public class Tools {
	static public void drawStringCentered(Graphics g, float screenWidth, float y, String str) {
		g.drawString(str, (screenWidth-g.getFont().getWidth(str))*0.5f, y);
	}

	static public void setScale(Graphics g, float screenWidth) {
		float screenHeight = (float)(screenWidth/Options.getInstance().getAspectRatio());
		float scaleX = Options.getInstance().getWidth()/screenWidth;
		float scaleY = Options.getInstance().getHeight()/screenHeight;
		g.scale(scaleX, scaleY);
	}
}
