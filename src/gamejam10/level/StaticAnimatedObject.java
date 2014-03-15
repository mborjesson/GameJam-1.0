package gamejam10.level;

import java.util.*;

import org.newdawn.slick.*;

public class StaticAnimatedObject extends LevelObject {	
	public List<SpriteAnimation> sprites = new ArrayList<SpriteAnimation>();
	
	public StaticAnimatedObject(String name, float x, float y, float width, float height)
	{
		super(name, x, y, width, height);
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
	
	public void update(int delta) {
		for(SpriteAnimation c : sprites) {
			if(c.rotate) {
				float rotation = c.sprite.getRotation();
				rotation += c.rotationVelocity*(delta/1000f);
				c.sprite.setRotation(rotation);
			}
		}          
	}
	
	public void render(Graphics g) {
		for(SpriteAnimation c : sprites) {
			c.sprite.draw(x + c.offsetX, y + c.offsetY);
		}          
    }
}