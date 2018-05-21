package client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 * This class creates the panel for choosing difficulty in Hangman
 * @author Jakob Kennerberg
 */
public class ViewerSelectDifficulty extends JPanel implements ActionListener, MouseListener {
	private JLabel title = new JLabel("DIFFICULTY    ", SwingConstants.CENTER);
	private JLabel ezHelp = new JLabel("", SwingConstants.CENTER);
	private JLabel dsHelp = new JLabel("",  SwingConstants.CENTER);
	private JButton btnBack = new JButton("<-- BACK");
	private JButton btnEz = new JButton("EZ");
	private JButton btnDarkSouls = new JButton("DARK SOULS");
	private JButton btnXtreme = new JButton();
	private JButton btnLoadGame = new JButton("Load latest saved game");
	private Font helpfont = new Font("SansSerif", Font.BOLD, 20);
	private boolean eeActivated = false;
	private Random rand = new Random();
	private ContinueListener continueListener;
	private Controller controller;
	
	/**
	 * Constructor
	 */
	public ViewerSelectDifficulty() {
		setPreferredSize(new Dimension(1200, 800));
		setLayout(null);
		setBackground(Color.darkGray);
		
		btnBack.addMouseListener(this);
		btnBack.addActionListener(this);
		btnEz.addActionListener(this);
		btnDarkSouls.addActionListener(this);
		btnEz.addMouseListener(this);
		btnDarkSouls.addMouseListener(this);
		btnLoadGame.addActionListener(this);
		btnLoadGame.addMouseListener(this);
		btnXtreme.addActionListener(this);
		btnXtreme.addMouseListener(this);
		
		add(titlePanel());
		add(btnPanel());
		add(bottomPanel());
	}
	
	/**
	 * Method which has the purpose to create the upper panel, including
	 * the back button and title.
	 * @returns the upper panel
	 */
	public JPanel titlePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(1200, 200));
		panel.setBounds(0, 0, 1200, 150);
		Font titlefont = new Font("SansSerif", Font.PLAIN, 125);
		Font btnfont = new Font("SansSerif", Font.BOLD, 30);
		panel.setBackground(Color.darkGray);
		title.setFont(titlefont);
		title.setForeground(Color.white);
		title.setHorizontalAlignment(JLabel.CENTER);
		btnBack.setPreferredSize(new Dimension(200, 100));
		btnBack.setFont(btnfont);
		btnBack.setForeground(Color.white);
		btnBack.setContentAreaFilled(false);
		btnBack.setBorderPainted(false);
		
		panel.add(btnBack, BorderLayout.WEST);
		panel.add(title, BorderLayout.CENTER);
		
		return panel;
	}
	
	/**
	 * Method which has the purpose to create the central panel, including the difficulty
	 * buttons.
	 * @returns the middle panel
	 */
	public JPanel btnPanel() {
		JPanel panel = new JPanel(null);
		panel.setBounds(0, 200, 1200, 400);
		Font btnfont = new Font("SansSerif", Font.BOLD, 30);
		panel.setBackground(Color.darkGray);
		btnEz.setBounds(450, 0, 300, 100);
		btnDarkSouls.setBounds(450, 150, 300, 100);
		btnEz.setFont(btnfont);
		btnDarkSouls.setFont(btnfont);
		btnEz.setForeground(Color.GREEN);
		btnDarkSouls.setForeground(Color.RED);
		btnEz.setHorizontalAlignment(JButton.CENTER);
		btnDarkSouls.setHorizontalAlignment(JButton.CENTER);
		btnDarkSouls.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black, Color.black));
		btnEz.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black, Color.black));
		btnDarkSouls.setBackground(Color.white);
		btnDarkSouls.setOpaque(true);
		btnEz.setBackground(Color.white);
		btnEz.setOpaque(true);
		ezHelp.setBounds(800, 0, 350, 100);
		dsHelp.setBounds(800, 150, 350, 100);
		panel.add(dsHelp);
		panel.add(ezHelp);
		panel.add(btnEz);
		panel.add(btnDarkSouls);
		
		return panel;
	}
	
	/**
	 * Method which has the purpose to create the bottom panel, including the EE 
	 * and load game buttons.
	 * @returns the bottom panel
	 */
	public JPanel bottomPanel() {
		JPanel panel = new JPanel(null);
		panel.setBackground(Color.darkGray);
		Font btnfont = new Font("SansSerif", Font.BOLD, 20);
		panel.setBounds(0, 600, 1200, 200);
		btnLoadGame.setBounds(450, 50, 300, 100);
		btnLoadGame.setFont(btnfont);
		btnLoadGame.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black, Color.black));
		btnLoadGame.setOpaque(false);
		btnLoadGame.setEnabled(false);
		btnLoadGame.setToolTipText("Feature Coming soon!");
		btnXtreme.setBounds(1050, 100, 150, 100);
		btnXtreme.setContentAreaFilled(false);
		btnXtreme.setBorderPainted(false);
		
		panel.add(btnXtreme);
		panel.add(btnLoadGame);
		
		return panel;
	}
	
	/**
	 * Method which has the purpose to make the EE button visible and 
	 * to activate the EE.
	 */
	private void activateEE() {
		Font btnfont = new Font("SansSerif", Font.BOLD, 20);
		btnXtreme.setFont(btnfont);
		btnXtreme.setText("Xtreme");
		btnXtreme.setForeground(randomColor());
		eeActivated = true;
	}
	
	/**
	 * Method which has the purpose to create a random color for (secret)usage in
	 * this class.
	 * @returns A random color
	 */
	public Color randomColor() {
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		Color randomColor = new Color(r, g, b);
		
		return randomColor;
	}
	
	/**
	 * Method which sets the listener(interface) to listen to changes in the frame
	 * @param listener The continueListener to control the flow from/to this frame
	 */
	public void setListener(ContinueListener listener) {
		continueListener = listener;
	}
	
	/**
	 * Method which sets the controller
	 * @param controller The controller to be used by this frame 
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	/**
	 * Method which listens to pressed buttons and performs actions related to them.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnBack) {
			continueListener.goBack();
		}
		if(e.getSource()==btnEz) {
			continueListener.nextPanel();
			controller.setDifficulty(Controller.EZ);
		}
		if(e.getSource()==btnDarkSouls) {
			continueListener.nextPanel();
			controller.setDifficulty(Controller.DARK_SOULS);
		}
		if(e.getSource()==btnXtreme) {
			if(eeActivated==true) {
				continueListener.skipToGame();
				controller.setDifficulty(Controller.XTREME);
				controller.setCategoryWord("files/Xtreme.txt", "XTREME");
			}
			activateEE();
		}
		if(e.getSource()==btnLoadGame) {
			controller.loadSaveFile();
			continueListener.skipToGame();
		}
	}

	 //The following methods belong to MouseListener, and listens to 
	 //then the mouse interactions with GUI components.
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {
		if(e.getComponent()==btnBack) {
			btnBack.setForeground(Color.RED);
		}
		if(e.getComponent()==btnXtreme) {
			if(eeActivated==true) {
				btnXtreme.setForeground(randomColor());
			}
		}
		if(e.getComponent()==btnLoadGame) {
			btnLoadGame.setForeground(Color.RED);
		}
		if(e.getComponent()==btnEz) {
			ezHelp.setText("<html>Easy Difficulty<br/>Player gets 10 lives");
			ezHelp.setFont(helpfont);
			ezHelp.setForeground(Color.GREEN);
		}
		if(e.getComponent()==btnDarkSouls) {
			dsHelp.setText("<html>Hard Difficulty<br/>Player gets 6 lives");
			dsHelp.setFont(helpfont);
			dsHelp.setForeground(Color.RED);
		}
	}

	public void mouseExited(MouseEvent e) {
		if(e.getComponent()==btnBack) {
			btnBack.setForeground(Color.white);
		}
		if(e.getComponent()==btnXtreme) {
			if(eeActivated==true) {
				btnXtreme.setForeground(randomColor());
			}
		}
		if(e.getComponent()==btnLoadGame) {
			btnLoadGame.setForeground(Color.BLACK);
		}
		if(e.getComponent()==btnEz) {
			ezHelp.setText(null);
		}
		if(e.getComponent()==btnDarkSouls) {
			dsHelp.setText(null);
		}
	}
	public void mouseClicked(MouseEvent e) {}
}
