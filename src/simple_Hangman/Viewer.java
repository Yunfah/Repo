package simple_Hangman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Viewer extends JPanel {
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int w = screenSize.width / 2;
	private int h = screenSize.height / 2;
	
	
	public Viewer() {
		setPreferredSize(new Dimension(w, h));
		this.setLayout(null);
		
		this.add(topPanel());
		this.add(bottomPanel());

	}

	private JPanel topPanel() {
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, w, h-h/3);
		panel.setBackground(Color.GREEN);
		
		return panel;
	}
	
	private JPanel bottomPanel() {
		JPanel panel = new JPanel();
		panel.setBounds(0, h-h/3, w, h/3);
		panel.setBackground(Color.RED);
		
		return panel;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Viewer());
		frame.pack();
		frame.setVisible(true);
	}
}
