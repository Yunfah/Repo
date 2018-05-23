package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Represents a player with a connection to a server.
 * @author Elina Kock, Jakob Kennerberg, Yun-Fah Chow, Yamma Sarwari
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
		} catch (IOException e) {
			System.out.println("Det gick fel");
		}
		start();
	}

	/**
	 * Sets the controller for this client. 
	 * @param controller The controller for this client.
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
			oos.writeUTF(username);		//sender of invite (this client)
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
				controller.getViewerGame().setCategory(gameMode);
				controller.getViewerOnlineList().setGameMode(gameMode);
				controller.setMultiplayerGameMode(gameMode);
				opponent = sender;
				oos.writeUTF("accept");
				oos.writeUTF(sender);	//player1
				oos.writeUTF(username);	//player2
				oos.writeUTF(gameMode);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Tells the server that this client either succeeded or failed 
	 * in guessing the word.
	 * @param win If this client succeeded or failed in guessing the word.
	 */
	public void win(boolean win) {	
		if (win) {
			try {
				oos.writeUTF("win");
				oos.writeBoolean(true);
				oos.writeUTF(opponent);
				oos.flush();
				System.out.println("true win sent to " + opponent);
			} catch (IOException e) {
				System.out.println("Error while sending win(true) from " + username);
			}
		} else {
			try {
				oos.writeUTF("win");
				oos.writeBoolean(false);
				System.out.println("false win sent to " + opponent);
				oos.writeUTF(opponent);
				oos.flush();
			} catch (IOException e) {
				System.out.println("Error while sending win(false) from " + username);
			}
		}
		this.leaveGame();
	}

	/**
	 * Tells the server that this client guessed the given character for
	 * the word that has to be guessed. Also tells the server whether
	 * the guess was correct or incorrect.
	 * @param letter The letter that the client guessed. 
	 * @param correct Set to true if this guess was correct, otherwise false.
	 */
	public void guessLetter(char letter, boolean correct) {
		try {
			oos.writeUTF("guess");
			oos.writeBoolean(correct);
			oos.writeChar(letter);
			oos.writeUTF(opponent);
			oos.flush();
		} catch (IOException e) {}
	} 
	
	public void finishCoOp() {
		try {
			oos.writeUTF("finishCoOp");
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Requests the server to disconnect this client.
	 */
	public void leaveGame() {
		try {
			oos.writeUTF("leaveGame");
			oos.writeUTF(opponent);
			oos.flush();
		} catch (IOException e) {} 
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
	public void serverComm() {
		try {
			oos.writeObject(username);
			while(true) {
				System.out.println("Client " + username + " waiting to read object...");
				Object input = ois.readObject();
				if (input instanceof ArrayList) {	//If the input is an arraylist it can only be a list of online clients
					ArrayList<String> list = (ArrayList<String>)input;
					controller.updateOnline(list);
				}else if (input instanceof Character) {
					char guessed = (Character)input;
					boolean isCorrect = ois.readBoolean();
					controller.pimpGuessedButton(guessed, isCorrect);
					controller.checkLetter(guessed);
				} else {
					String str = (String) input;
					if (str.equals("invite")) {	//This client is invited
						String sender = ois.readUTF();
						String gameMode = ois.readUTF();
						System.out.println("Client " + username + " invited to " + gameMode + " by " + sender);
						receiveInvite(sender, gameMode);
					} else if (str.equals("reject")) {
						System.out.println("Invite decline");
						JOptionPane.showMessageDialog(null, "Invite was declined");
						controller.getViewerOnlineList().closePendingInviteMessage();
					} else if (str.equals("wordToGuess")) {	//Sets the word that has to be guessed
						String word = ois.readUTF();
						String gameMode = ois.readUTF();
						controller.setWordToGuess(word, gameMode);
					} else if (str.equals("chooseWord")) {
						String word = JOptionPane.showInputDialog("Choose a word for your opponent to guess\n(include nothing but letters or spaces):");
						oos.writeUTF("setWord");
						oos.writeUTF(word);
						oos.flush();
					} else if (str.equals("closePendingInvite")) {
						controller.getViewerOnlineList().closePendingInviteMessage();
					} else if (str.equals("victoryMessage")) {
						JOptionPane.showMessageDialog(null, ois.readUTF());
						boolean opponentWin = ois.readBoolean();
						if (opponentWin) {
							controller.getViewerGame().disableAllLetters();
						}
						controller.getListener().goBackMP();
					} else if (str.equals("turn")) {
						boolean myTurn = ois.readBoolean();
						controller.setTurn(myTurn);
					} else if (str.equals("gameMode")) {
						controller.setMultiplayerGameMode(ois.readUTF());
					} else if( str.equals("opponentLeft")) {
						controller.opponentLeft();
					}
				}
			} //end while
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		serverComm();
	}
}
