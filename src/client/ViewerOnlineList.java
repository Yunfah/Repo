package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Panel that holds a list of all players currently connected to the server.
 * Also allows players to choose a player from the online list to invite
 * them to a game of hangman. 
 */
public class ViewerOnlineList extends JPanel implements MouseListener {
	private ContinueListener continueListener;
	private Controller controller;
	private JLabel lblHeader = new JLabel("Choose a player     ", SwingConstants.CENTER);
	private JLabel lblOnline = new JLabel("Online");
	private JLabel lblSettings = new JLabel("");	//Should show what gamemode the player has chosen.
	private JButton btnBack = new JButton("<-- Back");
	private JButton btnInvite = new JButton("Invite");
	private ButtonGroup bg = new ButtonGroup();
	private JPanel pnlOnlineList;
	private ArrayList<JRadioButton> rbList = new ArrayList<JRadioButton>();
	private ButtonListener listener = new ButtonListener();
	private RadioButtonListener rbtnListener = new RadioButtonListener();
	private String selectedPlayer;
	private String gamemode;

	/**
	 * Constructor.
	 */
	public ViewerOnlineList() {
		setPreferredSize(new Dimension(1200, 800));
		setLayout(new BorderLayout());
		add(titlePanel(), BorderLayout.NORTH);
		add(mainPanel(), BorderLayout.CENTER);
		btnInvite.addActionListener(listener);
		btnBack.addActionListener(listener);
		btnBack.addMouseListener(this);
	}

	/**
	 * Sets up and returns the top panel holding the header and 
	 * back button for this Viewer. 
	 * @return JPanel to be used at the top of the window. 
	 */
	private JPanel titlePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
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
	 * Sets up and returns the panel containing the list of all currently connected
	 * players and the invite button.
	 * @return JPanel that holds the main functionality of this Viewer.
	 */
	private JPanel mainPanel() {
		JPanel main = new JPanel();
		main.setLayout(null);

		main.setBackground(Color.DARK_GRAY);
		Font btnFont = new Font("SansSerif", Font.BOLD, 30);

		lblOnline.setBounds(100, 0, 200, 100);
		lblOnline.setFont(btnFont);
		lblOnline.setForeground(Color.GREEN);

		pnlOnlineList = new JPanel();
		pnlOnlineList.setLayout(new GridLayout(100,1)); // Change from gridLayout to something better?? + change the values to onlinelist.size to
		// make it only do as many as needed.

		JScrollPane scroll = new JScrollPane(pnlOnlineList);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(100, 70, 400, 500);

		btnInvite.setEnabled(false);
		btnInvite.setBounds(750, 400, 200, 100);
		btnInvite.setFont(btnFont);

		lblSettings.setBounds(600, 20, 500, 100);
		lblSettings.setFont(btnFont);
		lblSettings.setForeground(Color.WHITE);

		getGameModeText();
		main.add(lblOnline);
		main.add(lblSettings);
		main.add(btnInvite);
		main.add(scroll);

		return main;
	} 

	/**
	 * Updates the list showing all currently connected players.
	 * @param onlineList List containing the names of all the currently connected players.
	 */
	public void updateOnlineList(ArrayList<String> onlineList) {
		pnlOnlineList.removeAll();
		pnlOnlineList.repaint();
		for (int i = 0; i < onlineList.size(); i++) { // Change value to onlinelist.size so that it can only show as many as needed
			JRadioButton btn = new JRadioButton(onlineList.get(i));// Change so that the server can read how many buttons it needs.
			btn.setSize(new Dimension(400, 60));
			bg.add(btn);
			rbList.add(btn);
			btn.addActionListener(rbtnListener);
			pnlOnlineList.add(btn);
		}
	}

	/**
	 * Sets the text of the label that shows the chosen game mode.
	 * @param text The name of the chosen game mode.
	 */
	public void setGameModeText(String text) {
		lblSettings.setText("You chose: " + text );
	}

	/**
	 * Returns the name of the chosen game mode.
	 * @return Name of the chosen game mode.
	 */
	public String getGameModeText() {
		return lblSettings.getText();
	}

	/**
	 * Sets the String for the chosen game mode. 
	 * @param gamemode The game mode that has been chosen. 
	 */
	public void setGameMode(String gamemode) {
		this.gamemode = gamemode;
	}

	/**
	 * Returns the name of the chosen game mode.
	 * @return The name of the chosen game mode.
	 */
	public String getGameMode() {
		return gamemode;
	}

	/**
	 * Sets the controller for this Viewer.
	 * @param controller The controller to be used for this Viewer. 
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * 
	 * @param listener
	 */
	public void setListener(ContinueListener listener) {
		continueListener = listener;
	}

	/**
	 * Shows a dialog confirming that an invite has been sent to selectedPlayer
	 * and that a response is being awaited.
	 * @param selectedPlayer The player that was chosen for an invite.
	 */
	public void inviteMessage(String selectedPlayer) {
//		String[] options = {"Cancel Invite"};
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("Invite sent to " + selectedPlayer + ". Awaiting response...");
		panel.add(lbl);
		JFrame frame = new JFrame();
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnBack) {
				continueListener.goBackMP();

			} else if (e.getSource() == btnInvite) {
				inviteMessage(selectedPlayer);
				controller.sendInvite(selectedPlayer, gamemode);
				System.out.println("Heja");
			}
		}
	}

	/**
	 * Enables the invite-button as soon as a player is chosen from 
	 * the online list.
	 * @author Jakob Kennerberg
	 *
	 */
	private class RadioButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			btnInvite.setEnabled(true);
			try {
				JRadioButton rb = (JRadioButton)e.getSource();
				selectedPlayer = rb.getText();
			} catch (Exception e1) {}
		}
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {
		if(e.getComponent()==btnBack) {
			btnBack.setForeground(Color.RED);
		}
	}
	public void mouseExited(MouseEvent e) {
		if(e.getComponent()==btnBack) {
			btnBack.setForeground(Color.WHITE);
		}
	}
	public void mouseClicked(MouseEvent e) {}
}
