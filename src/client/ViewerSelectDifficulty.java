package client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.*;

public class ViewerSelectDifficulty extends JPanel implements ActionListener, MouseListener {
	private JLabel title = new JLabel("DIFFICULTY    ");
	private JButton btnBack = new JButton("<-- BACK");
	private JButton btnEz = new JButton("EZ");
	private JButton btnDS = new JButton("DARK SOULS");
	private JButton btnEE = new JButton();
	private boolean eeActivated = false;
	private Random rand = new Random();
	private ContinueListener continueListener;
	private Controller controller;
	
	public ViewerSelectDifficulty() {
		setPreferredSize(new Dimension(1200, 800));
		setBackground(Color.DARK_GRAY);
		
		btnBack.addMouseListener(this);
		btnBack.addActionListener(this);
		btnEz.addActionListener(this);
		btnDS.addActionListener(this);
		btnEE.addActionListener(this);
		btnEE.addMouseListener(this);
		
		add(titlePanel(), BorderLayout.NORTH);
		add(btnPanel(), BorderLayout.CENTER);
		add(bottomPanel(), BorderLayout.SOUTH);
	}
	
	public JPanel titlePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(1200, 200));
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
	
	public JPanel btnPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(350, 400));
		Font btnfont = new Font("SansSerif", Font.BOLD, 30);
		panel.setBackground(Color.DARK_GRAY);
		btnEz.setPreferredSize(new Dimension(300,150));
		btnDS.setPreferredSize(new Dimension(300,150));
		btnEz.setFont(btnfont);
		btnDS.setFont(btnfont);
		btnEz.setForeground(Color.GREEN);
		btnDS.setForeground(Color.RED);
		btnEz.setHorizontalAlignment(JButton.CENTER);
		btnDS.setHorizontalAlignment(JButton.CENTER);
		
		panel.add(btnEz, BorderLayout.NORTH);
		panel.add(btnDS, BorderLayout.SOUTH);
		
		return panel;
	}
	
	public JPanel bottomPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.DARK_GRAY);
		panel.setPreferredSize(new Dimension(1200, 200));
		btnEE.setPreferredSize(new Dimension(150, 50));
		btnEE.setContentAreaFilled(false);
		btnEE.setBorderPainted(false);
		
		panel.add(btnEE, BorderLayout.EAST);
		
		return panel;
	}
	
	private void activateEE() {
		Font btnfont = new Font("SansSerif", Font.BOLD, 20);
		btnEE.setFont(btnfont);
		btnEE.setText("Xtreme");
		btnEE.setForeground(randomColor());
		eeActivated = true;
	}
	
	public Color randomColor() {
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		Color randomColor = new Color(r, g, b);
		
		return randomColor;
	}

	public void setListener(ContinueListener listener) {
		continueListener = listener;
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnBack) {
			continueListener.goBack();
		}
		if(e.getSource()==btnEz) {
			continueListener.nextPanel();
			//sätt difficulty till easy
		}
		if(e.getSource()==btnDS) {
			continueListener.nextPanel();
			//sätt difficulty till hard
		}
		if(e.getSource()==btnEE) {
			if(eeActivated==true) {
				continueListener.nextPanel();
				//sätt difficulty till xtreme
			}
			activateEE();
		}
	}
		
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
	}

	public void mouseClicked(MouseEvent e) {}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test of difficulty window");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new ViewerSelectDifficulty());
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);	
	}
}
