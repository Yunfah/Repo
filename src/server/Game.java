package server;

import client.Client;
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
	
	public Game(ClientHandler player1, ClientHandler player2, String gameMode) {
		this.player1 = player1;
		this.player2 = player2;
		this.gameMode = gameMode;
	}
	
	public void changeTurns() {
		
	}
	
	public void setWinner() {
		
	}
	
	
}
