package client;

import java.io.Serializable;

/**
 * This class contains a object which keeps track of the progress made in a game.
 * This class is used when saving and loading a game.
 * @author Elina Kock, Jakob Kennerberg, Yun-Fah Chow, Yamma Sarwari
 *
 */
public class WordProgress implements Serializable {
	private String wordToGuess;
	private char[] wordProgress;
	private int wrongLetterCount;
	private boolean[] lettersGuessed;

	/**
	 * Constructor
	 * @param wordToGuess
	 * @param progress
	 * @param wrongGuesses
	 * @param lettersGuessed
	 */
	public WordProgress(String wordToGuess, char[] progress, int wrongGuesses, boolean[] lettersGuessed) {
		this.wordToGuess = wordToGuess;
		wordProgress = progress;
		wrongLetterCount = wrongGuesses;
		this.lettersGuessed = lettersGuessed;
	}

	/**
	 * Returns the word being guessed
	 * @return wordToGuess
	 */
	public String getWordToGuess() {
		return wordToGuess;
	}

	/**
	 * Sets the word to guess
	 * @param wordToGuess
	 */
	public void setWordToGuess(String wordToGuess) {
		this.wordToGuess = wordToGuess;
	}

	/**
	 * Returns an array of the letters being guessed correctly
	 * @return wordProgress
	 */
	public char[] getWordProgress() {
		return wordProgress;
	}
	
	/**
	 * Returns an array of all the letter buttons being pressed during the game
	 * @return lettersGuessed
	 */
	public boolean[] getLettersGuessed() {
		return lettersGuessed;
	}

	/**
	 * Sets the array of correct guessed letters.
	 * @param wordProgress
	 */
	public void setWordProgress(char[] wordProgress) {
		this.wordProgress = wordProgress;
	}

	/**
	 * Returns the count of wrong guesses made during the game.
	 * @return wrongLetterCount
	 */
	public int getWrongLetterCount() {
		return wrongLetterCount;
	}

	/**
	 * Sets the count of wrong guesses made during the game.
	 * @param wrongLetterCount
	 */
	public void setWrongLetterCount(int wrongLetterCount) {
		this.wrongLetterCount = wrongLetterCount;
	}
	
	/**
	 * Returns a string of the progress made during the game.
	 */
	public String toString() {
		String progress = "";
		for (char c : wordProgress) {
			progress += c;
		}
		String str = "Word:"+wordToGuess + ",progress:" + progress + ",Mistakes:" + wrongLetterCount;
		return str;
	}
}
