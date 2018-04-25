package server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

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
	
	private void setWord(String word) {
		if (word == null) {
			ArrayList<String> arraylist = new ArrayList<String>();
			Random rand = new Random();
			
			try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("files/Random.txt"),"UTF-8"))) {
				String word2 = br.readLine();
				while(word != null) {
					arraylist.add(word2);
					word2 = br.readLine();
				}
				int index = rand.nextInt(arraylist.size());
				String str = arraylist.get(index);
				
				//viewerGame.setCategory(category);
			} catch (IOException e ) {}
		} else {
			//TODO: set gameword to word (the parameter) 
		}
	}
	
	
}
