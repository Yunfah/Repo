package client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

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
	
	private int ezLife = 10;
	private int dsLife = 5;
	private int xtremeLife = 3;

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

//	public void setCategory(String filename, String category) {
//		Random rand = new Random();
//		list.clear();
//		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8"))) {
//			String word = br.readLine();
//			while(word != null) {
//				list.add(word);
//				word = br.readLine();
//			}
//			int index = rand.nextInt(list.size());
//			String choosenWord = list.get(index).toUpperCase();
//			
//			viewerGame.setWord(choosenWord, choosenWord.length());
//			//choosenWord är själva ordet, choosenWord.length är mängden streck som ska ritas upp i VGame	
//			viewerGame.setCategory(category);
//		}catch (IOException e ) {}
//	}

	public void setDifficulty(int difficulty) {
		if (difficulty == EZ) {
			this.difficulty = ezLife;
		} else if (difficulty == DARK_SOULS) {
			this.difficulty = dsLife;
		} else if (difficulty == XTREME) {
			this.difficulty = xtremeLife;
		} else {
			//INVALID DIFFICULTY
		}
	}
}
