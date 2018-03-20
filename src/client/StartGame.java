package client;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.*;

public class StartGame extends JFrame implements ContinueListener {
	private JPanel cards = new JPanel(new CardLayout());
	CardLayout cardLayout = (CardLayout)cards.getLayout();
	private String currentCard = "cardMode";

	public StartGame() {
		setSize(new Dimension(1200, 800));
		setTitle("Hangman");
		setResizable(false);
		setupCards();
		getContentPane().add(cards);
		setVisible(true);
	}

	private void setupCards() {
		ViewerSelectMode selectMode = new ViewerSelectMode();
		selectMode.addListener(this);
		JPanel cardMode = new JPanel();
		cardMode.add(selectMode);

		ViewerSelectDifficulty selectDifficulty = new ViewerSelectDifficulty();
		selectMode.addListener(this);
		JPanel cardDifficulty = new JPanel();
		cardDifficulty.add(selectDifficulty);

		ViewerSelectCategory selectCategory = new ViewerSelectCategory();
		selectMode.addListener(this);
		JPanel cardCategory = new JPanel();
		cardCategory.add(selectCategory);

		ViewerGame game = new ViewerGame();
		selectMode.addListener(this);
		JPanel cardGame = new JPanel();
		cardGame.add(game);

		cards.add(cardMode, "cardMode");
		cards.add(cardDifficulty, "cardDifficulty");
		cards.add(cardCategory, "cardCategory");
		cards.add(cardGame, "cardGame");
	}

	@Override
	public void nextPanel() {
		switch (currentCard) {
		case "cardMode" : 
			cardLayout.show(cards, "cardDifficulty");
			currentCard = "cardDifficulty";
			break;
		case "cardDifficulty" : 
			cardLayout.show(cards, "cardCategory");
			currentCard = "cardCategory";
			break;
		case "cardCategory" : 
			cardLayout.show(cards, "cardGame");
			currentCard = "cardGame";
			break;
		}
	}

	@Override
	public void goBack() {
		switch (currentCard) {
		case "cardGame" : cardLayout.show(cards, "cardCategory");
		break;
		case "cardCategory" : cardLayout.show(cards, "cardDifficulty");
		break;
		case "cardDifficulty" : cardLayout.show(cards, "cardMode");
		break;
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new StartGame();
			}	
		});
	}
}
