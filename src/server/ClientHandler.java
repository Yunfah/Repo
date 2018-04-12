package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import client.Client;

public class ClientHandler extends Thread {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Client client;
	private String username;
	private boolean inGame = false;
	
	public ClientHandler(Socket socket, ObjectInputStream ois, ObjectOutputStream oos, String username) {
		this.socket = socket;
		this.ois = ois;
		this.oos = oos;
		this.username = username;
	}
	 
	public String getUsername() {
		return username;
	}
	
	public void run() {
		while (true) {
			
		}
	}

}
