package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ViewerSelectMode extends JPanel {
	private JPanel pnlButtons = new JPanel();
	private JLabel lblHeader = new JLabel("Hangman    ", SwingConstants.CENTER);
	private JButton btnBack = new JButton("<-- BACK");
	private JButton btnSingle = new JButton("Singleplayer");
	private JButton btnMulti = new JButton(" Multiplayer ");
	
	public ViewerSelectMode() {
		setPreferredSize(new Dimension (1200,800));
		setLayout(new BorderLayout());
		setBackground(Color.DARK_GRAY);
		
		btnSingle.setFont(new Font("Sans Serif", Font.PLAIN, 40));
		btnMulti.setFont(new Font("Sans Serif", Font.PLAIN, 40));
		
		add(pnlButtons, BorderLayout.CENTER);
		
		pnlButtons.setBackground(Color.DARK_GRAY);
		pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout.Y_AXIS));
		btnSingle.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlButtons.add(Box.createRigidArea(new Dimension(100,150)));
		btnMulti.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		add(titlePanel(), BorderLayout.NORTH);
		pnlButtons.add(btnSingle);
		pnlButtons.add(btnMulti);
		
	}
	
	private JPanel titlePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(1200, 200));
		Font titlefont = new Font("SansSerif", Font.PLAIN, 125);
		Font btnfont = new Font("SansSerif", Font.BOLD, 30);
		panel.setBackground(Color.DARK_GRAY);
		lblHeader.setFont(titlefont);
		lblHeader.setForeground(Color.WHITE);
		btnBack.setPreferredSize(new Dimension(200, 100));
		btnBack.setFont(btnfont);
		btnBack.setForeground(Color.WHITE);
		btnBack.setBorderPainted(false);
		
		panel.add(btnBack, BorderLayout.WEST);
		panel.add(lblHeader, BorderLayout.CENTER);
		
		return panel;
		
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
