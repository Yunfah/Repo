package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientHandler extends Thread {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private String username;
	private Server server;
	private boolean inGame = false;

	public ClientHandler(Socket socket, ObjectInputStream ois, ObjectOutputStream oos, Server server, String username) {
		this.socket = socket;
		this.ois = ois;
		this.oos = oos;
		this.server = server;
		this.username = username;
		this.start();
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
	
	/**
	 * This ClientHandler's Client receives an invite for gamemode from sender.
	 * @param sender The sender of the invite.
	 * @param gameMode The gamemode this invite will start if accepted. 
	 */
	public void recieveInvite(String sender, String gameMode) {
		//TODO: Send an invite to this client from sender for gamemode
		try {
			oos.writeUTF("invite");
			oos.writeUTF(sender);
			oos.writeUTF(gameMode);
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
				Object input = ois.readObject();
				
				if (input instanceof String) {
					String str = (String)input;
					
					switch (str) {
					case "logout" : server.logout(this);
					break;
					case "invite" : { //Send invite to chosen player. 
						System.out.println("trying to invite ");
						String[] invite = ois.readUTF().split(",");
						String sender = invite[0];
						String receiver = invite[1];
						String gamMmode = invite[2];
						System.out.println("Requesting server to send invite to " + receiver);
						server.sendInvite(sender, receiver, gamMmode); //<- servern hittar CH med usernamet och anropar dens receiveInvite(). 
					}
					break;
					case "accept" : int ble; //accept invite that was just received.
					break;
					case "decline" : int b; //decline invite that was just received. 
					break;
					
					} //end switch
				}
				
			} catch (Exception e) {
				server.logout(this);
				break;
			}
		} //end while
	}
}
