package microteam.game.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import microteam.game.main.GameMain;
import microteam.game.main.Resources;
import microteam.game.model.Block;
import microteam.game.model.Cloud;
import microteam.game.model.Player;

public class PlayState extends State {
	
	private Player player;
	private ArrayList<Block> blocks;
	private Cloud cloud1, cloud2;
	
	private Font scoreFont;
	private int playerScore = 0;
	
	private static final int BLOCK_HEIGHT = 50;
	private static final int BLOCK_WIDTH = 20;
	
	private int blockSpeed = -200;
	
	private static final int PLAYER_WIDTH = 66;
	private static final int PLAYER_HEIGHT = 92;
	
	

	@Override
	public void init() {
		player = new Player(160, GameMain.GAME_HEIGHT - 45 - PLAYER_HEIGHT, PLAYER_WIDTH,PLAYER_HEIGHT);
		blocks = new ArrayList<Block>();
		cloud1 = new Cloud(100, 100);
		cloud2 = new Cloud(500, 50);
		scoreFont = new Font("SansSerif", Font.BOLD, 25);
		
		
		//only using 5 blocks- hard code this amount
		for(int i = 0; i < 5; i++){
			Block b = new Block(i * 200, GameMain.GAME_HEIGHT - 95, BLOCK_WIDTH, BLOCK_HEIGHT);
			blocks.add(b);
		}
		
	}

	@Override
	public void update(float delta) {
		
		if(!player.isAlive()){
			Resources.gameOver.play();
			setCurrentState(new GameOverState(playerScore / 100));
		} 
		//increment the score every second
		playerScore+= 1;
		
		//if player scores 500 points (therefore divisible by 500), increase the speed of the blocks
		if (playerScore %500 == 0 && blockSpeed > -280){ //if block speed is faster than -280(moving left)
			blockSpeed -= 10; //increase block speed by 10
		}
		
		//update game objects
		cloud1.update(delta);
		cloud2.update(delta);
		Resources.runAnim.update(delta);
		player.update(delta);
		updateBlocks(delta);
		
	}
	
	public void updateBlocks(float delta){
		//create for-each loop
		for(Block b : blocks){
			b.update(delta, blockSpeed);
			
			if(b.isVisible()){
				if(player.isDucked() && b.getRect().intersects(player.getDuckRect())){
					System.out.println("player hit whilst ducking");
					b.onCollide(player);
				} else if (!player.isDucked() && b.getRect().intersects(player.getRect())){
					System.out.println("player hit whilst running");
					b.onCollide(player);
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Resources.skyBlue);
		g.fillRect(0, 0, GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT);
		
		//object rendering
		renderPlayer(g);
		renderBlocks(g);
		renderSun(g);
		renderClouds(g);
		g.drawImage(Resources.grass, 0, 405, null);
		renderScore(g);
		
	}
	
	private void renderScore(Graphics g){
		g.setFont(scoreFont);
		g.setColor(Color.GRAY);
		g.drawString(playerScore/100 + "", 20, 30); //(x,y) location
	}
	
	public void renderPlayer(Graphics g){
		if(player.isGrounded()){
			if(player.isDucked()){
				g.drawImage(Resources.duck, (int)player.getX(), (int)player.getY(), null);
			} else {
				Resources.runAnim.render(g, (int)player.getX(), (int)player.getY(), player.getWidth(),player.getHeight());
			}
		} else {
			g.drawImage(Resources.jump, (int)player.getX(), (int)player.getY(), null);
		}
	}
	
	private void renderBlocks(Graphics g){
		//for each Block object 'b' in the ArrayList 'blocks' 
		for(Block b : blocks){
			if(b.isVisible()){ //if the visible variable == true
				g.drawImage(Resources.block, (int)b.getX(), (int)b.getY(), BLOCK_WIDTH, BLOCK_HEIGHT, null);	
			}
		}
	}
	
	private void renderSun(Graphics g){
		g.setColor(Color.ORANGE);
		g.fillOval(715, -85, 170, 170);
		g.setColor(Color.YELLOW);
		g.fillOval(725, -75, 150, 150);
	}
	
	private void renderClouds(Graphics g){
		g.drawImage(Resources.cloud1, (int)cloud1.getX(), (int)cloud1.getY(), 100, 60, null); //cast from float to int
		g.drawImage(Resources.cloud2, (int)cloud2.getX(), (int)cloud2.getY(), 100, 60, null);
	}
	


	@Override
	public void onClick(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			player.jump();
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			player.duck();
			
		}
		
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
