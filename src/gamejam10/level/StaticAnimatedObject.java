package gamejam10.level;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class StaticAnimatedObject extends LevelObject {	
	public List<SpriteAnimation> sprites = new ArrayList<SpriteAnimation>();
	
	public StaticAnimatedObject(float x, float y, float width, float height)
	{
		super(x, y, width, height);
		this.setXVelocity(0);
		this.setYVelocity(0);
		this.maximumFallSpeed = 0;
		this.onGround = true;
	}
	
	public void addSpriteAnimation(SpriteAnimation anim) {
		sprites.add(anim);
	}
	
	public SpriteAnimation addSprite(String img, int offsetX, int offsetY, int frameWidth, int frameHeight) throws SlickException{
		SpriteAnimation c = new SpriteAnimation(new Image(img));
        c.offsetX = offsetX;
        c.offsetY = offsetY;
        
        sprites.add(c);
        return c;
	}
	
	public void clearSprites() {
		sprites.clear();
	}
	
	protected void setSprite(Image i) {
    	sprites.clear();

    	SpriteAnimation c = new SpriteAnimation(i);
        c.offsetX = 0;
        c.offsetY = 0;
        
        sprites.add(c);
    }
	
	public void render(Graphics g){
        //draw a moving animation if we have one and we moved within the last 150 miliseconds
		for(SpriteAnimation c : sprites) {
			if(c.rotate) {
				float rotation = c.sprite.getRotation();
				rotation += c.rotationVelocity;
				c.sprite.setRotation(rotation);
			}
			
			c.sprite.draw(x + c.offsetX, y + c.offsetY);
		}          
    }
}