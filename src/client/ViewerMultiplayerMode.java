package client;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.BevelBorder;



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
<<<<<<< HEAD
	private JButton btnCoOp = new JButton("Turnbased Co-op");
	private JButton btn1W1G = new JButton("1 Writes, 1 Guesses");
	private JButton btn1v1 = new JButton("1v1"); 
=======
	private JLabel lblImage = new JLabel();
	private Icon iconG1;
	private Icon iconG2;
	private JButton btnG1 = new JButton("Turnbased Co-op");
	private JButton btnG2 = new JButton("1 Writes, 1 Guesses");
	private JButton btnG3 = new JButton("1v1"); 
>>>>>>> ecf7fcb55d89d479efcc348567572e7ed45ecc31
	private JButton btnBack = new JButton("<-- Back");
	private BackListener bListener = new BackListener();
	
	
	/**
	 * Constructor, which sets up the panel for the multiplayer mode list.
	 */
	public ViewerMultiplayerMode() {
		setPreferredSize(new Dimension(1200, 800));
		setLayout(new BorderLayout());
		add(titlePanel(), BorderLayout.NORTH);
		add(buttonPanel(), BorderLayout.CENTER);

		iconG1 = scaleImage("files/helpstickmen.png");
		iconG2 = scaleImage("files/fightingstickmen.png");
		
<<<<<<< HEAD
		btnCoOp.addActionListener(listener);
		btn1W1G.addActionListener(listener);
		btn1v1.addActionListener(listener);
		btnBack.addMouseListener(new BackListener());
=======
		btnG1.addActionListener(listener);
		btnG2.addActionListener(listener);
		btnG3.addActionListener(listener);
		btnBack.addMouseListener(bListener);
		btnG1.addMouseListener(bListener);
		btnG2.addMouseListener(bListener);
		btnG3.addMouseListener(bListener);
>>>>>>> ecf7fcb55d89d479efcc348567572e7ed45ecc31
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
		panel.setBackground(Color.darkGray);
		Font font = new Font("SansSerif", Font.PLAIN, 30);
<<<<<<< HEAD
		btnCoOp.setBounds(450, 50, 300, 100);
		btn1W1G.setBounds(450, 200, 300, 100);
		btn1v1.setBounds(450, 350, 300, 100);
=======
		btnG1.setBounds(450, 50, 300, 100);
		btnG2.setBounds(450, 200, 300, 100);
		btnG3.setBounds(450, 350, 300, 100);
		lblImage.setBounds(75, 100, 300, 300 );
>>>>>>> ecf7fcb55d89d479efcc348567572e7ed45ecc31
		
		btnCoOp.setFont(font);
		btn1W1G.setFont(font);
		btn1v1.setFont(font);

<<<<<<< HEAD
		btnCoOp.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black, Color.black));
		btnCoOp.setBackground(Color.white);
		btnCoOp.setOpaque(true);
		btn1W1G.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black, Color.black));
		btn1W1G.setBackground(Color.white);
		btn1W1G.setOpaque(true);
		btn1v1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black, Color.black));
		btn1v1.setBackground(Color.white);
		btn1v1.setOpaque(true);
		
		panel.add(btnCoOp);
		panel.add(btn1W1G);
		panel.add(btn1v1);
=======
		btnG1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black, Color.black));
		btnG1.setBackground(Color.white);
		btnG1.setOpaque(true);
		btnG2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black, Color.black));
		btnG2.setBackground(Color.white);
		btnG2.setOpaque(false);
		btnG2.setEnabled(false);
		btnG2.setToolTipText("Gamemode Coming soon!");
		btnG3.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black, Color.black));
		btnG3.setBackground(Color.white);
		btnG3.setOpaque(true);

		panel.add(lblImage);
		panel.add(btnG1);
		panel.add(btnG2);
		panel.add(btnG3);
>>>>>>> ecf7fcb55d89d479efcc348567572e7ed45ecc31
		
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
	 * Method which scales a picture to the preferred size
	 * @param filename The path to the picture
	 * @return The scaled version of the picture
	 */
	public ImageIcon scaleImage(String filename) {
		ImageIcon image = new ImageIcon(filename);
		Image transImage = image.getImage();
		Image scaledImage = transImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		image = new ImageIcon(scaledImage);
		return image;
	}
	
	/**
	 * A private class which listens to buttons being pressed.
	 * @author Yun-Fah Chow
	 */
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnCoOp) {
				continueListener.nextPanelMP();
				viewerOnlineList.setGameModeText("Turnbased Co-op");
				viewerOnlineList.setGameMode("co-op");
				//Set game mode to turn based co-op.
			} else if (e.getSource() == btn1W1G) {
				continueListener.nextPanelMP();
				viewerOnlineList.setGameModeText("1 Writes, 1 Guesses");
				viewerOnlineList.setGameMode("write-guess");
				//set game mode to 1 writes, 1 guesses (prompt player to enter a word).
			} else if (e.getSource() == btn1v1) {
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
	 * A private class for the buttons so that when you hover, it will turn red. Does also
	 * update the GUI with a informative picture.
	 * @author Yun-Fah Chow
	 */
	private class BackListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {}
		
		public void mouseEntered(MouseEvent e) {
			if(e.getSource()==btnBack) {
				btnBack.setForeground(Color.RED);
			}
			if(e.getSource()==btnG1) {
				btnG1.setForeground(Color.RED);
				lblImage.setIcon(iconG1);
			}
			if(e.getSource()==btnG2) {
				btnG2.setForeground(Color.RED);
			}
			if(e.getSource()==btnG3) {
				btnG3.setForeground(Color.RED);
				lblImage.setIcon(iconG2);
			}
		}
		
		public void mouseExited(MouseEvent e) {
			if(e.getSource()==btnBack) {
				btnBack.setForeground(Color.white);
			}
			if(e.getSource()==btnG1) {
				btnG1.setForeground(Color.black);
				lblImage.setIcon(null);
			}
			if(e.getSource()==btnG2) {
				btnG2.setForeground(Color.black);
			}
			if(e.getSource()==btnG3) {
				btnG3.setForeground(Color.black);
				lblImage.setIcon(null);
			}
		}
		
		public void mousePressed(MouseEvent e) {}
		
		public void mouseReleased(MouseEvent e) {}
	}
}
