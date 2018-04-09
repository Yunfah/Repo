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

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Controller {
	private ViewerGame viewerGame;
	private ViewerUsername viewerUsername;
	private ViewerMultiplayerMode viewerMultiplayer;
	private ViewerOnlineList viewerOnlineList;
	private ArrayList<String> list = new ArrayList<String>();

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

	public void setViewerUsername(ViewerUsername viewer) {
		viewerUsername = viewer;
	}

	public void setViewerMultiplayerMode(ViewerMultiplayerMode viewer) {
		viewerMultiplayer = viewer;
	}

	public void setViewerOnlineList(ViewerOnlineList viewer) {
		viewerOnlineList = viewer; 
	}

	public void setMode(int mode) {
		this.modeChosen = mode;
		//if multiplayer -> connect to server
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
		if (correctLetters == wordToGuess.length()) 
			viewerGame.setWin(true);
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
		WordProgress newSave = new WordProgress(wordToGuess, encodedWord, wrongGuesses);
		 
		try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(
				new FileOutputStream("files/SaveFile.dat", false)))) {
			
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
		list.clear();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8"))) {
			String word = br.readLine();
			while(word != null) {
				list.add(word);
				word = br.readLine();
			}
			int index = rand.nextInt(list.size());
			setWordToGuess(list.get(index));	
			viewerGame.setCategory(category);
		} catch (IOException e ) {}
	}
	
	public void setWordToGuess(String word) {
		wordToGuess = word.toUpperCase();
		setEncodedWordFromString(wordToGuess);
	}
	
	private void setEncodedWordFromString(String word) {
		word.toUpperCase();
		encodedWord = new char[word.length()];
		for (int i = 0; i < wordToGuess.length(); i++) {
			if (wordToGuess.charAt(i) == ' ') {
				encodedWord[i] = ' ';
			} else {
				encodedWord[i] = '-';
			}
			System.out.print(encodedWord[i]);
		}
		viewerGame.setWord(encodedWord);
	}
	
	public int getDifficulty() {
		return difficulty;
	}

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
		} else {
			System.out.println("Somehow an invalid difficulty was entered.");
		}
	}
	
	public void connect(Client client) {
		
	}
	
	public static void main(String[] args) {
		
	}
}
