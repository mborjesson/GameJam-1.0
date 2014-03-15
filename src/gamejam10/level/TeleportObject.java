package gamejam10.level;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TeleportObject extends StaticAnimatedObject {
	
	private long timeSinceUsed = System.currentTimeMillis();
	
	public TeleportObject(String name, float x, float y, float width,
			float height) {
		
		
		super(name, x, y, width, height);

		//this.setSprite(new Image("data/images/windmill-base.png"));
		SpriteAnimation sa = null;
		try {
			sa = new SpriteAnimation(new Image("data/images/portal_small.png"));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sa.offsetX = 0;
		sa.offsetY = 0;
		sa.rotate = true;
		sa.rotationVelocity = 150f;
		this.addSpriteAnimation(sa);
		
		
	}

	
	public long getTimeSinceUsed() {
		return timeSinceUsed;
	}

	public void setTimeSinceUsed(long timeSinceUsed) {
		this.timeSinceUsed = timeSinceUsed;
	}

	

}
