package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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

	/**
	 * Constructor.
	 * @param username The username that represents this client.
	 * @param ip The ip address of the server that the client connects to.
	 * @param port The port that the server listens on.
	 */
	public Client(String username, String ip, int port) {
		this.username = username;
		try {
			socket = new Socket(ip, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {}
		start();
	}
	
	/**
	 * Sets the controller for this client. 
	 * @param controller
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getUsername() {
		return username;
	}
	
	public void sendInvite(String username) {
		
	}
	
	public void receiveInvite(String username, String gamemode) {
		JOptionPane.showConfirmDialog(null, username + " invited you to play " + gamemode
				+ ". Accept?");
	}

	public void run() {

		try {
			oos.writeObject(username);
			
			while(true) {
				Object input = ois.readObject();
				if (input instanceof ArrayList) {
					ArrayList<String> list = (ArrayList<String>)input;
					controller.updateOnline(list);
				}
				
			} //end while
			
		} catch (IOException e) {

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
}
