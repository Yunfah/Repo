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

public class Controller  {
	private Client client;
	private ViewerGame viewerGame;
	private ViewerMultiplayerMode viewerMultiplayerMode;
	private ViewerOnlineList viewerOnlineList;
	private ArrayList<String> listWordsFromCategory = new ArrayList<String>();
	private ContinueListener continueListener;

	private String wordToGuess = "";
	private char[] encodedWord = null; 

	private int modeChosen;
	public static final int SINGLE_PLAYER = 1;
	public static final int MULTIPLAYER = 2;

	//Represents what handicap (difficulty) the player starts with
	private int difficulty;
	public static final int EZ = 0;
	public static final int DARK_SOULS = 4;
	public static final int XTREME = 7;

	public void setViewerGame(ViewerGame viewer) {
		viewerGame = viewer;
	}

	public ViewerGame getViewerGame() {
		return viewerGame;
	}

	public void setViewerMultiplayerMode(ViewerMultiplayerMode viewer) {
		viewerMultiplayerMode = viewer;
	}

	public void setViewerOnlineList(ViewerOnlineList viewer) {
		viewerOnlineList = viewer; 
	}

	public ViewerOnlineList getViewerOnlineList() {
		return viewerOnlineList;
	}

	public void setListener(ContinueListener continueListener) {
		this.continueListener = continueListener;
	}
	
	public ContinueListener getListener() {
		return continueListener;
	}

	public void setMode(int mode) {
		this.modeChosen = mode;
		if (mode == MULTIPLAYER) {
			this.setDifficulty(EZ);
			viewerGame.disableSpecialButtons();
		}
		else
			viewerGame.enableSpecialButtons();
	}

	public int getMode() {
		return modeChosen;
	}

	/**
	 * Checks if the given letter exists in the word. If so,
	 * shows the correctly guessed letter in all dedicated spots
	 * in the game window.
	 * @param letter The letter guessed.
	 */
	public void checkLetter(char letter) {
		String s = String.valueOf(letter);	//String representation of the char parameter
		if (wordToGuess.contains(s)) {
			for (int i = 0; i < wordToGuess.length(); i++) {
				if (wordToGuess.charAt(i) == letter) {
					encodedWord[i] = letter;
					//if co-op -> skicka bokstaven till motspelaren
					if (modeChosen == MULTIPLAYER && viewerOnlineList.getGameMode() == "co-op") {
						//client.sendLetterGuessed(letter);
					}
				}
			}
		} else {
			viewerGame.incrementWrongLetterCount();
		}
		viewerGame.setWord(encodedWord);
		checkWin();
	}

	/**
	 * Checks if all letters of the word have been guessed. If so,
	 * sets up a victory message in the game window. 
	 */
	private void checkWin() { 
		int correctLetters = 0;
		for (int i = 0; i < encodedWord.length; i++) {
			if (encodedWord[i] != '-')
				correctLetters++;
		}
		if (correctLetters == wordToGuess.length()) {
			viewerGame.setWin(true);
			if (modeChosen == MULTIPLAYER) {
				client.win(true); //win() should tell CH to tell the other client(player) that this client won
				JOptionPane.showMessageDialog(null, "Congratulations, you won! You will be sent back \nto the game mode chooser.");
				continueListener.goBackMP();
			}
		}
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
	 * Saves the current progress. Only one save file
	 * may exist at a time.
	 */
	public void saveGameProgress() {	//TEST THIS METHOD PLEASE
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
	public void loadSaveFile() {	//TEST THIS METHOD PLEASE
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
	 * Reads a word from the given category and sets it up in the game window.
	 * @param filename The category-file to read a word from.
	 * @param category The name of the category.
	 */
	public void setCategory(String filename, String category) {
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
	 * Sets the word that has to be guessed to the given String. If multiplayer
	 * is the chosen mode, the name of the category is also set to "Multiplayer". 
	 * @param word The word that will have to be guessed during this game.
	 */
	public void setWordToGuess(String word, String gameMode) {
		wordToGuess = word.toUpperCase();
		setEncodedWordFromString(wordToGuess);
		if(modeChosen == MULTIPLAYER) {
			viewerGame.setCategory(gameMode);
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
	 * Resets the word the user chose, gets another one from the list of words. 
	 */
	public void resetCategoryWord() {
		Random rand = new Random();
		int index = rand.nextInt(listWordsFromCategory.size());
		setWordToGuess(listWordsFromCategory.get(index), null);
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
	 * Connects the user to the server.
	 * @param username their username.
	 * @param ip the ip they connect to.
	 * @param port the port they connect to.
	 */
	public void connect(String username, String ip, int port) {
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
		viewerGame.setEnabled(myTurn); //TEST THIS!!!!!!!!!
	}

	/**
	 * Updates the onlineList with its users.
	 * @param onlineList The list of all the users online.
	 */
	public void updateOnline(ArrayList<String> onlineList) {
		viewerOnlineList.updateOnlineList(onlineList);
	}
}
