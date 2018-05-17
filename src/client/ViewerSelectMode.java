package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.BevelBorder;

/**
 * This class contains the first frame to show when starting the application
 * @author Jakob Kennerberg
 *
 */
public class ViewerSelectMode extends JPanel {
	private JLabel lblHeader = new JLabel("Hangman", SwingConstants.CENTER);
	private JLabel lblSingle = new JLabel();
	private JLabel lblMulti = new JLabel();
	private JButton btnSingle = new JButton("Singleplayer");
	private JButton btnMulti = new JButton(" Multiplayer ");
	private Icon icon;
	private ButtonListener listener = new ButtonListener();
	private ContinueListener continueListener;
	private Controller controller;
	
	/**
	 * Constructor
	 */
	public ViewerSelectMode() {
		setPreferredSize(new Dimension (1200,800));
		setLayout(new BorderLayout());
		setBackground(Color.DARK_GRAY);
		icon = new ImageIcon("files/running stick.gif");
		
		add(titlePanel(), BorderLayout.NORTH);
		add(buttonPanel(), BorderLayout.CENTER);
	}
	
	/**
	 * Method which creates the upper panel of the frame
	 * @return The upper panel
	 */
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
	
	/**
	 * Method which creates the central and bottom panel of the frame
	 * @return The panel containing the buttons
	 */
	private JPanel buttonPanel() {
		JPanel bPanel = new JPanel(null);
		bPanel.setBackground(Color.WHITE);
				
		btnSingle.setBounds(450, 50, 300, 100);
		btnMulti.setBounds(450, 200, 300, 100);
			
		btnSingle.setFont(new Font ("Sans Serif", Font.BOLD, 30));
		btnMulti.setFont(new Font ("Sans Serif", Font.BOLD, 30));
		btnSingle.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK, Color.DARK_GRAY));
		btnMulti.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK, Color.DARK_GRAY));
		
		btnSingle.addActionListener(listener);
		btnMulti.addActionListener(listener);
		btnSingle.addMouseListener(listener);
		btnMulti.addMouseListener(listener);
		
		lblMulti.setBounds(825, 250, 350, 325);
		lblSingle.setBounds(75, 250, 330, 325);
		
		bPanel.add(lblMulti);
        bPanel.add(lblSingle);	
		bPanel.add(btnSingle);
		bPanel.add(btnMulti);
		
		return bPanel;
	}
	
	/**
	 * Method which sets the listener(interface) to the frame
	 * @param listener The listener to be set
	 */
	public void setListener(ContinueListener listener) {
		continueListener = listener;
	}
	
	/**
	 * Method which sets the controller to the frame
	 * @param controller The controller to be set
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	/**
	 * An inner class which listens to the buttons of the panel
	 * @author Jakob Kennerberg
	 *
	 */
	private class ButtonListener implements ActionListener, MouseListener {
		
		/**
		 * Method which handles the actions of the buttons when clicked
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnSingle) {
				continueListener.nextPanel();
				controller.setMode(Controller.SINGLE_PLAYER);
				System.out.println("singleplayer chosen");
			} else if (e.getSource() == btnMulti) {
				controller.setMode(Controller.MULTIPLAYER);
				continueListener.nextPanelMP();
			}
		}

		public void mouseClicked(MouseEvent e) {}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}
		
		/**
		 * Method which handles the actions of the buttons when hovered above
		 */
		public void mouseEntered(MouseEvent e) {
			if(e.getSource()==btnSingle) {
				btnSingle.setForeground(Color.RED);
				lblSingle.setIcon(icon);
			}
			if(e.getSource()==btnMulti) {
				btnMulti.setForeground(Color.RED);
				lblSingle.setIcon(icon);
				lblMulti.setIcon(icon);
			}
		}
		
		/**
		 * Method which handles the actions of the button when not hovered above anymore
		 */
		public void mouseExited(MouseEvent e) {
			if(e.getSource()==btnSingle) {
				btnSingle.setForeground(Color.BLACK);
				lblSingle.setIcon(null);
			}
			if(e.getSource()==btnMulti) {
				btnMulti.setForeground(Color.BLACK);
				lblSingle.setIcon(null);
				lblMulti.setIcon(null);
			}
		}
	}
}
