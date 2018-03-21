package client;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.*;

public class StartGame extends JFrame implements ContinueListener {
	CardLayout cardLayout = new CardLayout();
	private JPanel cards = new JPanel(cardLayout);
	private String currentCard;
	
	private Controller controller;
	private ViewerSelectMode selectMode = new ViewerSelectMode();
	private ViewerSelectDifficulty selectDifficulty = new ViewerSelectDifficulty();
	private ViewerSelectCategory selectCategory = new ViewerSelectCategory();
	private ViewerGame game = new ViewerGame();

	public StartGame(Controller controller) {
		this.controller = controller;
		setSize(new Dimension(1200, 800));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Hangman");
		setResizable(false);

		setupCards();
		currentCard = "cardMode";
		cardLayout.show(cards, currentCard);
		getContentPane().add(cards);
		this.pack();
		setVisible(true);
	}

	private void setupCards() {
		selectMode.setController(controller);
		selectMode.setListener(this);
		JPanel cardMode = new JPanel();
		cardMode.add(selectMode);

		selectDifficulty.setController(controller);
		selectDifficulty.setListener(this);
		JPanel cardDifficulty = new JPanel();
		cardDifficulty.add(selectDifficulty);

		selectCategory.setController(controller);
		selectCategory.setListener(this);
		JPanel cardCategory = new JPanel();
		cardCategory.add(selectCategory);

		game.setController(controller);
		game.setListener(this);
		JPanel cardGame = new JPanel();
		cardGame.add(game);

		cards.add(cardMode, "cardMode");
		cards.add(cardDifficulty, "cardDifficulty");
		cards.add(cardCategory, "cardCategory");
		cards.add(cardGame, "cardGame");
	}

	@Override
	public void nextPanel() {
		if (currentCard.equals("cardMode")) {
			currentCard = "cardDifficulty";
		} else if (currentCard.equals("cardDifficulty")) {
			currentCard = "cardCategory";
		} else if (currentCard.equals("cardCategory")) {
			currentCard = "cardGame";
		}
		
		cardLayout.show(cards, currentCard);
	}

	@Override
	public void goBack() {
		if (currentCard.equals("cardGame")) {
			currentCard = "cardCategory";
		} else if (currentCard.equals("cardCategory")) {
			currentCard = "cardDifficulty";
		} else if (currentCard.equals("cardDifficulty")) {
			currentCard = "cardMode";
		}
		
		cardLayout.show(cards, currentCard);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new StartGame(new Controller());
			}	
		});
	}
}
