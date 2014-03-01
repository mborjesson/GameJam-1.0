package gamejam10.camera;

public class Camera {
	private float x;
	private float y;
	
	private float width;
	private float height;
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getMinX() {
		return x-width*0.5f;
	}
	
	public float getMinY() {
		return y-height*0.5f;
	}
	
	public float getMaxX() {
		return x+width*0.5f;
	}
	
	public float getMaxY() {
		return y+height*0.5f;
	}
}
