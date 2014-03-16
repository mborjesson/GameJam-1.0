package gamejam10.text;

import gamejam10.level.StaticObject;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class TextBox extends StaticObject {

	
	private SpriteSheet spriteSheet;
	private String text; 
	protected long visibilityTimer = System.currentTimeMillis();
	
	public TextBox(String name, String text, Animation animationImage, boolean collidable,
			boolean visible, float x, float y, float w, float h) {
		super(name, animationImage, collidable, visible, x, y, w, h);
		// TODO Auto-generated constructor stub
		this.text = text;
		this.name = name;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.collidable = collidable;
		this.visible = visible;
		this.animation = animationImage;
		
		try {
			spriteSheet = new SpriteSheet(
					"data/images/text2.png",
					32, 32);
		} catch (SlickException ex) {
			ex.printStackTrace();
		}
			
	}
	
	
	public int getAsciiValue(Character cr) {
		
		String str = "" + cr;
		int cp = String.valueOf(str).codePointAt(0);
		// System.out.println("cp: " + cp);
		return cp;
	}
	
	
	private void checkVisibilityTimer() {
		long now = System.currentTimeMillis();
		
//		System.out.println("name: " + name);
//		if (!this.name.contains("trigger")) {
//			System.out.println("now: " + now);
//			System.out.println("this.visibilityTimer: " + this.visibilityTimer);
//			System.out.println("delta: " + (now - this.visibilityTimer));
//		}
		
		if (now > this.visibilityTimer) {
			this.visible = false;
		} else {
			this.visible = true;
		}
		
	}
	
	public void setVisibilityTimer(long secs) {
		this.visibilityTimer = System.currentTimeMillis() + (secs * 1000l);
	}

 

	@Override
	public void render(Graphics g) {
		
		
	//	super.render(g);
		checkVisibilityTimer();
	if (visible) {	
		int charsPerLine = 20;
		g.setColor(Color.darkGray);
		
		g.fillRoundRect(x, y, w, h, 5);
		//g.fillRect(x, y, w, h);

		g.setColor(Color.lightGray);
		
		float textX = x;
		float textY = y;
		//	g.scale(0.3f, 0.3f);
		int numLines = (text.length() / charsPerLine) + 1;
		//System.out.println("numLines: " + numLines);
		for (int i=0;i<numLines;i++) {
			if (i == numLines-1) {
				g.drawString((text.substring(i*charsPerLine, text.length())).trim(), textX, textY);
			} else {
				g.drawString((text.substring(i*charsPerLine, (i*charsPerLine)+charsPerLine)).trim() + "-", textX, textY);
			}
			
			
			textY +=12;
		}
		
	}	
		
		
// Stuff för graphics sprite, orkade inte leta upp en med mindre upplösning än 32x32 påer char, vilket är way för stort  // gregof
//		g.scale(0.3f, 0.3f);
//		int charCount =0;
//		
//		for (int i =0; i < text.length(); i++) {
//			int asciiValue = getAsciiValue(text.charAt(i)) -32; 
//			int spriteX = asciiValue / 16;
//			
//			int spriteY = asciiValue % 16;
//			
//			Image img = spriteSheet.getSprite(spriteY, spriteX);
////			System.out.println("img: " + img + ", x: " + x + ", y: " + y);
//			if (charCount > charsPerLine) {
//				textY += 25;
//				charCount = 0;
//				textX = (textX - (charsPerLine * 32)) - 32;
//			}
//			
//			g.drawImage(img, textX + (i*32), textY);
//			charCount++;
//		}
			
		
//	 g.resetTransform(); 

		
	}
	
	

}
