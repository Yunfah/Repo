package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;

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
	private String opponent;

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
	 * Returns this client's username.
	 * @return This client's username. 
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sends a game invite for a certain game mode to a chosen client.
	 * @param receiver The client that will receive the invite.
	 * @param gameMode The game mode that an accepted invite will start.
	 */

	public void sendInvite(String receiver, String gameMode) {
		try {
			opponent = receiver;
			oos.writeUTF("invite");
			oos.writeUTF(username);	
			oos.writeUTF(receiver);
			oos.writeUTF(gameMode);
			oos.flush();
			System.out.println("Invite sent from client to clientHandler.");
		} catch (IOException e) {
			System.out.println("Error sending invite from Client.");
		}
	}	

	/**
	 * Opens an invite from sender for a certain game mode.
	 * @param sender The sender of this invite.
	 * @param gameMode The game mode that this invite will start if accepted. 
	 */
	public void receiveInvite(String sender, String gameMode) {	
		String[] options = {"COME FORTH!", "Nay!"};
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("<html>" + sender + " wishes to partake in a duel with thee!"
				+ "<br>Game mode: " + gameMode + "</html>");
		panel.add(lbl);
		int selectedOption = JOptionPane.showOptionDialog(null, panel, "Hangman",
				JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);
		
		if (selectedOption == JOptionPane.NO_OPTION) {
			try {
				oos.writeUTF("decline");
				oos.writeUTF(sender);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (selectedOption == JOptionPane.YES_OPTION) {	
			try {
				opponent = sender;
				oos.writeUTF("accept");
				oos.writeUTF(sender);
				oos.writeUTF(username);
				oos.writeUTF(gameMode);
				oos.flush();
				controller.getViewerGame().setCategory(gameMode);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void win(boolean win) {	//DONE?
		if (win) {
			try {
				oos.writeObject("win");
				oos.writeBoolean(true);
				oos.writeUTF(opponent);
			} catch (IOException e) {
				System.out.println("Error while sending win(true) from " + username);
			}
		} else {
			try {
				oos.writeObject("win");
				oos.writeBoolean(false);
				oos.writeUTF(opponent);
			} catch (IOException e) {
				System.out.println("Error while sending win(false) from " + username);
			}
		}
	}
	
	/**
	 * 
	 * @param letter
	 */
	public void guessLetter(char letter) {
		try {
			oos.writeObject("guess");
			oos.writeChar(letter);
			oos.writeUTF(opponent);
		} catch (IOException e) {
			
		}
	}
	
	/**
	 * Sends a request to the server to be disconnected from it.
	 */
	public void logout() {
		try {
			oos.writeUTF("logout");
			oos.flush();
		}catch (IOException e) {
			System.out.println("Couldn´t log out");
		}
	}

	/**
	 * Communicates with a server through a ClientHandler. 
	 */
	public void run() {

		try {
			oos.writeObject(username); 
			while(true) {
				System.out.println("Client " + username + " waiting to read object...");
				Object input = ois.readObject();
				if (input instanceof ArrayList) {	//If the input is an arraylist it can only be a list of online clients
					ArrayList<String> list = (ArrayList<String>)input;
					controller.updateOnline(list);	//
				} else {
					String str = (String) input;
					if (str.equals("invite")) {
						String sender = ois.readUTF();
						String gameMode = ois.readUTF();
						System.out.println("Client " + username + " invited to " + gameMode + " by " + sender);
						receiveInvite(sender, gameMode);
					} else if (str.equals("reject")) {
						System.out.println("Invite decline");
						JOptionPane.showMessageDialog(null, "Invite was declined");
					} else if (str.equals("word")) {
						controller.setWordToGuess(ois.readUTF());
					}	
				}
			} //end while

		} catch (IOException e) {

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
}
