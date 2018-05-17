package client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;


/**
 * This class contains the panel which lets a player choose which gamemode they want to play in multiplayer 
 * @author Elina Kock, Jakob Kennerberg, Yun-Fah Chow
 */
public class ViewerMultiplayerMode extends JPanel {
	private ContinueListener continueListener;
	private ButtonListener listener = new ButtonListener();
	private Controller controller;
	private ViewerOnlineList viewerOnlineList;
	private JLabel lblHeader = new JLabel ("Game Mode    ", SwingConstants.CENTER);
	private JButton btnG1 = new JButton("Turnbased Co-op");
	private JButton btnG2 = new JButton("1 Writes, 1 Guesses");
	private JButton btnG3 = new JButton("1v1"); 
	private JButton btnBack = new JButton("<-- Back");
	
	
	/**
	 * Constructor, which sets up the panel for the multiplayer mode list.
	 */
	public ViewerMultiplayerMode() {
		setPreferredSize(new Dimension(1200, 800));
		setLayout(new BorderLayout());
		add(titlePanel(), BorderLayout.NORTH);
		add(buttonPanel(), BorderLayout.CENTER);
		
		btnG1.addActionListener(listener);
		btnG2.addActionListener(listener);
		btnG3.addActionListener(listener);
		btnBack.addMouseListener(new BackListener());
		btnBack.addActionListener(listener);
	}
	
	/**
	 * Sets up the upper parts of the panel.
	 * @return returns the panel
	 */
	private JPanel titlePanel() {
		JPanel panel = new JPanel (new BorderLayout());
		panel.setPreferredSize(new Dimension(1200, 200));
		Font titleFont = new Font("SansSerif", Font.PLAIN, 100);
		Font btnFont = new Font("SansSerif", Font.BOLD, 30);
		
		panel.setBackground(Color.DARK_GRAY);
		lblHeader.setFont(titleFont);
		lblHeader.setForeground(Color.WHITE);
		
		btnBack.setFont(btnFont);
		btnBack.setForeground(Color.WHITE);
		btnBack.setContentAreaFilled(false);
		btnBack.setBorderPainted(false);
		
		panel.add(btnBack, BorderLayout.WEST);
		panel.add(lblHeader, BorderLayout.CENTER);
		
		return panel;
	}
	
	/**
	 * Sets up the buttons for the panel
	 * @return returns a panel with buttons.
	 */
	private JPanel buttonPanel() {
		JPanel panel = new JPanel (null);
		panel.setBackground(Color.DARK_GRAY);
		Font font = new Font("SansSerif", Font.PLAIN, 30);
		btnG1.setBounds(450, 50, 300, 100);
		btnG2.setBounds(450, 200, 300, 100);
		btnG3.setBounds(450, 350, 300, 100);
		
		btnG1.setFont(font);
		btnG2.setFont(font);
		btnG3.setFont(font);
		
		panel.add(btnG1);
		panel.add(btnG2);
		panel.add(btnG3);
		
		return panel;
	}
	
	/**
	 * Method which sets the controller to the frame.
	 * @param controller
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	/**
	 * Method which lets the class update the upcoming panel with information.
	 * @param viewer
	 */
	public void setViewerOnlineList(ViewerOnlineList viewer) {
		this.viewerOnlineList = viewer;
	}
	
	/**
	 * Method which sets the listener(interface) to the frame.
	 * @param listener
	 */
	public void setListener(ContinueListener listener) {
		continueListener = listener;
	}
	
	/**
	 * A private class which listens to buttons being pressed.
	 * @author Yun-Fah Chow
	 */
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnG1) {
				continueListener.nextPanelMP();
				viewerOnlineList.setGameModeText("Turnbased Co-op");
				viewerOnlineList.setGameMode("co-op");
				//Set game mode to turn based co-op.
			} else if (e.getSource() == btnG2) {
				continueListener.nextPanelMP();
				viewerOnlineList.setGameModeText("1 Writes, 1 Guesses");
				viewerOnlineList.setGameMode("write-guess");
				//set game mode to 1 writes, 1 guesses (prompt player to enter a word).
			} else if (e.getSource() == btnG3) {
				continueListener.nextPanelMP();
				viewerOnlineList.setGameModeText("1v1");
				viewerOnlineList.setGameMode("1v1");
				//set game mode to 1v1 (both players get the same word.
			} else if(e.getSource() == btnBack) {
				//Go back to username-screen. 
				controller.getClient().logout();
				continueListener.goBackMP();
			}
		}
	}
	
	/**
	 * A private class for the back button so that when you hover, it will turn red. 
	 * @author Yun-Fah Chow
	 */
	private class BackListener implements MouseListener {
		public void mouseClicked(MouseEvent arg0) {}
		
		public void mouseEntered(MouseEvent arg0) {
			btnBack.setForeground(Color.RED);
		}
		
		public void mouseExited(MouseEvent arg0) {
			btnBack.setForeground(Color.WHITE);
		}
		
		public void mousePressed(MouseEvent arg0) {}
		
		public void mouseReleased(MouseEvent arg0) {}
	}
}
