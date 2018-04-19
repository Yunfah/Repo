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
				Object input = ois.readObject();
				
				if (input instanceof String) {
					String str = (String)input;
					
					switch (str) {
					case "logout" : server.logout(this);
					break;
					case "invite" : { //Send invite to chosen player. How to show chosen player?
						String[] invite = ois.readUTF().split(",");
						String username = invite[0];
						String gamemode = invite[1];
						server.sendInvite(username, gamemode); //<- servern hittar CH med usernamet och anropar dens receiveInvite. 
					}
					break;
					case "accept" : int ble; //accept invite that was just received.
					break;
					case "decline" : int b; //decline invite that was just received. 
					}
					
				}
				
			} catch (Exception e) {
				server.logout(this);
				break;
			}
		} //end while
	}
}
