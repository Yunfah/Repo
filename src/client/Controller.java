package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * Handles the logic on the client-side for playing a game of hangman. 
 * Handles guesses, wins, losses, and chosen game modes.
 * @author Elina Kock, Jakob Kennerberg, Yun-Fah Chow
 *
 */
public class Controller  {
	private Client client;
	private ViewerGame viewerGame;
	private ViewerOnlineList viewerOnlineList;
	private ArrayList<String> listWordsFromCategory = new ArrayList<String>();
	private ContinueListener continueListener;

	private String wordToGuess = "";
	private char[] encodedWord = null; 
	private boolean myTurn = true;

	private int modeChosen;
	public static final int SINGLE_PLAYER = 1;
	public static final int MULTIPLAYER = 2;
	private String multiplayerGameMode;

	//Represents what handicap (difficulty) the player starts with
	private int difficulty;
	public static final int EZ = 0;
	public static final int DARK_SOULS = 4;
	public static final int XTREME = 7;

	/**
	 * Sets the ViewerGame instance (the game panel) that this controller handles.
	 * @param viewer The ViewerGame instance to be handled by this controller.
	 */
	public void setViewerGame(ViewerGame viewer) {
		viewerGame = viewer;
	}

	/**
	 * Returns the ViewerGame instance that is handled by this controller.
	 * @return The ViewerGame handled by this controller.
	 */
	public ViewerGame getViewerGame() {
		return viewerGame;
	}

	/**
	 * Sets the ViewerOnlineList (panel for a list of online clients) that this 
	 * controller uses.
	 * @param viewer The ViewerOnlineList to be handled by this controller.
	 */
	public void setViewerOnlineList(ViewerOnlineList viewer) {
		viewerOnlineList = viewer; 
	}

	/**
	 * Returns the ViewerOnlineList that this controller uses.
	 * @return The ViewerOnlineList that this controller uses. 
	 */
	public ViewerOnlineList getViewerOnlineList() {
		return viewerOnlineList;
	}

	/**
	 * Sets the ContinueListener that this controller can use along with its
	 * known viewers.
	 * @param continueListener The ContinueListener that this controller will use.
	 */
	public void setListener(ContinueListener continueListener) {
		this.continueListener = continueListener;
	}

	/**
	 * Returns the instance of the ContinueListener that this controller uses.
	 * @return The ContinueListener that this controller uses. 
	 */
	public ContinueListener getListener() {
		return continueListener;
	}

	/**
	 * Sets an integer representation of whether the player has chosen single
	 * or multiplayer. Disables the "reset" and "new word" buttons if mulitplayer
	 * is chosen.
	 * @param mode Set to Controller.SINGLEPLAYER (1) or Controller.MULTIPLAYER (2)
	 */
	public void setMode(int mode) {
		this.modeChosen = mode;
		if (mode == MULTIPLAYER) {
			viewerGame.disableSpecialButtons();
		}
		else
			viewerGame.enableSpecialButtons();
	}

	/**
	 * Returns an integer representation of 
	 * @return Integer representation of the chosen game mode (single or 
	 * multi-player). 1 for single player, 2 for multiplayer.
	 */
	public int getMode() {
		return modeChosen;
	}
	
	public void setMultiplayerGameMode(String gameMode) {
		multiplayerGameMode = gameMode;
	}

	/**
	 * Sets the difficulty of this game.
	 * @param difficulty The difficulty that will be used during this round. 
	 */
	public void setDifficulty(int difficulty) {
		if (difficulty == EZ) {
			this.difficulty = EZ;
			viewerGame.setDifficulty(EZ);
		} else if (difficulty == DARK_SOULS) {
			this.difficulty = DARK_SOULS;
			viewerGame.setDifficulty(DARK_SOULS);
		} else if (difficulty == XTREME) {
			this.difficulty = XTREME; 
			viewerGame.setDifficulty(XTREME);
		} else {	//saved game loaded
			viewerGame.setDifficulty(difficulty);
		}
	}

	/**
	 * Returns the chosen difficulty as an int that is the handicap ("wrong guesses") the
	 * player starts with. 
	 * @return difficulty Amount of "wrong guesses" the game starts with in this difficulty. 
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * Reads a word from the given category and sets it up in the game window.
	 * @param filename The category-file to read a word from.
	 * @param category The name of the category.
	 */
	public void setCategoryWord(String filename, String category) {
		Random rand = new Random();
		listWordsFromCategory.clear();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8"))) {
			String word = br.readLine();
			while(word != null) {
				listWordsFromCategory.add(word);
				word = br.readLine();
			}
			int index = rand.nextInt(listWordsFromCategory.size());
			setWordToGuess(listWordsFromCategory.get(index), null);	
			viewerGame.setCategory(category);
		} catch (IOException e ) {}
	}

	/**
	 * Resets and replaces the current word with another one from the 
	 * same category. 
	 */
	public void resetCategoryWord() {
		Random rand = new Random();
		int index = rand.nextInt(listWordsFromCategory.size());
		setWordToGuess(listWordsFromCategory.get(index), null);
	}

	/**
	 * Sets the word that has to be guessed to the given String. If multiplayer
	 * is the chosen mode, the category header is also set to the chosen mode. 
	 * @param word The word that will have to be guessed during this game.
	 */
	public void setWordToGuess(String word, String gameMode) {
		wordToGuess = word.toUpperCase();
		setEncodedWordFromString(wordToGuess);
		if(modeChosen == MULTIPLAYER) {
			viewerGame.setCategory(gameMode);
			viewerGame.enableAllLetters();
			viewerGame.setDifficulty(EZ);
			continueListener.skipToGame();	
		}
	}

	/**
	 * Makes a char array the length of the word that has to be guessed and sets
	 * all non-guessed letters to '-'. 
	 * @param word The word to encode into a char array. 
	 */
	private void setEncodedWordFromString(String word) {
		word.toUpperCase();
		encodedWord = new char[word.length()];
		for (int i = 0; i < wordToGuess.length(); i++) {
			if (wordToGuess.charAt(i) == ' ') {
				encodedWord[i] = ' ';
			} else {
				encodedWord[i] = '-';
			}
		}
		viewerGame.setWord(encodedWord);
	}

	/**
	 * Sets the encoded word to the given encoded word. 
	 * Should be used to change the progress of how much
	 * has been guessed. 
	 * @param encodedWord
	 */
	public void setEncodedWord(char[] encodedWord) {
		this.encodedWord = encodedWord;
	}

	/**
	 * Checks if the given letter exists in the word. If so,
	 * shows the correctly guessed letter in all dedicated spots
	 * in the game window. Also checks if the latest guess results in
	 * a win, and switches the turns in multiplayer co-op mode.
	 * @param letter The letter guessed.
	 */
	public void checkLetter(char letter) {
		String s = String.valueOf(letter);	//String representation of the char parameter
		boolean correct = false;
		if (wordToGuess.contains(s)) {
			correct = true;
			viewerGame.toneButton(s, correct);
			for (int i = 0; i < wordToGuess.length(); i++) {
				if (wordToGuess.charAt(i) == letter) {
					encodedWord[i] = letter;
				}
			}
		} else {
			viewerGame.incrementWrongLetterCount();
			viewerGame.toneButton(s, correct);
		}
		viewerGame.addLetterGuessed(s);
		if (modeChosen == MULTIPLAYER && multiplayerGameMode.equals("co-op") && myTurn == true) {
			client.guessLetter(letter, correct);
			myTurn = false;	
			viewerGame.setTurn(false);
		} else if (modeChosen == MULTIPLAYER && multiplayerGameMode.equals("co-op") && !myTurn){
			myTurn = true;
			viewerGame.setTurn(true);
		}
		viewerGame.setWord(encodedWord);
		checkWin();
	}

	/**
	 * Checks if all letters of the word have been guessed. If so,
	 * sends a victory message the game window and the opponent if 
	 * in multiplayer mode.  
	 */
	private void checkWin() { 
		int correctLetters = 0;
		for (int i = 0; i < encodedWord.length; i++) {
			if (encodedWord[i] != '-')
				correctLetters++;
		}
		if (correctLetters == wordToGuess.length()) {
			viewerGame.setResult(true);
			if (modeChosen == MULTIPLAYER) {
				if (!viewerOnlineList.getGameMode().equals("co-op")) {
					client.win(true); //win() should tell CH to tell the other client(player) that this client won
				}
				JOptionPane.showMessageDialog(null, "Congratulations, you won! You will be sent back \nto the multiplayer menu.");
				client.finishCoOp();
				continueListener.goBackMP();
			}
		}
	}

	/**
	 * Changes the color of the button representing the guessed letter
	 * to green if the guess was correct and to red if it was incorrect.
	 * @param guessedLetter The guessed letter.
	 * @param isCorrect If the guess was correct or not. 
	 */
	public void pimpGuessedButton(char guessedLetter, boolean isCorrect) {
		String value = String.valueOf(guessedLetter);
		viewerGame.toneButton(value, isCorrect);
	}

	/**
	 * Sets the word to as if it has been completely guessed
	 * and shows it in the game window.
	 */
	public void setWordGuessed() {
		for (int i = 0; i < wordToGuess.length(); i++) {
			encodedWord[i] = wordToGuess.charAt(i);
		}
		viewerGame.setWord(encodedWord);
	}

	/**
	 * Connects the user to the server.
	 * @param username their username.
	 * @param ip the ip they connect to.
	 * @param port the port they connect to.
	 */
	public void connect(String username, String ip, int port) throws IOException {
		client = new Client(username, ip, port);
		client.setController(this);
		viewerOnlineList.setUsername("Your name: " + username);
	}

	/**
	 * Returns the Client object of this Controller.
	 * @return The Client(user) returned.
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * Updates the onlineList with its users.
	 * @param onlineList The list of all the users online.
	 */
	public void updateOnline(ArrayList<String> onlineList) {
		viewerOnlineList.updateOnlineList(onlineList);
	}

	/**
	 * Sends an invitation to another user.
	 * @param reciever the username of the reciever.
	 * @param gamemode the name of the current gamemode.
	 */
	public void sendInvite(String reciever, String gamemode) {
		System.out.println("Send invite");
		client.sendInvite(reciever, gamemode);
	}

	/**
	 * Enables or disables this controller's Client's turn.
	 * @param myTurn Set to true to enable turn, and false to disable it.
	 */
	public void setTurn(boolean myTurn) {
		this.myTurn = myTurn;
		viewerGame.setTurn(myTurn); 
	}

	/**
	 * Method which calls a method in ViewerGame, showing a message
	 */
	public void opponentLeft() {
		viewerGame.opponentLeftMessage();
	}

	/**
	 * Saves the current progress. Only one save file
	 * may exist at a time.
	 */
	public void saveGameProgress() {	//Done?
		int wrongGuesses = viewerGame.getWrongLetterCount();
		boolean[] buttonsPressed = viewerGame.getButtonsPressed();
		WordProgress newSave = new WordProgress(wordToGuess, encodedWord, wrongGuesses, buttonsPressed);

		try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(
				new FileOutputStream("files/SaveFile.dat")))) {

			System.out.println("Saving " + newSave.toString());
			oos.writeObject(newSave);
			oos.flush();

		} catch (FileNotFoundException e) {
			System.out.println("File not found while saving.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads the latest save file and sets up a game from it.
	 */
	public void loadSaveFile() {	//NOT DONE
		//Load savefile and set up a single player game from it.
		try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(
				new FileInputStream("files/SaveFile.dat")))) {

			WordProgress progress = (WordProgress)ois.readObject();
			String word = progress.getWordToGuess();
			char[] encoded = progress.getWordProgress();
			int mistakes = progress.getWrongLetterCount();		

			setWordToGuess(word, null);
			setEncodedWord(encoded);
			setDifficulty(mistakes);

			System.out.println(progress.toString());

		} catch (FileNotFoundException e) {
			System.out.println("File not found while loading.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}