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
	private ArrayList<Game> gameList = new ArrayList<Game>();
	private int port;
	
	public Server(int port) {
		this.port = port;
		try {
			serverSocket = new ServerSocket(port);
			server.start();
		} catch (IOException e) {}
	}
	
	public void sendInvite(String sender, String username, String gamemode) { //take parameter for who to send an invite to
		ClientHandler user = clientList.get(username);
		user.recieveInvite(sender);
		
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
