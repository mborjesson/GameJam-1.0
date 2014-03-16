package gamejam10.text;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public abstract class TextRenderer {
	
	private SpriteSheet spriteSheet;
	
	public TextRenderer(String sheetname) {
		
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
		
		System.out.println("cp: " + cp);
		return cp;
	}
	
	
	
	public void renderText(Graphics g, String text, float x, float y, int charPerLine) {
		
		int charCount =0;
		for (int i =0; i < text.length(); i++) {
			int asciiValue = getAsciiValue(text.charAt(i)) -32; 
			int spriteX = asciiValue / 16;
			
			
			int spriteY = asciiValue % 16;
			
			Image img = spriteSheet.getSprite(spriteY, spriteX);
//			System.out.println("img: " + img + ", x: " + x + ", y: " + y);
			if (charCount > charPerLine) {
				y += 25;
				charCount = 0;
				x = (x - (charPerLine * 32)) - 32;
			}
			
			g.drawImage(img, x + (i*32), y);
			charCount++;
		}
			
//		int asciiValue = getAsciiValue("A");
//		
//		int spriteX = asciiValue / 16;
//		int spriteY = asciiValue % 16;
//		
////		System.out.println(spriteX);
////		System.out.println(spriteY);
//		
//		Image img = spriteSheet.getSprite(spriteY, spriteX);
////		System.out.println("img: " + img + ", x: " + x + ", y: " + y);
//		g.drawImage(img, x, y);
		
		
	}
	

}
