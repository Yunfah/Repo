package client;

import java.io.Serializable;

public class WordProgress implements Serializable {
	private String wordToGuess;
	private char[] wordProgress;
	private int wrongLetterCount;
	private boolean[] lettersGuessed;

	public WordProgress(String wordToGuess, char[] progress, int wrongGuesses, boolean[] lettersGuessed) {
		this.wordToGuess = wordToGuess;
		wordProgress = progress;
		wrongLetterCount = wrongGuesses;
		this.lettersGuessed = lettersGuessed;
	}

	public String getWordToGuess() {
		return wordToGuess;
	}

	public void setWordToGuess(String wordToGuess) {
		this.wordToGuess = wordToGuess;
	}

	public char[] getWordProgress() {
		return wordProgress;
	}
	
	public boolean[] getLettersGuessed() {
		return lettersGuessed;
	}

	public void setWordProgress(char[] wordProgress) {
		this.wordProgress = wordProgress;
	}

	public int getWrongLetterCount() {
		return wrongLetterCount;
	}

	public void setWrongLetterCount(int wrongLetterCount) {
		this.wrongLetterCount = wrongLetterCount;
	}
	
	public String toString() {
		String progress = "";
		for (char c : wordProgress) {
			progress += c;
		}
		String str = "Word:"+wordToGuess + ",progress:" + progress + ",Mistakes:" + wrongLetterCount;
		return str;
	}
}
