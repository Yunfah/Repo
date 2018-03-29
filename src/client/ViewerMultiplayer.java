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
 * Lets the player choose another online player from a list
 * so that they can play together.
 *
 */
public class ViewerMultiplayer extends JPanel {
	private ContinueListener continueListener;
	private ButtonListener listener = new ButtonListener();
	private Controller controller;
	private JLabel lblHeader = new JLabel ("Game Mode    ", SwingConstants.CENTER);
	private JButton btnG1 = new JButton("Turnbased Co-op");
	private JButton btnG2 = new JButton("1 Writes, 1 Guesses");
	private JButton btnG3 = new JButton("1v1");
	private JButton btnBack = new JButton("<-- Back");
	
	
	public ViewerMultiplayer() {
		setPreferredSize(new Dimension(1200, 800));
		setLayout(new BorderLayout());
		add(titlePanel(), BorderLayout.NORTH);
		add(buttonPanel(), BorderLayout.CENTER);
		
		btnBack.addMouseListener(new BackListener());
		btnBack.addActionListener(listener);
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
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
	
	public void setListener(ContinueListener listener) {
		continueListener = listener;
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == btnG1) {
				//Do this
			} else if (e.getSource() == btnG2) {
				//Do this
			} else if (e.getSource() == btnG3) {
				//Do this
			} else if(e.getSource() == btnBack) {
				//Do this
				continueListener.goBackMP();
			}
		}
		
	}
	
	private class BackListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) {}
		@Override
		public void mouseEntered(MouseEvent arg0) {
			btnBack.setForeground(Color.RED);
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
			btnBack.setForeground(Color.WHITE);
		}
		@Override
		public void mousePressed(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test of Multiplayer window");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new ViewerMultiplayer());
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
