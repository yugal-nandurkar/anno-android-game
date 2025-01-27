package microteam.game.model;

import microteam.framework.util.RandomNumberGenerator;

public class Cloud {
	
	private float x, y;
	private static final int VEL_X = -15;
	
	public Cloud(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void update(float delta){
		x += VEL_X * delta; //framerate-independent movement
		
		if(x <= -200){
			//reset to the right
			x += 1000;
			y = RandomNumberGenerator.getRandIntBetween(20, 100); //random y int between 20-100
		}
		
	}
	
	public float getX(){
		return x;
	}

	public float getY() {
		return y;
	}

}
