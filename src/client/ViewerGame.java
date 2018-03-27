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

		//		Graphics g = drawingPanel.getGraphics();
		//		drawingPanel.paintNext(g, 0);
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
			controller.checkLetter(e.getActionCommand().charAt(0));
			drawingPanel.repaint();
			drawingPanel.incrementWrongLetterCount();
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

//	private static void createAndShowGui() {
//		ViewerGame mainPanel = new ViewerGame();
//
//		JFrame frame = new JFrame("Hangman");
//		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		frame.getContentPane().add(mainPanel);
//		frame.pack();
//		frame.setVisible(true);
//		frame.setLocationRelativeTo(null);
//	}
//
//	public static void main(String[] args) {
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				createAndShowGui();
//			}
//		});
//	}

	public void setWord(String chosenWord, int length) {
		drawingPanel.setWord(chosenWord);
	}
	
	public void setCategory(String category) {
		drawingPanel.setCategory(category);
	}

	public int displayLife () {
		// TODO: display life count in the GUI
		// Method to show how many tries the player have left. Should show in the window. 
		
		return 0;
	}
}

class DrawingPanel extends JPanel {
	private static final int PREF_W = 1200;
	private static final int PREF_H = 500;
	private int wrongLetterCount = 0;
	private String word;
	private String category;

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
		g.setFont(new Font("SansSerif", Font.BOLD, 30));
		
		if (word != null) {
			g.setColor(Color.RED);
			int width = g.getFontMetrics().stringWidth(word);
			g.drawString(word, 600-width/2, 300);
		}
		
		g.setColor(Color.BLUE);
		int w = g.getFontMetrics().stringWidth(category);
		g.drawString(category, 600-w/2, 50);
		paintNext(g, wrongLetterCount);	
		drawWordLines(g, word.length());
		
		//rita h�r och kalla repaint f�r att anropa denna
	} 

	public void paintNext(Graphics g, int wrongLetterCount) {
		g.setColor(Color.BLACK);
		switch (wrongLetterCount) {
		case 0 : g.drawArc(100, 450, 200, 200, 0, 180); //rita halvcirkel (kulle)
		break;
		case 1 : g.drawLine(200, 450, 200, 100); //rita streck mitt upp fr�n halvcirkeln
		break;
		case 2 : g.drawLine(200, 100, 400, 100); //rita streck till h�ger ut fr�n strecket i case 1.
		break;
		case 3 : g.drawLine(200, 150, 250, 100); //rita snett streck mellan strecken fr�n case 1 & 2.
		break;
		case 4 : g.drawLine(400, 100, 400, 150); //rita litet streck ner fr�n strecket i case 2.
		break;
		case 5 : g.drawOval(375, 150, 50, 50); //rita gubbens huvud.
		break;
		case 6 : g.drawLine(400, 200, 400, 330); // rita gubbens kropp.
		break; 
		case 7 : g.drawLine(400, 230, 360, 300); // rita v�nster arm.
		break;
		case 8 : g.drawLine(400, 230, 440, 300); //rita h�ger arm.
		break;
		case 9 : g.drawLine(400, 330, 360, 390); //rita v�nster ben.
		break;
		case 10 : g.drawLine(400, 330, 440, 390); //rita h�ger arm.
		break;
		}
		
	}
	
	public void drawWordLines(Graphics g, int length) {
		g.setColor(Color.BLACK);
		int x1 = 550;
		int x2 = 550+30;
		int y = 500;
		for (int i = 0; i < length; i++) {
			g.drawLine(x1, y, x2, y);
			x1 += 45;
			x2 += 45;
		}
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	public void setCategory(String category) {
		this.category = category;
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