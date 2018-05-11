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
 *
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
			serverSocket = new ServerSocket(port);	//BLABLABLA KOMMENTAR FÖR ATT PUSHA
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

	public void victoryMessage(String receiver, boolean senderIsWinner) {	//NOT DONE
		ClientHandler ch = clientList.get(receiver);
		System.out.println("In server victory message");
		if (senderIsWinner) {
			ch.receiveVictoryMessage("Your opponent succeeded in guessing the word.", true);
		} else {
			ch.receiveVictoryMessage("Your opponent failed at guessing the word. You may keep trying.", false);
		}
	}

	/**
	 * Disconnects the given ClientHandler from this server.
	 * @param ch ClientHandler to be disconnected.
	 */
	public void logout(ClientHandler ch) {
		System.out.println(ch.getUsername() + " wants to disconnect.");
		clientList.remove(ch.getUsername(), ch);
		ch = null; //needed?
		sendClientList();
	}

	/**
	 * Sends a list of all currently online clients to every 
	 * connected client. 
	 */
	private void sendClientList() {
		ArrayList<String> usernameList = new ArrayList<String>();
		for (Entry<String, ClientHandler> entry : clientList.entrySet()) {	//Creates list with the usernames of connected clients.
			usernameList.add(entry.getValue().getUsername());
		}

		for (Entry<String, ClientHandler> entry : clientList.entrySet()) {	//Sends the username list to all connected clients. 
			entry.getValue().sendClientList(usernameList);
		}
	}

	/**
	 * Creates a game of hangman in the given game mode, and with the given
	 * players (clients).
	 * @param player1 One of the players. The player that sent the invite.
	 * @param player2 The other player. The player that received the invite.
	 * @param gameMode The game mode that this game will use the rules of. 
	 */
	public void createGame(String player1, String player2, String gameMode) {
		ClientHandler p1 = clientList.get(player1);	//sender of invite
		ClientHandler p2 = clientList.get(player2);	//Accepter of invite
		p1.closePendingInviteWindow();
		gameList.add(new Game(p1, p2, gameMode));
	}
	
	/**
	 * 
	 * @param letterGuessed
	 * @param receiverOfGuess
	 */
	public void sendGuess(char letterGuessed, String receiverOfGuess) {
		ClientHandler ch = clientList.get(receiverOfGuess);
		ch.receiveGuess(letterGuessed);
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
	 * Listens to connections from clients and creates ClientHandlers for them.
	 */
	@Override
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
