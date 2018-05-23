package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.BevelBorder;

/**
 * Panel that holds a list of all players currently connected to the server.
 * Also allows players to choose a player from the online list to invite
 * them to a game of hangman. 
 * @author Elina Kock, Jakob Kennerberg, Yun-Fah Chow
 */
public class ViewerOnlineList extends JPanel implements MouseListener {
	private ContinueListener continueListener;
	private Controller controller;
	private JLabel lblHeader = new JLabel("Choose a player     ", SwingConstants.CENTER);
	private JLabel lblOnline = new JLabel("Online");
	private JLabel lblGameMode = new JLabel("");
	private JLabel lblUsername = new JLabel("");
	private JButton btnBack = new JButton("<-- Back");
	private JButton btnInvite = new JButton("Invite");
	private ButtonGroup bg = new ButtonGroup();
	private JPanel pnlOnlineList;
	private ArrayList<JRadioButton> rbList = new ArrayList<JRadioButton>();
	private ButtonListener listener = new ButtonListener();
	private RadioButtonListener rbtnListener = new RadioButtonListener();
	private String selectedPlayer;
	private String gamemode;
	private JFrame pendingInviteFrame = new JFrame();
	private JLabel lbl = new JLabel();

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
		btnInvite.addMouseListener(this);
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
		Font font = new Font("SansSerif", Font.BOLD, 30); 

		lblOnline.setBounds(100, 0, 200, 100);
		lblOnline.setFont(font);
		lblOnline.setForeground(Color.GREEN);

		pnlOnlineList = new JPanel();
		pnlOnlineList.setLayout(new GridLayout(100,1)); 
		
		JScrollPane scroll = new JScrollPane(pnlOnlineList);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(100, 70, 400, 500);

		btnInvite.setEnabled(false);
		btnInvite.setBounds(750, 400, 200, 100);
		btnInvite.setFont(font);
		btnInvite.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black, Color.black));

		lblGameMode.setBounds(600, 20, 500, 50);
		lblGameMode.setFont(font);
		lblGameMode.setForeground(Color.WHITE);
		
		lblUsername.setBounds(600, 70, 400, 50);
		lblUsername.setFont(font);
		lblUsername.setForeground(Color.CYAN);

		main.add(lblOnline);
		main.add(lblGameMode);
		main.add(lblUsername);
		main.add(btnInvite);
		main.add(scroll);

		initializeInviteMessage();

		return main;
	} 

	/**
	 * Updates the list showing all currently connected players.
	 * @param onlineList List containing the names of all the currently connected players.
	 */
	public void updateOnlineList(ArrayList<String> onlineList) {
		pnlOnlineList.removeAll();
		pnlOnlineList.repaint();
		for (int i = 0; i < onlineList.size(); i++) {
			JRadioButton btn = new JRadioButton(onlineList.get(i));
			btn.setSize(new Dimension(400, 60));
			bg.add(btn);
			rbList.add(btn);
			btn.addActionListener(rbtnListener);
			pnlOnlineList.add(btn);
			if(btn.getText().equals(controller.getClient().getUsername())) {
				btn.setEnabled(false);
			}
		}
	}

	/**
	 * Sets the text of the label that shows the chosen game mode.
	 * @param text The name of the chosen game mode.
	 */
	public void setGameModeText(String text) {
		lblGameMode.setText("You chose: " + text );
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
	 * Method which updates the panel with the username you have chosen.
	 * @param username The username of this client. 
	 */
	public void setUsername(String username) {
		lblUsername.setText(username);
	}

	/**
	 * Sets the controller for this Viewer.
	 * @param controller The controller to be used for this Viewer. 
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * Method which sets the listener(interface) to the frame
	 * @param listener The ContinueListener that will control panel switching from this frame.
	 */
	public void setListener(ContinueListener listener) {
		continueListener = listener;
	}

	/**
	 * Shows a dialog confirming that an invite has been sent to selectedPlayer
	 * and that a response is being awaited.
	 */
	public void initializeInviteMessage() {
		JPanel panel = new JPanel();
		panel.add(lbl);
		pendingInviteFrame.add(panel);
		pendingInviteFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		pendingInviteFrame.pack();
	}

	/**
	 * Method which sets the text in the frame and makes it visible
	 * @param txt The text to set
	 */
	public void setInviteMessage(String txt) {
		lbl.setText(txt);
		pendingInviteFrame.pack();
		pendingInviteFrame.setVisible(true);
		pendingInviteFrame.setLocationRelativeTo(null);
	}

	/**
	 * Method which closes the window shown when inviting someone
	 */
	public void closePendingInviteMessage() {
		pendingInviteFrame.dispatchEvent(new WindowEvent(pendingInviteFrame, WindowEvent.WINDOW_CLOSING));
	}

	/**
	 * Methods which listen to the mouse hovering over the buttons, changing
	 * the color then it happens.
	 */
	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {
		if(e.getComponent()==btnBack) {
			btnBack.setForeground(Color.RED);
		}
		if(e.getSource()==btnInvite) {
			btnInvite.setForeground(Color.RED);
		}
	}

	public void mouseExited(MouseEvent e) {
		if(e.getComponent()==btnBack) {
			btnBack.setForeground(Color.WHITE);
		}
		if(e.getSource()==btnInvite) {
			btnInvite.setForeground(Color.BLACK);
		}
	}

	public void mouseClicked(MouseEvent e) {}

	/**
	 * An inner class that listens to the input made by clicking on the buttons
	 * and performs actions accordingly
	 * @author Jakob Kennerberg
	 */
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnBack) {
				continueListener.goBackMP();

			} else if (e.getSource() == btnInvite) {
				setInviteMessage("Invite sent to " + selectedPlayer + ". Awaiting response...");
				controller.sendInvite(selectedPlayer, gamemode);
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
			btnInvite.setBackground(Color.white);
			btnInvite.setOpaque(true);
			try {
				JRadioButton rb = (JRadioButton)e.getSource();
				selectedPlayer = rb.getText();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}