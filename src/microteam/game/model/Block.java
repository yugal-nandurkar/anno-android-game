package microteam.game.model;

import java.awt.Rectangle;

import microteam.framework.util.RandomNumberGenerator;

public class Block {
	private float x, y;
	private int width, height;
	private Rectangle rect;
	private boolean visible;
	
	private static final int UPPER_Y = 275;
	private static final int LOWER_Y = 355;
	
	public Block(float x, float y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		rect = new Rectangle((int)x, (int)y, width, height);
		visible = false;
	}
	
	//Note: Velocity value will be passed in from PlayState!
	public void update(float delta, float velX){ //update is for movement of objects
		x += velX * delta; //always add *delta for framerate-independent movement
		if (x <= -50){
			reset();
		}
		updateRect();
		
	}
	
	public void updateRect(){
		rect.setBounds((int)x, (int)y, width, height);
	}
	
	public void reset(){
		visible = true;
		//1 in 3 chance of becoming an Upper Block
		if (RandomNumberGenerator.getRandInt(3) == 0){
			y = UPPER_Y;
		} else{
			y = LOWER_Y;
		}
		
		x += 1000;
	}
	
	public void onCollide(Player p){
		visible = false; //block disappears
		p.pushBack(30); //push player back 30 pixels
	}
	
	public float getX(){
		return x;
	}

	public float getY() {
		return y;
	}

	public Rectangle getRect() {
		return rect;
	}

	public boolean isVisible() {
		return visible;
	}
	
	

}
