package server;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

public class ServerUI extends JFrame {
	private JTextField tfPort = new JTextField();
	private JLabel lblCurrentIP = new JLabel(getIP());
	private JLabel lblCurrentPort = new JLabel("Port: ");
	private JButton btnConfirm = new JButton("Confirm");
	private JPanel panel = new JPanel();
	
	public ServerUI() {
		setSize(new Dimension(400, 300));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		add(setupUI());
		pack();
		setVisible(true);
	}
	
	private JPanel setupUI() {
		panel.setPreferredSize(new Dimension(500,250));
		panel.setLayout(null);
		panel.setBackground(Color.GREEN);
		Font fontLabels = new Font("SansSerif", Font.BOLD, 18);
		
		int alignementX = 50;
		
		JLabel lblListenPort = new JLabel("Listen on port:");
		lblListenPort.setBounds(alignementX, 20, 150, 30);
		lblListenPort.setFont(fontLabels);
		panel.add(lblListenPort);
		
		tfPort.setBounds(alignementX, 55, 150, 30);
		panel.add(tfPort);
		
		lblCurrentPort.setBounds(alignementX, 85, 100, 30);
		lblCurrentPort.setFont(fontLabels);
		panel.add(lblCurrentPort);
		
		JLabel lblServer = new JLabel("Server IP:");
		lblServer.setBounds(alignementX, 120, 100, 30);
		lblServer.setFont(fontLabels);
		panel.add(lblServer);
		
		btnConfirm.setBounds(alignementX+200, 55, 100, 30);
		panel.add(btnConfirm);
		
		
		
		return panel;
	}
	
	private String getIP() {
		try {
			InetAddress localIP = InetAddress.getLocalHost();
			return localIP.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "Failed to resolve IP";
	}
	
	private class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int port = (Integer.parseInt(tfPort.getText()));
			try (Socket portTest = new Socket("localhost", port)) {
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		ServerUI test = new ServerUI();
	}
}
