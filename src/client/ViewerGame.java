package client;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

public class ViewerGame extends JPanel {

	// using a List of JButtons to hold my collection
	private List<JButton> letterButtons = new ArrayList<>();
	private DrawingPanel drawingPanel = new DrawingPanel();
	private ContinueListener continueListener;
	private Controller controller;

	public ViewerGame() {
		setPreferredSize(new Dimension(1200, 800));
		JPanel letterButtonPanel = new JPanel(new GridLayout(3, 0, 3, 3));
		letterButtonPanel.setBorder(BorderFactory.createTitledBorder("Letters"));
		ButtonListener buttonListener = new ButtonListener();
		for (char c = 'A'; c <= 'Z'; c++) {
			String text = String.valueOf(c);
			JButton button = new JButton(text);
			button.addActionListener(buttonListener);
			letterButtons.add(button); // add JButton to List<JButton>
			letterButtonPanel.add(button);  // and add to GridLayout-using JPanel
		}

		// JPanel to hold non-letter JButtons
		JPanel specialBtnsPanel = new JPanel(new GridLayout(1, 0, 3, 3));
		specialBtnsPanel.add(new JButton(new ResetAction("Reset", KeyEvent.VK_R)));
		specialBtnsPanel.add(new JButton(new ExitAction("Exit", KeyEvent.VK_X)));

		// JPanel to hold non-drawing JPanels. It uses BoxLayout
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.PAGE_AXIS));
		bottomPanel.add(letterButtonPanel);
		bottomPanel.add(specialBtnsPanel);
		
		// set layout and border of main JPanel and add other JPanels to it
		setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		setLayout(new BorderLayout(3, 3));
		add(drawingPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		
	}
	


	public void setListener(ContinueListener listener) {
		continueListener = listener;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Button pressed: " + e.getActionCommand());
			((AbstractButton) e.getSource()).setEnabled(false);
		}
	}

	private class ResetAction extends AbstractAction {
		public ResetAction(String name, int mnemonic) {
			super(name);
			putValue(MNEMONIC_KEY, mnemonic);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (JButton button : letterButtons) {
				button.setEnabled(true);
			}
		}
	}

	private class ExitAction extends AbstractAction {
		public ExitAction(String name, int mnemonic) {
			super(name);
			putValue(MNEMONIC_KEY, mnemonic);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Component component = (Component) e.getSource();
			Window win = SwingUtilities.getWindowAncestor(component);
			win.dispose();
		}
	}

	private static void createAndShowGui() {
		ViewerGame mainPanel = new ViewerGame();

		JFrame frame = new JFrame("Hangman");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}
		});
	}

	public void setWord(String choosenWord, int length) {
		// TODO Auto-generated method stub
		// The method should get the word and the length of the word,
		// and display the word together with lines to show how long it is. 
	}
	
	public int displayLife () {
		// TODO: display life count in the GUI
		// Method to show how many tries the player have left. Should show in the window. 
		
		return 0;
	}
}

class DrawingPanel extends JPanel {
	private static final int PREF_W = 1200;
	private static final int PREF_H = 800;
	private int wrongLetterCount = 0;
	private String message = "BLABLABLA";

	public DrawingPanel() {
		setBorder(BorderFactory.createTitledBorder("Hang Man"));
		setBackground(Color.WHITE);
	}

	@Override
	public Dimension getPreferredSize() {
		if (isPreferredSizeSet()) {
			return super.getPreferredSize();
		}
		return new Dimension(PREF_W, PREF_H);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		((Graphics2D)g).setStroke(new BasicStroke(3));
		if (message != null) {
			g.setColor(Color.RED);
			g.drawString(message, 30, 40);

		}
	}   

	public void incrementWrongLetterCount() {
		wrongLetterCount++;
		repaint();
	}

	public void reset() {
		wrongLetterCount = 0;
		repaint();
	}
}