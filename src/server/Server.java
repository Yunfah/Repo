package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Hosts multiplayer games of hangman. Keeps track of currently connected
 * clients, and clients that are currently in a game of hangman. Also
 * pairs clients together. 
 * @author Elina Kock, Jakob Kennerberg, Yun-Fah Chow, Yamma Sarwari
 */
public class Server implements Runnable {
	private Thread server = new Thread(this);
	private ServerSocket serverSocket;
	private HashMap<String, ClientHandler> clientList = new HashMap<String, ClientHandler>(); //The string is the client username
	private ArrayList<Game> gameList = new ArrayList<Game>();	//should hold a list of all active games between clients at this time.
	private int port;

	/**
	 * Constructor.
	 * @param port The port this server will listen on. 
	 */
	public Server(int port) {
		this.port = port;
		try {
			serverSocket = new ServerSocket(port);
			server.start();
		} catch (IOException e) {}
	}

	/**
	 * Sends a game invite for game mode to receiver. If the receiver is already
	 * in a game, the invite is declined. 
	 * @param sender The client that sent the invite. 
	 * @param receiver The client the invite is meant for. 
	 * @param gameMode The game mode this invite will start if accepted.
	 */
	public void sendInvite(String sender, String receiver, String gameMode) { 
		System.out.println(sender + " asks " + receiver + " to play " + gameMode);
		ClientHandler ch = clientList.get(receiver);
		if (!ch.isInGame()) {
			ch.recieveInvite(sender, gameMode);
		} else {
			ClientHandler chSender = clientList.get(sender);
			chSender.reject();
		}
	}

	/**
	 * Method which sends a victory message to the winner of the game and informs the loser 
	 * about it as well.
	 * @param receiver The receiver of the victory message.
	 * @param senderIsWinner True if the sender of the message won, false if the sender failed.
	 */
	public void victoryMessage(String receiver, boolean senderIsWinner) {
		ClientHandler ch = clientList.get(receiver);
		System.out.println("In server victory message");
		if (senderIsWinner) {
			ch.receiveVictoryMessage("Your opponent succeeded in guessing the word.", true);
		} else {
			ch.receiveVictoryMessage("Your opponent failed at guessing the word. The round is over.", false);
		}
	}

	/**
	 * Disconnects the given ClientHandler from this server.
	 * @param ch ClientHandler to be disconnected.
	 */
	public void logout(ClientHandler ch) {
		System.out.println(ch.getUsername() + " wants to disconnect.");
		clientList.remove(ch.getUsername(), ch);
		ch = null; 
		sendClientList();
	}

	/**
	 * Sends a list of all currently online clients to every 
	 * connected client. The first loop creates a list of the usernames of
	 * the connected clients while the second loop sends it to all connected
	 * clients.
	 */
	private void sendClientList() {
		ArrayList<String> usernameList = new ArrayList<String>();
		for (Entry<String, ClientHandler> entry : clientList.entrySet()) {
			usernameList.add(entry.getValue().getUsername());
		}

		for (Entry<String, ClientHandler> entry : clientList.entrySet()) {
			entry.getValue().sendClientList(usernameList);
		}
	}

	/**
	 * Creates a game of hangman in the given game mode, and with the given
	 * players (clients).
	 * @param player1 One of the players. The player that sent the invite.
	 * @param player2 The other player. The player that received and accepted the invite.
	 * @param gameMode The game mode that this game will use the rules of. 
	 */
	public void createGame(String player1, String player2, String gameMode) {
		ClientHandler p1 = clientList.get(player1);
		ClientHandler p2 = clientList.get(player2);
		p1.closePendingInviteWindow();
		gameList.add(new Game(p1, p2, gameMode));
		p1.setMultiplayerMode(gameMode);
		p2.setMultiplayerMode(gameMode);
	}

	/**
	 * Method which sends the letter which as been guessed to the opponent. This method should
	 * be used in game modes co-op and 1 writes 1 guesses.
	 * @param letterGuessed The letter that was guessed.
	 * @param receiverOfGuess The receiver of the guess. 
	 */
	public void sendGuess(char letterGuessed, boolean isCorrect, String receiverOfGuess) {
		ClientHandler ch = clientList.get(receiverOfGuess);
		ch.receiveGuess(letterGuessed, isCorrect);
	}

	/**
	 * Sends a message to the given sender that their invite was declined.
	 * @param sender The sender of the declined invite.
	 */
	public void declineInviteFrom(String sender) {
		System.out.println("Going to reject " + sender);
		ClientHandler senderOfInvite = clientList.get(sender);
		senderOfInvite.closePendingInviteWindow();
		senderOfInvite.reject();
	}

	/**
	 * Method which notifies the opponent that the client clienthandler calling this method has left the game
	 * @param opponent The opponent who will be notified
	 */
	public void messageOpponent(String opponent) {
		ClientHandler opponentToMessage = clientList.get(opponent);
		opponentToMessage.sendLeaveMessage();
	}

	/**
	 * Listens to connections from clients and creates ClientHandlers for them.
	 */
	public void run() {
		System.out.println("Server is running on port " + port + "...");
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

				String username = (String)ois.readObject();
				System.out.println(username + " connected.");

				clientList.put(username, new ClientHandler(ois, oos, this, username));

				sendClientList();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}	
	}
}
