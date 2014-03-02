package gamejam10.sun;

public class Sun {
	private int maxTime = 0;
	private int currentTime = 0;
	
	private float radius;
	
	/**
	 * 
	 * @param initialTime in ms
	 */
	public Sun(int initialTime) {
		maxTime = initialTime;
		currentTime = 0;
	}
	
	public void update(int frameTime) {
		currentTime += frameTime;
		//System.out.println(currentTime);
		
		radius = (float) ( 40 + 2 * Math.sin(currentTime / 500.0) );
		
	}
	
	public long getRemainingTime() {
		return maxTime-currentTime;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public float getSunColor() {
		return Math.min(1f, Math.max((float)(maxTime-currentTime)/maxTime, 0f));
	}
	
	public float getSunPositionX() {
		return Math.min((float)currentTime/maxTime, 1f);
	}
	
	public float getSunPositionY() {
		return (float)Math.sin((Math.PI+getSunPositionX()*Math.PI)*0.5f);
	}
}
