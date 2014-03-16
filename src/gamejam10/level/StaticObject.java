package gamejam10.level;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

import gamejam10.physics.*;

/**
 * 
 * @author gregof
 */
public abstract class StaticObject {

	protected String name;
	protected float x;
	protected float y;
	protected float w;
	protected float h;
	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}

	protected BoundingShape boundingShape;
	protected boolean collidable = false;
	protected boolean visible = false;
	protected Animation animation;
	

	public StaticObject(String name, Animation animationImage, boolean collidable, boolean visible, float x, float y, float w, float h) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.collidable = collidable;
		this.visible = visible;
		this.animation = animationImage;

		// default bounding shape is a 32 by 32 box
		boundingShape = new AABoundingRect(x, y, w, h);

	}
	
	public void render (Graphics g) {
		
		if (animation != null && this.visible) {
			g.drawAnimation(animation, x, y);
		}
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float f) {
		x = f;
		updateBoundingShape();
	}

	public void setY(float f) {
		y = f;
		updateBoundingShape();
	}

	public void updateBoundingShape() {
		boundingShape.updatePosition(x, y);
	}

	public BoundingShape getBoundingShape() {
		return boundingShape;
	}

	public boolean isCollidable() {
		return collidable;
	}

	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
	
	
}

