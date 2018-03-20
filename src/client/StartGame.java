package client;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.*;

public class StartGame extends JFrame implements ContinueListener {
	private JPanel cards = new JPanel(new CardLayout());
	
	public StartGame() {
		setSize(new Dimension(1200, 800));
		setTitle("Hangman");
		setResizable(false);
		
		JPanel cardMode = new JPanel();
		cardMode.add(new ViewerSelectMode());
		
		JPanel cardDifficulty = new JPanel();
		cardDifficulty.add(new ViewerSelectDifficulty());
		
		JPanel cardCategory = new JPanel();
		cardCategory.add(new ViewerSelectCategory());
		
		JPanel cardGame = new JPanel();
		cardGame.add(new ViewerGame());
		
		cards.add(cardMode, "cardMode");
		cards.add(cardDifficulty, "cardDifficulty");
		cards.add(cardCategory, "cardCategory");
		cards.add(cardGame, "cardGame");
		
		this.getContentPane().add(cards);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new StartGame();
	}

	@Override
	public void nextPanel() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void goBack() {
		// TODO Auto-generated method stub		
	}

}
