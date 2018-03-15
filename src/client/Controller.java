package client;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Controller {
	private ViewerGame viewerGame;
	private ViewerSelectCategory viewerSelectCategory;
	private ViewerSelectDifficulty viewerSelectDifficulty;
	private ViewerSelectMode viewerSelectMode;
	
	private int currentViewer;
	private int modeChosen;
	public static final int SINGLE_PLAYER = 1;
	public static final int MULTIPLAYER = 2;

	public Controller() {

	}
	
	public void setMode(int mode) {
		this.modeChosen = mode;
	}

	/**
	 * Uppdaterar vilken panel som visas i spelfönstret.
	 */
	private void showUI() {
		JPanel panel = null;
		if (modeChosen == SINGLE_PLAYER) {
			switch (currentViewer) {
			case 1 : panel = viewerSelectMode;
			break;
			case 2 : panel = viewerSelectDifficulty;
			break;
			case 3 : panel = viewerSelectCategory;
			break;
			case 4 : panel = viewerGame;
			}
		} else if (modeChosen == MULTIPLAYER ) {
			switch (currentViewer) {
			case 1 : panel = viewerSelectMode;
			break;
			case 2 : panel = viewerGame;
			break;
			}
		}
		
		JFrame frame = new JFrame("Hangman");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
