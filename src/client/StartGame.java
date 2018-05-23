package client;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.File;
import java.net.MalformedURLException;
import javax.swing.*;

/**
 * This class is responsible for starting the application and contains the logic for
 * switching between the panels
 * @author Yun-Fah Chow, Elina Kock
 */
public class StartGame extends JFrame implements ContinueListener {
	CardLayout cardLayout = new CardLayout();
	private JPanel cards = new JPanel(cardLayout);
	private String currentCard;
	private Controller controller;
	private ViewerSelectMode selectMode = new ViewerSelectMode();
	private ViewerSelectDifficulty selectDifficulty = new ViewerSelectDifficulty();
	private ViewerSelectCategory selectCategory = new ViewerSelectCategory();
	private ViewerGame game = new ViewerGame();
	private ViewerUsername username = new ViewerUsername();
	private ViewerMultiplayerMode multiplayerMode = new ViewerMultiplayerMode();
	private ViewerOnlineList onlineList = new ViewerOnlineList();
	private AudioClip sound;

	/**
	 * Constructor
	 * @param controller, sets the controller
	 */
	public StartGame(Controller controller) {
		this.controller = controller;
		controller.setListener(this);
		setSize(new Dimension(1200, 800));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Hangman");
		setResizable(false);
		setupCards();
		currentCard = "cardMode";
		cardLayout.show(cards, currentCard);
		getContentPane().add(cards);
		setVisible(true);
		this.pack();
		elevator();
	}
	
	/**
	 * Method which sets up all the "cards", where cards are all the viewer panels.
	 */
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
		
		username.setController(controller); 
		username.setListener(this); 
		JPanel cardUsername = new JPanel(); 
		cardUsername.add(username); 
		
		onlineList.setController(controller);
		onlineList.setListener(this);
		JPanel cardOnlineList = new JPanel();
		cardOnlineList.add(onlineList);
		
		multiplayerMode.setController(controller);
		multiplayerMode.setViewerOnlineList(onlineList);
		multiplayerMode.setListener(this);
		JPanel cardMultiplayer = new JPanel();
		cardMultiplayer.add(multiplayerMode);
		
		controller.setViewerGame(game);
		controller.setViewerOnlineList(onlineList);

		cards.add(cardMode, "cardMode");
		cards.add(cardDifficulty, "cardDifficulty");
		cards.add(cardCategory, "cardCategory");
		cards.add(cardGame, "cardGame");
		cards.add(cardUsername, "cardUsername"); 
		cards.add(cardMultiplayer, "cardMultiplayerMode");
		cards.add(cardOnlineList, "cardOnlineList");
	}

	/**
	 * Method used to go to the next panel for singleplayer.
	 */
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
	
	/**
	 * Skips directly to the game window. 
	 */
	public void skipToGame() {
		currentCard = "cardGame";
		cardLayout.show(cards, currentCard);
	}

	/**
	 * The go-back function for singleplayer (goes back to the previous panel)
	 */
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
	
	/**
	 * The go-back function for multiplayer (goes back to a previous panel)
	 * When going back from the MultiplayerMode panel it is switched to the
	 * main menu panel.
	 */
	public void goBackMP() { 
		if (currentCard.equals("cardOnlineList")) {
			currentCard = "cardMultiplayerMode";
	 	} else if (currentCard.equals("cardMultiplayerMode")) {
			currentCard = "cardMode";
		} else if (currentCard.equals("cardUsername")) { 
			currentCard = "cardMode";
		} else if (currentCard.equals("cardGame")) {
			currentCard = "cardMultiplayerMode";
		}
		cardLayout.show(cards, currentCard);
	}
	
	/**
	 * The function to go to the next panel in multiplayer 
	 */
	public void nextPanelMP() {
		if (currentCard.equals("cardMode")) {
			currentCard = "cardUsername"; 
		} else if (currentCard.equals("cardUsername")) { 
			currentCard = "cardMultiplayerMode";
		} else if (currentCard.equals("cardMultiplayerMode")) {
			currentCard = "cardOnlineList";
		}
		cardLayout.show(cards, currentCard);
	}

	/**
	 * Method which is responsible for playing the background music in
	 * the application
	 */
	public void elevator() {
		try{
			File file = new File("files/elevator.wav");
			sound =  Applet.newAudioClip(file.toURI().toURL());
		} catch (MalformedURLException e) {}
		sound.loop();
	}
	
	/**
	 * The main method of the application, summoning the frame. 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new StartGame(new Controller()));
	}
}
