package client;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ViewerOnlineList extends JPanel {
	private ContinueListener continueListener;
	private Controller controller;
	
	
	public ViewerOnlineList() {
		setPreferredSize(new Dimension(1200, 800));
		setLayout(new BorderLayout());
		
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public void setListener(ContinueListener listener) {
		continueListener = listener;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test of category window");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new ViewerOnlineList());
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
