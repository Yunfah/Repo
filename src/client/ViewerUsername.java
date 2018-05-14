package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 
 * @author Jakob Kennerberg
 *
 */
public class ViewerUsername extends JPanel implements ActionListener, MouseListener, DocumentListener, KeyListener {
	private JLabel title = new JLabel("Please enter a username");
	private JLabel subtitle = new JLabel("(will represent you during this online session)");
	private JLabel name = new JLabel("Username:");
	private JLabel image = new JLabel();
	private JButton btnBack = new JButton("<-- Back");
	private JButton btnNext = new JButton("Continue");
	private JTextField txtField = new JTextField();
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
		btnBack.addActionListener(this);

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

		txtField.setBounds(425, 350, 350, 75);
		txtField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		txtField.setHorizontalAlignment(JLabel.CENTER);
		txtField.addKeyListener(this);

		btnNext.setBounds(825, 350, 200, 75);
		btnNext.setFont(btnFont);
		btnNext.setEnabled(false);
		btnNext.addActionListener(this);
		
		ImageIcon icon = new ImageIcon("files/Hangman GIF.gif");
		image.setBounds(500, 475 ,400,300);
		image.setIcon(icon);

		txtField.getDocument().addDocumentListener(this);
		btnBack.addMouseListener(this);

		add(image);
		add(title);
		add(subtitle);
		add(btnBack);
		add(name);
		add(txtField);
		add(btnNext);
		addKeyListener(this);
		setFocusable(true);
	}

	public void setListener(ContinueListener listener) {
		continueListener = listener;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void checkLogIn() {
		String username = txtField.getText();
		if(username.length() > 0) {
			btnNext.setEnabled(true);
		} else {
			btnNext.setEnabled(false);
		}
	}

	public void connect() {
		String ip = "0";
		do {
			try {
				ip = JOptionPane.showInputDialog("What ip do you want to connect to?");
			} catch (NullPointerException e1) {
				ip = "0";
			}
		} while (ip.length() <= 7);

		int port = 1;
		do {
			try {
				port = Integer.parseInt(JOptionPane.showInputDialog("What port do you want to connect to?"));
			} catch (NumberFormatException e2) {
				port = 1;
			} catch (NullPointerException e3) {}
		} while (port < 1024 || port > 65536);

		continueListener.nextPanelMP();
		controller.connect(txtField.getText(), ip, port);
	}

	public void actionPerformed(ActionEvent e) {	
		if(e.getSource()==btnNext) {
			connect();

		} else if(e.getSource()==btnBack) {
			continueListener.goBackMP();
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

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10 || e.getKeyCode() == KeyEvent.VK_ENTER) {
			connect();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
