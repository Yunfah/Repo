package client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Controller {
	private ViewerGame viewerGame;
	private ViewerSelectCategory viewerSelectCategory;
	private ViewerSelectDifficulty viewerSelectDifficulty;
	private ViewerSelectMode viewerSelectMode;
	private ArrayList<String> list = new ArrayList<String>();

	private int modeChosen;
	public static final int SINGLE_PLAYER = 1;
	public static final int MULTIPLAYER = 2;
	
	private int difficulty;
	public static final int EZ = 1;
	public static final int DARK_SOULS = 2;
	public static final int XTREME = 3;

	public Controller() {
		

	}
	
	public void setViewerGame(ViewerGame viewer) {
		viewerGame = viewer;
	}
	
	public void setViewerSelectCategory(ViewerSelectCategory viewer) {
		viewerSelectCategory = viewer;
	}
	
	public void setViewerSelectDifficulty(ViewerSelectDifficulty viewer) {
		viewerSelectDifficulty = viewer;
	}
	
	public void setViewerSelectMode(ViewerSelectMode viewer) {
		viewerSelectMode = viewer;
	}

	public void setMode(int mode) {
		this.modeChosen = mode;
		//if multiplayer -> connect to server
	}

	public void setCategory(String filename) throws IOException {
		list.clear();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8"))) {
			String word = br.readLine();
			while(word != null) {
				list.add(word);
				word = br.readLine();
			}
			
			
		}
		//Read words from filename, put them in a list and 
		//choose a random word (from the list) for the player to guess.
		//viewerGame.setWord(String word);
		
	}

	public void setDifficulty(int difficulty) {
		switch (difficulty) {
		case 1 : difficulty = EZ;
		break;
		case 2 : difficulty = DARK_SOULS;
		break;
		case 3 : difficulty = XTREME;
		break;
		}

	}

}
