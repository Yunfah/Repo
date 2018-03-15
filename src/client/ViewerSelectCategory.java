package client;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ViewerSelectCategory extends JPanel {
	private JButton btnRandom = new JButton("Random");
	private JButton btnCategory1; //change button names when categories have been identified;
	private JButton btnCategory2;
	private JButton btnCategory3;
	private JLabel lblCategory = new JLabel("CATEGORY");
	
	public ViewerSelectCategory() {
		setPreferredSize(new Dimension(1200,800));
		
		
	}
	

	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Test of category window");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new ViewerSelectCategory());
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
}
