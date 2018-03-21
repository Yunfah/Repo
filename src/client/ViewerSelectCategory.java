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
import java.util.LinkedList;

import javax.swing.*;

public class ViewerSelectCategory extends JPanel {
	private JButton btnRandom = new JButton("Random");
	private JButton btnCategory1 = new JButton("Cat. 1"); //change button names when categories have been identified.
	private JButton btnCategory2 = new JButton("Cat. 2");
	private JButton btnCategory3 = new JButton("Cat. 3");
	private JButton btnBack = new JButton("<-- BACK");
	
	private JLabel lblCategory = new JLabel("CATEGORY    ", SwingConstants.CENTER);
	
	private ButtonListener listener = new ButtonListener();
	private ContinueListener continueListener;
	
	public ViewerSelectCategory() {
		setPreferredSize(new Dimension(1200,800));
		setLayout(new BorderLayout());
		
		add(pnlNorth(), BorderLayout.NORTH);
		add(pnlButtons(), BorderLayout.CENTER);
		
		btnRandom.addActionListener(listener);
		btnCategory1.addActionListener(listener);
		btnCategory2.addActionListener(listener);
		btnCategory3.addActionListener(listener);
		btnBack.addActionListener(listener);
		btnBack.addMouseListener(new BackListener());
	}
	
	private JPanel pnlButtons() {
		JPanel panel = new JPanel(null);
		Font btnFont = new Font("SansSerif", Font.BOLD, 30);
		
		btnRandom.setBounds(450, 50, 300, 100);
		btnCategory1.setBounds(450, 200, 300, 100);
		btnCategory2.setBounds(450, 350, 300, 100);
		btnCategory3.setBounds(450, 500, 300, 100);
		btnRandom.setFont(btnFont);
		btnCategory1.setFont(btnFont);
		btnCategory2.setFont(btnFont);
		btnCategory3.setFont(btnFont);	
		
		panel.setBackground(Color.DARK_GRAY);
		panel.add(btnRandom);
		panel.add(btnCategory1);
		panel.add(btnCategory2);
		panel.add(btnCategory3);
		
		return panel;
	}
	
	private JPanel pnlNorth() {
		JPanel panel = new JPanel();
		Font btnFont = new Font("SansSerif", Font.BOLD, 30);

		panel.setBackground(Color.DARK_GRAY);
		panel.setLayout(new BorderLayout());
		panel.add(lblCategory, BorderLayout.CENTER);
		panel.add(btnBack, BorderLayout.WEST);
		
		btnBack.setFont(btnFont);
		btnBack.setForeground(Color.WHITE);
		btnBack.setContentAreaFilled(false);
		btnBack.setBorderPainted(false);
		
		lblCategory.setFont(new Font("SansSerif", Font.PLAIN, 125));
		lblCategory.setForeground(Color.WHITE);
		
		return panel;
	}
	
	public void setListener(ContinueListener listener) {
		continueListener = listener;
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnRandom) {
				continueListener.nextPanel();
				//Ge ett slumpvalt ord av alla ord som finns
			} else if (e.getSource() == btnCategory1) {
				continueListener.nextPanel();
				//ge ett ord från denna kategori
			} else if (e.getSource() == btnCategory2) {
				continueListener.nextPanel();
				//ge ett ord från denna kategori
			} else if (e.getSource() == btnCategory3) {
				continueListener.nextPanel();
				//ge ett ord från denna kategori
			} else if (e.getSource() == btnBack) {
				continueListener.goBack();
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

	//Test method for this viewer class
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test of category window");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new ViewerSelectCategory());
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
}
