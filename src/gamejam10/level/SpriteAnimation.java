package gamejam10.level;

import org.newdawn.slick.*;

public class SpriteAnimation {
	public float offsetX;
	public float offsetY;
	public Image sprite;
	public boolean rotate;
	public float rotationVelocity;
	
	public SpriteAnimation(Image img) {
		img.setFilter(Image.FILTER_NEAREST);
		sprite = img;
	}
}
