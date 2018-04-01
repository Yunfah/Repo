package client;

import java.io.Serializable;

public class WordProgress implements Serializable {
	private String wordToGuess;
	private char[] wordProgress;
	private int wrongLetterCount;

	public WordProgress(String wordToGuess, char[] progress, int wrongGuesses) {
		this.wordToGuess = wordToGuess;
		wordProgress = progress;
		wrongLetterCount = wrongGuesses;
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
	
	public static void main(String[] args) {
		char[] testProgress = {'-', 'e', '-'};
		WordProgress test = new WordProgress("Hej", testProgress, 1);
		
		System.out.println(test);
	}
}
