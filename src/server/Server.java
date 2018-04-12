package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Hosts multiplayer games of Hangman. Pairs clients together. 
 *
 */
public class Server implements Runnable {
	private Thread server = new Thread(this);
	private ServerSocket serverSocket;
	private ArrayList<ClientHandler> clientList = new ArrayList<ClientHandler>();
	private ArrayList<Game> gameList = new ArrayList<Game>();
	
	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
			server.start();
		} catch (IOException e) {}
	}
	@Override
	public void run() {
		System.out.println("Server is running...");
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				
				System.out.println(ois.readUTF());
				clientList.add(new ClientHandler(socket, ois, oos));
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
