package client;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*; 

/**
 * Lets the player choose another online player from a list
 * so that they can play together.
 *
 */
public class ViewerMultiplayer extends JPanel {
	private ContinueListener continueListener;
	
	public ViewerMultiplayer() {
		setPreferredSize(new Dimension(1200, 800));
		setLayout(new BorderLayout());
		
		
		
	}
	
	public void setListener(ContinueListener listener) {
		continueListener = listener;
	}

}
