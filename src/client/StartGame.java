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
	private ViewerUsername userName = new ViewerUsername(); //Change to username instead of userName
	private ViewerMultiplayerMode multiplayerMode = new ViewerMultiplayerMode();
	private ViewerOnlineList onlineList = new ViewerOnlineList();

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
		setVisible(true);
		this.pack();
		new PlaySound().playElevatorLoop();
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
		
		userName.setController(controller); //Change to username instead of userName
		userName.setListener(this); //Change to username instead of userName
		JPanel cardUserName = new JPanel(); //Change to username instead of userName
		cardUserName.add(userName); //Change to username instead of userName
		
		multiplayerMode.setController(controller);
		multiplayerMode.setListener(this);
		JPanel cardMultiplayer = new JPanel();
		cardMultiplayer.add(multiplayerMode);
		
		onlineList.setController(controller);
		onlineList.setListener(this);
		JPanel cardOnlineList = new JPanel();
		cardOnlineList.add(onlineList);
		
		controller.setViewerGame(game);
		controller.setViewerUsername(userName); //Change to username instead of userName
		controller.setViewerMultiplayerMode(multiplayerMode);
		controller.setViewerOnlineList(onlineList);

		cards.add(cardMode, "cardMode");
		cards.add(cardDifficulty, "cardDifficulty");
		cards.add(cardCategory, "cardCategory");
		cards.add(cardGame, "cardGame");
		cards.add(cardUserName, "cardUserName"); //Change to username instead of userName
		cards.add(cardMultiplayer, "cardMultiplayerMode");
		cards.add(cardOnlineList, "cardOnlineList");
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
	public void skipToGame() {
		cardLayout.show(cards, "cardGame");
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

	@Override
	public void goBackMP() { 
		if (currentCard.equals("cardOnlineList")) {
			currentCard = "cardMultiplayerMode";
	 	} else if (currentCard.equals("cardMultiplayerMode")) {
			currentCard = "cardMode";
		} else if (currentCard.equals("cardUserName")) { //Change to username instead of userName
			currentCard = "cardMode";
		}
		cardLayout.show(cards, currentCard);
	}

	@Override
	public void nextPanelMP() {
		if (currentCard.equals("cardMode")) {
			currentCard = "cardUserName"; //Change to username instead of userName
		} else if (currentCard.equals("cardUserName")) { //Change to username instead of userName
			currentCard = "cardMultiplayerMode";
		} else if (currentCard.equals("cardMultiplayerMode")) {
			currentCard = "cardOnlineList";
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
