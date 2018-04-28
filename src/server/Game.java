package server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import client.Controller;

/**
 * Represents a round of hangman between two clients(players).
 *
 */
public class Game {
	private ClientHandler player1;
	private ClientHandler player2;
	private Controller controllerP1;	//Something to control turns etc. is needed.
	private Controller controllerP2;	//Should these controllers come from CH? Then CH needs to know about client/controller (client sends its controller)
	private String gameMode;
	private String word;

	/**
	 * Constructor.
	 * @param player1 A player of this game of hangman. The player that invited the other player.
	 * @param player2 A player of this game of hangman. The player that was invited to play.
	 * @param gameMode The game mode of this game of hangman.
	 */
	public Game(ClientHandler player1, ClientHandler player2, String gameMode) {
		System.out.println("Going to create game. In Game instance...");
		this.player1 = player1;
		this.player2 = player2;
		this.gameMode = gameMode;
		//TODO: Set the word to guess?
		if (gameMode.equals("write-guess")) { //prompt sender of invite to set a word?
			// word = what the sender of the invite chose.
		} else {	//give both players the same random word... how?
			setRandomWord();
			//send this.word to both clients
			player1.setWordToGuess(word);
			player2.setWordToGuess(word);
		}
	}

	public void changeTurns() {

	}

	public void setWinner() {

	}

	/**
	 * Sets up a random word that is to be guessed in this game.
	 */
	private void setRandomWord() {
		ArrayList<String> list = new ArrayList<String>();
		Random rand = new Random();

		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("files/Random.txt"),"UTF-8"))) {
			String word = br.readLine();
			while(word != null) {
				list.add(word);
				System.out.println(word + " added to list.");
				word = br.readLine();
			}
			
			System.out.println("arraylist size: " + list.size());
			int index = rand.nextInt(list.size());
			this.word = list.get(index);
		} catch (IOException e ) {}
	}
}
