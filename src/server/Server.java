package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Hosts multiplayer games of Hangman. Pairs clients together. 
 *
 */
public class Server implements Runnable {
	private Thread server = new Thread(this);
	private ServerSocket serverSocket;
	private HashMap<String, ClientHandler> clientList = new HashMap<String, ClientHandler>(); //The string is the client username
	private ArrayList<Game> gameList = new ArrayList<Game>();	//should hold a list of all active games between clients at this time.
	private int port;
	
	/**
	 * Constructor.
	 * @param port The port this server will listen on. 
	 */
	public Server(int port) {
		this.port = port;
		try {
			serverSocket = new ServerSocket(port);	//BLABLABLA KOMMENTAR FÖR ATT PUSHA
			server.start();
		} catch (IOException e) {}
	}
	
	/**
	 * Sends a game invite for game mode to receiver.
	 * @param sender The client that sent the invite. 
	 * @param receiver The client the invite is meant for. 
	 * @param gameMode The game mode this invite will start if accepted.
	 */
	public void sendInvite(String sender, String receiver, String gameMode) { 
		System.out.println(sender + " asks " + receiver + " to play " + gameMode);
		ClientHandler ch = clientList.get(receiver);
		ch.recieveInvite(sender, gameMode);
	}
	
	public void logout(ClientHandler ch) {
		System.out.println(ch.getUsername() + " wants to disconnect.");
		clientList.remove(ch.getUsername(), ch);
		ch = null; //needed?
		sendClientList();
	}
	
	private void sendClientList() {
		ArrayList<String> usernameList = new ArrayList<String>();
		for (Entry<String, ClientHandler> entry : clientList.entrySet()) {
			usernameList.add(entry.getValue().getUsername());
		}
		
		for (Entry<String, ClientHandler> entry : clientList.entrySet()) {
			entry.getValue().sendClientList(usernameList);
		}
	}
	
	public void createGame(String player1, String player2, String gameMode) {
		ClientHandler p1 = clientList.get(player1);
		ClientHandler p2 = clientList.get(player2);
		gameList.add(new Game(p1, p2, gameMode)) ;
		
	}
	
	@Override
	public void run() {
		System.out.println("Server is running on port " + port + "...");
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				
				String username = (String)ois.readObject();
				System.out.println(username + " connected.");

				clientList.put(username, new ClientHandler(socket, ois, oos, this, username));
				
				sendClientList();
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}	
	}
}
