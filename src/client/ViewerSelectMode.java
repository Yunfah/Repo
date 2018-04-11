package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;

public class ViewerSelectMode extends JPanel {
	private JLabel lblHeader = new JLabel("Hangman", SwingConstants.CENTER);
	private JButton btnSingle = new JButton("Singleplayer");
	private JButton btnMulti = new JButton(" Multiplayer ");
	private ButtonListener listener = new ButtonListener();
	private ContinueListener continueListener;
	private Controller controller;
	
	public ViewerSelectMode() {
		setPreferredSize(new Dimension (1200,800));
		setLayout(new BorderLayout());
		setBackground(Color.DARK_GRAY);
		
		add(titlePanel(), BorderLayout.NORTH);
		add(buttonPanel(), BorderLayout.CENTER);	
	}
	
	private JPanel titlePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(1200, 200));
		Font titlefont = new Font("SansSerif", Font.PLAIN, 125);
		panel.setBackground(Color.DARK_GRAY);
		lblHeader.setFont(titlefont);
		lblHeader.setForeground(Color.WHITE);

		panel.add(lblHeader, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel buttonPanel() {
		JPanel bPanel = new JPanel(null);
		bPanel.setBackground(Color.DARK_GRAY);
				
		btnSingle.setBounds(450, 50, 300, 100);
		btnMulti.setBounds(450, 200, 300, 100);
			
		btnSingle.setFont(new Font ("Sans Serif", Font.BOLD, 30));
		btnMulti.setFont(new Font ("Sans Serif", Font.BOLD, 30));
		
		btnSingle.addActionListener(listener);
		btnMulti.addActionListener(listener);
		
		bPanel.add(btnSingle);
		bPanel.add(btnMulti);
		
		return bPanel;
	}
	
	public void setListener(ContinueListener listener) {
		continueListener = listener;
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	private class ButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnSingle) {
				continueListener.nextPanel();
				controller.setMode(Controller.SINGLE_PLAYER);
				System.out.println("singleplayer chosen");
			} else if (e.getSource() == btnMulti) {
				controller.setMode(Controller.MULTIPLAYER);
				continueListener.nextPanelMP();
				System.out.println("multiplayer chosen");
			}
			
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
