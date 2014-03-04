package gamejam10.level;

import org.newdawn.slick.*;

public class WindmillObject extends StaticAnimatedObject {
	public WindmillObject(float x, float y) throws SlickException
	{
		super(x, y, 77, 90);
		
		// Stäng av collision detection
		//this.boundingShape.setCollisionState(true);
		
		// Sätt sprites
		this.setSprite(new Image("data/images/windmill-base.png"));
		SpriteAnimation sa = new SpriteAnimation(new Image("data/images/windmill-blades.png"));
		sa.offsetX = -5;
		sa.offsetY = -23;
		sa.rotate = true;
		sa.rotationVelocity = 75f;
		this.addSpriteAnimation(sa);
	}
}
