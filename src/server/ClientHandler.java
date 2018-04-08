package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import client.Client;

public class ClientHandler {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Client client;
	private boolean inGame = false;
	
	public ClientHandler(Socket socket, ObjectInputStream ois, ObjectOutputStream oos) {
		
	}

}
