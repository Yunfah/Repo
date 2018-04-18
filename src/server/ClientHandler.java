package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

import client.Client;

public class ClientHandler extends Thread implements Serializable {
	private Socket socket;
	private ObjectOutputStream oos;
	private Client client;
	private String username;
	private Server server;
	private boolean inGame = false;
	
	public ClientHandler(Socket socket, ObjectOutputStream oos, Server server, String username) {
		this.socket = socket;
		this.oos = oos;
		this.server = server;
		this.username = username;
	}
	 
	public String getUsername() {
		return username;
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
	
	public void run() {
		while (true) {
			
		}
	}

}
