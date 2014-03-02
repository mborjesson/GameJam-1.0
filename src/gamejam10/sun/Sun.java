package gamejam10.sun;

public class Sun {
	private int maxTime = 0;
	private int currentTime = 0;
	
	private int radius = 40;
	
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
	}
	
	public long getRemainingTime() {
		return maxTime-currentTime;
	}
	
	public int getRadius() {
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
