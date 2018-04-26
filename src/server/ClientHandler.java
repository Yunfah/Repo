package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Thread that handles requests on the server side for a client.
 * Acts as a middle man between client and server. 
 *
 */
public class ClientHandler implements Runnable {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private String username;
	private Server server;
	private boolean inGame = false;

	/**
	 * Constructor.
	 * @param socket
	 * @param ois ObjectInputStream with which to read from the client.
	 * @param oos ObjectOutputStream with which to write to the client.
	 * @param server The server that created this ClientHandler. Server to communicate with.
	 * @param username The username of the client. 
	 */
	public ClientHandler(Socket socket, ObjectInputStream ois, ObjectOutputStream oos, Server server, String username) {
		this.socket = socket;
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
		//TODO: Send an invite to this client from sender for gamemode
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
		} catch (IOException e) {
			System.out.println("Error in rejection");
		}
	}
	
	public void startGame() {
		//TODO: Here?
	}
	
	//Should somehow set the word to guess in this client's game window (ViewerGame)
	public void setWordToGuess(String word) {
		try {
			oos.writeUTF(word);
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
					System.out.println("trying to invite ");
					String sender = ois.readUTF();
					String receiver = ois.readUTF();
					String gamMmode = ois.readUTF();
					System.out.println("Requesting server to send invite to " + receiver);
					server.sendInvite(sender, receiver, gamMmode); //<- servern hittar CH med usernamet och anropar dens receiveInvite().
				}
				break;
				case "logout" : {
					System.out.println("fake logggoot");
					server.logout(this); 
				}
				break;
				case "accept" : {	//this client accpets an invite
					System.out.println(username + " accepted invite.");
					String p1 = ois.readUTF();
					String p2 = ois.readUTF();
					String gameMode = ois.readUTF();
					inGame = true;
					System.out.println("Asking server to accept...");
					server.createGame(p1, p2, gameMode); //accept invite that was just received.
				}
				break;
				case "decline" : { //this client declines an invite.
					String sender = ois.readUTF();
					server.declineInviteFrom(sender);
				}
				break;
				case "test" : int ignore; //test method
				break;
				} //end switch		
			System.out.println("End of switch in CH");	
			} catch (Exception e) {
				System.out.println("Exception in CH run method");
				server.logout(this);
				break;
			}
		} //end while
		System.out.println("End of CH while loop");
	}
}
