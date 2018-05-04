package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Thread that handles requests on the server side for a client.
 * Acts as a middle man between client and server. 
 *
 */
public class ClientHandler implements Runnable {
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private String username;
	private Server server;
	private boolean inGame = false;

	/**
	 * Constructor.
	 * @param ois ObjectInputStream with which to read from the client.
	 * @param oos ObjectOutputStream with which to write to the client.
	 * @param server The server that created this ClientHandler. Server to communicate with.
	 * @param username The username of the client. 
	 */
	public ClientHandler (ObjectInputStream ois, ObjectOutputStream oos, Server server, String username) {
		this.ois = ois;
		this.oos = oos;
		this.server = server;
		this.username = username;
		new Thread (this).start();
	}

	/**
	 * Returns the username of this ClientHandler's client.
	 * @return Username of the client. 
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns true if this ClientHandler's client is currently in 
	 * a game of hangman.
	 * @return If this client is already in a game or not. 
	 */
	public boolean isInGame() {
		return inGame;
	}

	/**
	 * Sends the current list of online clients to the client associated with this 
	 * ClientHandler. 
	 * @param list
	 */
	public void sendClientList(ArrayList<String> list) {
		try {
			oos.writeObject(list);
			oos.flush();
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

	/**
	 * This ClientHandler's Client receives an invite for gamemode from sender.
	 * @param sender The sender of the invite.
	 * @param gameMode The gamemode this invite will start if accepted. 
	 */
	public void recieveInvite(String sender, String gameMode) {
		System.out.println(username + " receiving invite");
		try {
			oos.writeObject("invite");
			oos.writeUTF(sender);
			oos.writeUTF(gameMode);
			oos.flush();
			System.out.println("Invite received");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is called by the server when another client rejects the invite
	 * sent by this client.
	 */
	public void reject() {
		System.out.println(username + " is being rejected."); 
		try {
			oos.writeObject("reject");
			oos.flush();
		} catch (IOException e) {
			System.out.println("Error in rejection");
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String setCustomWord() {
		String word = JOptionPane.showInputDialog("Choose a word for your opponent to guess");
		//Possible error-handling for later
		return word;
	}

	/**
	 * Shows a message saying that the opponent has either succeeded or failed
	 * in guessing the word. 
	 * @param message A message saying whether the opponent has succeeded or failed. 
	 */
	public void receiveVictoryMessage(String message, boolean victory) {	//NOT DONE
		System.out.println(message);
		try { 
			oos.writeObject("victoryMessage");
			oos.writeUTF(message);
			oos.writeBoolean(victory);
			oos.flush();
			//TODO:
		} catch (IOException e) {
			
		}
	}

	/**
	 * Sets up the word that has to be guessed for this Client. 
	 * @param word The word that has to be guessed.
	 */
	public void setWordToGuess(String word, String gameMode) {
		try {
			oos.writeObject("word");
			oos.writeUTF(word);
			oos.writeUTF(gameMode);
			oos.flush();
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

	public void closePendingInviteWindow() {
		try {
			oos.writeObject("closePendingInvite");
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Listens for requests from this Client and handles them using the server.
	 */
	public void run() {
		while (true) {
			try {
				String input = ois.readUTF();

				System.out.println(input + " was a string");
				switch (input) {
				case "invite" : { //Send invite to chosen player. 
					String sender = ois.readUTF();
					String receiver = ois.readUTF();
					String gameMode = ois.readUTF();
					System.out.println("Requesting server to send invite to " + receiver);
					server.sendInvite(sender, receiver, gameMode); //<- servern hittar CH med usernamet och anropar dens receiveInvite().
				}
				break;
				case "logout" : {
					server.logout(this); 
				}
				break;
				case "accept" : {	//this client accepts an invite
					System.out.println(username + " accepted invite.");
					String p1 = ois.readUTF();	//sender of invite
					String p2 = ois.readUTF();	//accepter of invite
					String gameMode = ois.readUTF();
					inGame = true;
					server.createGame(p1, p2, gameMode); //accept invite that was just received.
				}
				break;
				case "decline" : { //this client declines an invite.
					String sender = ois.readUTF();
					server.declineInviteFrom(sender);
				}
				break;
				case "win" : { //Sends to receiver whether this client won or failed
					System.out.println("Read win in CH switch");
					boolean win = ois.readBoolean();
					String receiver = ois.readUTF();
					System.out.println("Sending " + win + " to " + receiver);
					server.victoryMessage(receiver, win);
				}
				break;
				case "guess" : {
					char letterGuessed = ois.readChar();
					String opponent = ois.readUTF();
					server.sendGuess(letterGuessed, opponent);
				}
				break;
				} //end switch		
				System.out.println("End of switch in CH");	
			} catch (Exception e) {
				if (e instanceof SocketException || e instanceof EOFException) {
					server.logout(this);
					break;
				} else {
					System.out.println("Exception in CH run method: " + e);
					e.printStackTrace();
					break;
				}
			}
		} //end while
		System.out.println("End of CH while loop");
	}
}
