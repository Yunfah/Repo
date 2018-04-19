package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import client.Client;

public class ClientHandler extends Thread {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Client client;
	private String username;
	private Server server;
	private boolean inGame = false;

	public ClientHandler(Socket socket, ObjectInputStream ois, ObjectOutputStream oos, Server server, String username) {
		this.socket = socket;
		this.ois = ois;
		this.oos = oos;
		this.server = server;
		this.username = username;
		start();
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
			try {
				ois.readObject();
			} catch (Exception e) {
				server.logout(this);
				break;
			}
		} //end while
	}
}
