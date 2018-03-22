package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hosts multiplayer games of Hangman. Pairs clients together. 
 *
 */
public class Server implements Runnable {
	private Thread server = new Thread(this);
	private ServerSocket serverSocket;
	
	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
			server.start();
		} catch (IOException e) {}
	}
	@Override
	public void run() {
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
