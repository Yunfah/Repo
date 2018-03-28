package client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Controller {
	private ViewerGame viewerGame;
	private ViewerSelectCategory viewerSelectCategory;
	private ViewerSelectDifficulty viewerSelectDifficulty;
	private ViewerSelectMode viewerSelectMode;
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

	public void setViewerSelectCategory(ViewerSelectCategory viewer) {
		viewerSelectCategory = viewer;
	}

	public void setViewerSelectDifficulty(ViewerSelectDifficulty viewer) {
		viewerSelectDifficulty = viewer;
	}

	public void setViewerSelectMode(ViewerSelectMode viewer) {
		viewerSelectMode = viewer;
	}

	public void setMode(int mode) {
		this.modeChosen = mode;
		//if multiplayer -> connect to server
	}

	public void checkLetter(char letter) {
		String s = String.valueOf(letter);	//String representation of the char parameter
//		int correctLetters = 0;
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
		//show incorrect letter in viewerGame
		checkWin();
	}
	
	private void checkWin() {
		int correctLetters = 0;
		for (int i = 0; i < encodedWord.length; i++) {
			if (encodedWord[i] != '-')
				correctLetters++;
		}
		if (correctLetters == wordToGuess.length()) {
			viewerGame.setWin(true);
		}
			
	}

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
			wordToGuess = list.get(index).toUpperCase();

			//Sets the word to a char[] filled with '-' for each letter.
			encodedWord = new char[wordToGuess.length()];
			for (int i = 0; i < wordToGuess.length(); i++) {
				encodedWord[i] = '-';
				System.out.print(encodedWord[i]);
			}
			System.out.println();
			viewerGame.setWord(encodedWord);
			viewerGame.setCategory(category);
		}catch (IOException e ) {}
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
}
