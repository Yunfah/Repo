package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import server.ClientHandler;

/**
 * Represents a player with a connection to a server.
 *
 */
public class Client extends Thread {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Controller controller;
	private String username;

	public Client(String username, String ip, int port) {
		this.username = username;
		try {
			socket = new Socket(ip, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {}
		start();
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public String getUsername() {
		return username;
	}

	public void run() {

		try {

			oos.writeObject(username);
			oos.writeObject(username);
			while(true) {
				Object input = ois.readObject();
				if (input instanceof ArrayList) {
					ArrayList<ClientHandler> list = (ArrayList<ClientHandler>)input;
					controller.updateOnline(list);
				}

			}
		} catch (IOException e) {

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		} try {
			oos.writeUTF(username);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
