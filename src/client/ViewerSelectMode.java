package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ViewerSelectMode extends JPanel {
	private JPanel pnlButtons = new JPanel();
	private JLabel lblHeader = new JLabel("Hangman", SwingConstants.CENTER);
	private JButton btnSingle = new JButton("Singleplayer");
	private JButton btnMulti = new JButton("Multiplayer");
	
	public ViewerSelectMode() {
		setPreferredSize(new Dimension (1200,800));
		setLayout(new BorderLayout());
		
		lblHeader.setFont(new Font("Sans Serif", Font.PLAIN, 80));
		btnSingle.setFont(new Font("Sans Serif", Font.PLAIN, 40));
		btnMulti.setFont(new Font("Sans Serif", Font.PLAIN, 40));

		add(lblHeader, BorderLayout.NORTH);
		add(pnlButtons, BorderLayout.CENTER);
		
// Flowlayout 		
//		btnSingle.setPreferredSize(new Dimension (300,150) );
//		btnMulti.setPreferredSize(new Dimension (300,150) );
//		pnlButtons.setLayout(new FlowLayout());
		
		pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout.Y_AXIS));
		btnSingle.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlButtons.add(Box.createRigidArea(new Dimension(100,150)));
		btnMulti.setAlignmentX(Component.CENTER_ALIGNMENT);

//		pnlButtons.setPreferredSize(new Dimension (800,400));
		pnlButtons.add(btnSingle);
		pnlButtons.add(btnMulti);
		
		
	}
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	public static void main (String [] args) {
		JFrame frame = new JFrame ("Hangman");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new ViewerSelectMode()); 
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}

}
