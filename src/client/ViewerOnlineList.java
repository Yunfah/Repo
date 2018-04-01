package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;

public class ViewerOnlineList extends JPanel {
	private ContinueListener continueListener;
	private Controller controller;
	private JLabel lblHeader = new JLabel("Choose a player     ", SwingConstants.CENTER);
	private JLabel lblOnline = new JLabel("Online");
	private JLabel lblSettings;	//Should show what gamemode the player has chosen.
	private JButton btnBack = new JButton("<-- Back");
	private JButton btnInvite = new JButton("Invite");
	private JPanel pnlOnlineList = new JPanel();


	public ViewerOnlineList() {
		setPreferredSize(new Dimension(1200, 800));
		setLayout(new BorderLayout());
		add(titlePanel(), BorderLayout.NORTH);
		add(mainPanel(), BorderLayout.CENTER);
	}

	private JPanel titlePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
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

	private JPanel mainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		panel.setBackground(Color.DARK_GRAY);
		Font btnFont = new Font("SansSerif", Font.BOLD, 30);

		JPanel onlineList = new JPanel();
		onlineList.setBounds(100, 50, 400, 500);
		
//		JScrollPane scroll = new JScrollPane(onlineList);
//		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		scroll.setBounds(100, 50, 400, 500);

		btnInvite.setBounds(800, 400, 200, 100);
		btnInvite.setFont(btnFont);

		panel.add(btnInvite);
		panel.add(onlineList);
		return panel;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void setListener(ContinueListener listener) {
		continueListener = listener;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Test of online list");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new ViewerOnlineList());
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

//		public static void main(String... args) {
//	        JFrame frame = new JFrame();
//	        JPanel panel = new JPanel();
//	        for (int i = 0; i < 10; i++) {
//	            panel.add(new JButton("Hello-" + i));
//	        }
//	        JScrollPane scrollPane = new JScrollPane(panel);
//	        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//	        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
//	        scrollPane.setBounds(50, 50, 400, 80);
//	        JPanel contentPane = new JPanel(null);
//	        contentPane.setPreferredSize(new Dimension(500, 400));
//	        contentPane.add(scrollPane);
//	        frame.setContentPane(contentPane);
//	        frame.pack();
//	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//	        frame.setVisible(true);
//	    }
}
