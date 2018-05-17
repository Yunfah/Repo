package server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a round of hangman between two clients(players).
 * @author Elina Kock, Jakob Kennerberg
 */
public class Game {
	private ClientHandler player1;
	private ClientHandler player2;
	private boolean player1Turn = true; //true if it is the turn of player1, false if it is player2.
	private String gameMode;
	private String word;

	/**
	 * Constructor.
	 * @param player1 A player of this game of hangman. The player that invited the other player.
	 * @param player2 A player of this game of hangman. The player that was invited to play.
	 * @param gameMode The gamemode of this game of hangman.
	 */
	public Game(ClientHandler player1, ClientHandler player2, String gameMode) {
		System.out.println("Going to create game. In Game instance...");
		this.player1 = player1;
		this.player2 = player2;
		this.gameMode = gameMode;
		if (gameMode.equals("write-guess")) { //prompt sender of invite to set a word?
			player1.setCustomWord();	//-> CH -> client -> String till CH -> ?
		} else {
			setRandomWord();
		}
		System.out.println("Sending " + word + " to clients.");
		player1.setWordToGuess(word, gameMode);
		player2.setWordToGuess(word, gameMode);
		if (gameMode.equals("co-op")) {
			player1.setTurn(true);
			player2.setTurn(false);
		}
	}

	/** 
	 * Switches whose turn it is to guess a letter.
	 */
	public void changeTurns() {
		if (player1Turn) {
			player1Turn = false;
		} else {
			player1Turn = true;
		}
//		player1.myTurn(player1Turn);	//Should enable or disable the letters for player1
//		player2.myTurn(!player1Turn);	//Should enable or disable the letters for player2
	}

	/**
	 * Sets up a random word that is to be guessed during this game.
	 */
	private void setRandomWord() {
		ArrayList<String> list = new ArrayList<String>();
		Random rand = new Random();

		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("files/Random.txt"),"UTF-8"))) {
			String word = br.readLine();
			while(word != null) { 
				list.add(word);
				word = br.readLine();
			}

			System.out.println("arraylist size: " + list.size());
			int index = rand.nextInt(list.size());
			this.word = list.get(index);
		} catch (IOException e ) {}
	}
}
