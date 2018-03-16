package client;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ViewerSelectCategory extends JPanel {
	private JButton btnRandom = new JButton("Random");
	private JButton btnCategory1 = new JButton("Cat. 1"); //change button names when categories have been identified.
	private JButton btnCategory2 = new JButton("Cat. 2");
	private JButton btnCategory3 = new JButton("Cat. 3");
	private JLabel lblCategory = new JLabel("CATEGORY", SwingConstants.CENTER);
	private JPanel pnlButtons = new JPanel();
	
	private ButtonListener listener = new ButtonListener();
	
	public ViewerSelectCategory() {
		setPreferredSize(new Dimension(1200,800));
		setLayout(new BorderLayout());
		
		lblCategory.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 80));
		
		pnlButtons.setLayout(null);
		btnRandom.setBounds(450, 50, 300, 100);
		btnCategory1.setBounds(450, 200, 300, 100);
		btnCategory2.setBounds(450, 350, 300, 100);
		btnCategory3.setBounds(450, 500, 300, 100);
		pnlButtons.add(btnRandom);
		pnlButtons.add(btnCategory1);
		pnlButtons.add(btnCategory2);
		pnlButtons.add(btnCategory3);
		
		add(lblCategory, BorderLayout.NORTH);
		add(pnlButtons, BorderLayout.CENTER);
		
		btnRandom.addActionListener(listener);
		btnCategory1.addActionListener(listener);
		btnCategory2.addActionListener(listener);
		btnCategory3.addActionListener(listener);
	}
	

	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnRandom) {
				
			} else if (e.getSource() == btnCategory1) {
				
			} else if (e.getSource() == btnCategory2) {
				
			} else if (e.getSource() == btnCategory3) {
				
			}
			
		}
	}

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
