package simple_Hangman;

import javax.swing.JOptionPane;

public class Controller {
	private Viewer viewer;
	private int connectedShowers;
	private int litresSaved;
	private int pointsSpent; //represents the amount of points (litres) spent on comments
	
	public Controller(Viewer viewer, int showers) {
		this.viewer = viewer;
		connectedShowers = showers;
	}
	
	public void setNbrOfShowers(int showers) {
		connectedShowers = showers;
	}
	
	public void setLitresSaved(int litres) {
		litresSaved = litres;
	}
	
	public int calculateWouldCost() {
		
		
		return 0;
	}
	
	public void spendPoints(int cost) {
		if (pointsSpent+cost > litresSaved) {
			JOptionPane.showMessageDialog(null, "Not enough points left. Save more water together first!");
		} else {
			//call method that lets user leave a comment for
		}
	}
	
	

}
