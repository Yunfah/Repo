package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ViewerUsername extends JPanel implements ActionListener, MouseListener, DocumentListener {
	private JLabel title = new JLabel("Please enter a username");
	private JLabel subtitle = new JLabel("(will represent you during this online session)");
	private JLabel name = new JLabel("Username:");
	private JLabel image = new JLabel();
	private JButton btnBack = new JButton("<-- Back");
	private JButton btnNext = new JButton("Continue");
	private JTextField txt = new JTextField();
	private Font btnFont = new Font("SansSerif", Font.BOLD, 30);
	private Font titleFont = new Font("SansSerif", Font.BOLD, 55);
	private Font subtitleFont = new Font("SansSerif", Font.BOLD, 25);
	private ContinueListener continueListener;
	private Controller controller;
	
	public ViewerUsername() {
		setPreferredSize(new Dimension(1200, 800));
		setLayout(null);
		setBackground(Color.DARK_GRAY);
		btnBack.setBounds(0, 0, 200, 100);
		btnBack.setFont(btnFont);
		btnBack.setForeground(Color.WHITE);
		btnBack.setContentAreaFilled(false);
		btnBack.setBorderPainted(false);
		
		title.setBounds(250, 100, 700, 100);
		title.setFont(titleFont);
		title.setForeground(Color.WHITE);
		title.setHorizontalAlignment(JLabel.CENTER);
		
		subtitle.setBounds(250, 200, 700, 50);
		subtitle.setFont(subtitleFont);
		subtitle.setForeground(Color.WHITE);
		subtitle.setHorizontalAlignment(JLabel.CENTER);
		
		name.setBounds(75, 350, 350, 75);
		name.setFont(titleFont);
		name.setForeground(Color.WHITE);
		name.setHorizontalAlignment(JLabel.CENTER);
		
		txt.setBounds(425, 350, 350, 75);
		txt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		txt.setHorizontalAlignment(JLabel.CENTER);
		
		btnNext.setBounds(825, 350, 200, 75);
		btnNext.setFont(btnFont);
		btnNext.setEnabled(false);

		ImageIcon icon = new ImageIcon("files/Hangman GIF.gif");
		image.setBounds(500, 475 ,400,300);
		image.setIcon(icon);
		
		txt.getDocument().addDocumentListener(this);
		btnBack.addMouseListener(this);
		
		add(image);
		add(title);
		add(subtitle);
		add(btnBack);
		add(name);
		add(txt);
		add(btnNext);
	}
	
	public void setListener(ContinueListener listener) {
		continueListener = listener;
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public void checkLogIn() {
		String username = txt.getText();
		if(username.length() > 0) {
			btnNext.setEnabled(true);
		}else {
			btnNext.setEnabled(false);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnBack) {
			continueListener.goBack();
		}
		if(e.getSource()==btnNext) {
			continueListener.nextPanel();
		//	controller.connect(txt.getText());
			//Skickar iväg username till servern
		}
	}
		
	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {
		if(e.getComponent()==btnBack) {
			btnBack.setForeground(Color.RED);
		}
	}

	public void mouseExited(MouseEvent e) {
		if(e.getComponent()==btnBack) {
			btnBack.setForeground(Color.WHITE);
		}
	}

	public void mouseClicked(MouseEvent e) {}
	
	public void insertUpdate(DocumentEvent e) {
		checkLogIn();
    }
	
    public void removeUpdate(DocumentEvent e) {
    	checkLogIn();
    }
    
    public void changedUpdate(DocumentEvent e) {
    	checkLogIn();
    }
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test of username window");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new ViewerUsername());
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);	
	}

}
