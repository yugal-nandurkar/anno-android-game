package microteam.game.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import microteam.game.main.GameMain;
import microteam.game.main.Resources;

public class GameOverState extends State {
	private String playerScore;
	private Font font;
	
	//pass the score into the constructor for the GameOverState
	public GameOverState(int playerScore){
		this.playerScore = playerScore + ""; // convert int to a String
		font = new Font("SansSerif", Font.BOLD, 50);
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.decode("#ffb366"));
		g.fillRect(0, 0, GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT);
		g.setColor(Color.DARK_GRAY);
		g.setFont(font);
		g.drawString("GameOver", 260, 100);
		g.drawString("Your Score: "+ playerScore, 225, 230);
		g.drawString("Press any key...", 217, 350);
	}

	@Override
	public void onClick(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		setCurrentState(new MenuState()); //reset to the menuState()
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
