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

/**
 * This class creates the panel for choosing difficulty in Hangman
 * @author Jakob Kennerberg
 *
 */
public class ViewerSelectDifficulty extends JPanel implements ActionListener, MouseListener {
	private JLabel title = new JLabel("DIFFICULTY    ", SwingConstants.CENTER);
	private JButton btnBack = new JButton("<-- BACK");
	private JButton btnEz = new JButton("EZ");
	private JButton btnDS = new JButton("DARK SOULS");
	private JButton btnEE = new JButton();
	private JButton btnLoadGame = new JButton("Load latest saved game");
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
		setBackground(Color.DARK_GRAY);
		
		btnBack.addMouseListener(this);
		btnBack.addActionListener(this);
		btnEz.addActionListener(this);
		btnDS.addActionListener(this);
		btnLoadGame.addActionListener(this);
		btnLoadGame.addMouseListener(this);
		btnEE.addActionListener(this);
		btnEE.addMouseListener(this);
		
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
		panel.setBackground(Color.DARK_GRAY);
		title.setFont(titlefont);
		title.setForeground(Color.WHITE);
		title.setHorizontalAlignment(JLabel.CENTER);
		btnBack.setPreferredSize(new Dimension(200, 100));
		btnBack.setFont(btnfont);
		btnBack.setForeground(Color.WHITE);
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
		panel.setBackground(Color.DARK_GRAY);
		btnEz.setBounds(450, 0, 300, 100);
		btnDS.setBounds(450, 150, 300, 100);
		btnEz.setFont(btnfont);
		btnDS.setFont(btnfont);
		btnEz.setForeground(Color.GREEN);
		btnDS.setForeground(Color.RED);
		btnEz.setHorizontalAlignment(JButton.CENTER);
		btnDS.setHorizontalAlignment(JButton.CENTER);
		
		panel.add(btnEz);
		panel.add(btnDS);
		
		return panel;
	}
	
	/**
	 * Method which has the purpose to create the bottom panel, including the EE 
	 * and load game buttons.
	 * @returns the bottom panel
	 */
	public JPanel bottomPanel() {
		JPanel panel = new JPanel(null);
		panel.setBackground(Color.DARK_GRAY);
		Font btnfont = new Font("SansSerif", Font.BOLD, 20);
		panel.setBounds(0, 600, 1200, 200);
		btnLoadGame.setBounds(450, 50, 300, 100);
		btnLoadGame.setFont(btnfont);
		btnEE.setBounds(1050, 100, 150, 100);
		btnEE.setContentAreaFilled(false);
		btnEE.setBorderPainted(false);
		
		panel.add(btnEE);
		panel.add(btnLoadGame);
		
		return panel;
	}
	
	/**
	 * Method which has the purpose to make the EE button visible and 
	 * to activate the EE.
	 */
	private void activateEE() {
		Font btnfont = new Font("SansSerif", Font.BOLD, 20);
		btnEE.setFont(btnfont);
		btnEE.setText("Xtreme");
		btnEE.setForeground(randomColor());
		eeActivated = true;
	}
	
	/**
	 * Method which has the purpose to create a random color for usage in
	 * this class.
	 * @returns the color
	 */
	public Color randomColor() {
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		Color randomColor = new Color(r, g, b);
		
		return randomColor;
	}
	
	/**
	 * Method which sets the listener to listen to changes in the frame
	 * @param listener
	 */
	public void setListener(ContinueListener listener) {
		continueListener = listener;
	}
	
	/**
	 * Method which sets the controller
	 * @param controller
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	/**
	 * Method which listens to pressed buttons and perform actions related to this.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnBack) {
			continueListener.goBack();
		}
		if(e.getSource()==btnEz) {
			continueListener.nextPanel();
			controller.setDifficulty(Controller.EZ);
		}
		if(e.getSource()==btnDS) {
			continueListener.nextPanel();
			controller.setDifficulty(Controller.DARK_SOULS);
		}
		if(e.getSource()==btnEE) {
			if(eeActivated==true) {
				continueListener.skipToGame();
				controller.setDifficulty(Controller.XTREME);
				controller.setCategory("files/Xtreme.txt", "XTREME");
			}
			activateEE();
		}
		if(e.getSource()==btnLoadGame) {
			controller.loadSaveFile();
			continueListener.skipToGame();
		}
	}
	
	/**
	 * The following methods belongs to MouseListener, and listens to 
	 * then the mouse interacts with GUI components.
	 */
	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {
		if(e.getComponent()==btnBack) {
			btnBack.setForeground(Color.RED);
		}
		if(e.getComponent()==btnEE) {
			if(eeActivated==true) {
				btnEE.setForeground(randomColor());
			}
		}
		if(e.getComponent()==btnLoadGame) {
			btnLoadGame.setForeground(Color.RED);
		}
	}

	public void mouseExited(MouseEvent e) {
		if(e.getComponent()==btnBack) {
			btnBack.setForeground(Color.WHITE);
		}
		if(e.getComponent()==btnEE) {
			if(eeActivated==true) {
				btnEE.setForeground(randomColor());
			}
		}
		if(e.getComponent()==btnLoadGame) {
			btnLoadGame.setForeground(Color.BLACK);
		}
	}

	public void mouseClicked(MouseEvent e) {}
	
//	public static void main(String[] args) {
//		JFrame frame = new JFrame("Test of difficulty window");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.add(new ViewerSelectDifficulty());
//		frame.pack();
//		frame.setResizable(false);
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);	
//	}
}
