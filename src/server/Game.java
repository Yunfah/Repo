package server;

import client.Client;
import client.Controller;

/**
 * Represents a round of hangman between two clients.
 *
 */
public class Game {
	private ClientHandler player1;
	private ClientHandler player2;
	private Controller controllerP1;	//Something to controll turns etc. is needed.
	private Controller controllerP2;
	
	public Game(ClientHandler player1, ClientHandler player2) {
		
	}

}
