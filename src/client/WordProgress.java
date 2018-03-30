package client;

public class WordProgress {
	private String wordToGuess;
	private char[] wordProgress;
	private int wrongLetterCount;

	public WordProgress(String wordToGuess, char[] progress, int wrongGuesses) {
		this.wordToGuess = wordToGuess;
		wordProgress = progress;
		wrongLetterCount = wrongGuesses;
	}
}
