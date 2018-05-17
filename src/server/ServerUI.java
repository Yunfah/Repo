package server;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 * UI class for the server. Displays the ip of the server and
 * allows the choice of port to be made before starting the server.
 * Starts the server when the port has been chosen. 
 * @author Elina Kock
 *
 */
public class ServerUI extends JFrame {
	private JTextField tfPort = new JTextField();
	private JLabel lblCurrentIP = new JLabel(getIP());
	private JLabel lblImage = new JLabel();
	private JLabel lblCurrentPort = new JLabel("Port: ");
	private JButton btnConfirm = new JButton("Confirm");
	private Icon icon;
	private JPanel panel = new JPanel();
	private Listener listener = new Listener();
	
	/**
	 * Constructor.
	 */
	public ServerUI() {
		super("Server");
		setSize(new Dimension(400, 300));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		add(setupUI());
		pack();
		setVisible(true);
	}
	
	/**
	 * Sets up the graphical components of this server UI.
	 * @return JPanel with all the components of this server UI.
	 */
	private JPanel setupUI() {
		panel.setPreferredSize(new Dimension(500,250));
		panel.setLayout(null);
		panel.setBackground(Color.white);
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK));
		Font fontLabels = new Font("SansSerif", Font.BOLD, 18);
		
		int alignementX = 50;
		
		JLabel lblListenPort = new JLabel("Listen on port:");
		lblListenPort.setBounds(alignementX, 20, 150, 30);
		lblListenPort.setFont(fontLabels);
		panel.add(lblListenPort);

		icon = new ImageIcon("files/gears.gif");
		
		tfPort.setBounds(alignementX, 55, 150, 30);
		tfPort.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK));
		panel.add(tfPort);
		
		lblCurrentPort.setBounds(alignementX, 85, 100, 30);
		lblCurrentPort.setFont(fontLabels);
		panel.add(lblCurrentPort);
		
		JLabel lblServer = new JLabel("Server IP:");
		lblServer.setBounds(alignementX, 130, 100, 30);
		lblServer.setFont(fontLabels);
		panel.add(lblServer);

		lblImage.setBounds(345, 5, 150, 240);

		panel.add(lblImage);

		
		lblCurrentIP.setBounds(alignementX, 170, 150, 30);
		lblCurrentIP.setFont(fontLabels);
		panel.add(lblCurrentIP);
		
		btnConfirm.setBounds(alignementX+200, 55, 100, 30);
		btnConfirm.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK));
		btnConfirm.setBackground(Color.red);
		btnConfirm.setOpaque(true);
		btnConfirm.addActionListener(listener);
		panel.add(btnConfirm);
		
		return panel;
	}
	
	/**
	 * Returns the ip of the machine that is currently running this
	 * program.
	 * @return The IP address of this machine.
	 */
	public String getIP() {
		try {
			InetAddress localIP = InetAddress.getLocalHost();
			return localIP.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "Failed to resolve IP";
	}
	
	/**
	 * Listens on the button for confirming the chosen port.
	 * Shows an error message if the port is already in use.
	 *
	 */
	private class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			int port = (Integer.parseInt(tfPort.getText()));
			
			//Check if port is available
			try (Socket portTest = new Socket( "localhost", port)) {	//port unavailable
				tfPort.setText("");
				System.out.println(portTest);
				JOptionPane.showMessageDialog(null, "Port unavailable");
			} catch (IOException e1) {									//Port is available
				tfPort.setEnabled(false);
				lblCurrentPort.setText(lblCurrentPort.getText() + port);
				
				JLabel lbl = new JLabel("Server running...");
				lbl.setBounds(180, 100, 250, 50);
				lbl.setFont(new Font("SansSerif", Font.BOLD, 25));
				lbl.setForeground(Color.RED);
				lblImage.setIcon(icon);
				btnConfirm.setBackground(Color.green);
				panel.add(lbl);
				panel.repaint();
				
				Server server = new Server(port);
			}	
		}	
	}
}
