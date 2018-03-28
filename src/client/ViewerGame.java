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
			System.out.println("Button pressed: " + e.getActionCommand() +"\nWrong: " + drawingPanel.getWrong());
			((AbstractButton) e.getSource()).setEnabled(false);

			controller.checkLetter(e.getActionCommand().charAt(0));

//			drawingPanel.repaint();
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
			reset();
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

	/**
	 * Sets the current progress of the word to guess.
	 * @param encodedWord A word encoded as '-' for each unguessed letter.
	 */
	public void setWord(char[] encodedWord) {
		drawingPanel.setWord(encodedWord);
	}

	/**
	 * Puts the name of the category at the top of the window
	 * @param category The name of the chosen category
	 */
	public void setCategory(String category) {
		drawingPanel.setCategory(category);
	}

	public void incrementWrongLetterCount() {
		drawingPanel.incrementWrongLetterCount();
	}

	public void setDifficulty(int difficulty) {
		drawingPanel.setWrongLetterCount(difficulty);
	}

	public void setWin() {
		
	}
	public int displayLife () {
		// TODO: display life count in the GUI
		// Method to show how many tries the player have left. Should show in the window. 		
		return 0;
	}
	
	public void reset() {
		int length = drawingPanel.getWord().length;
		char[] newWord = new char[length];
		for (int i = 0; i < length; i++)
			newWord[i] = '-';
		drawingPanel.setWrongLetterCount(controller.getDifficulty());
		drawingPanel.setWord(newWord);
		controller.setEncodedWord(newWord);
	}
}

class DrawingPanel extends JPanel {
	private static final int PREF_W = 1200;
	private static final int PREF_H = 500;
	private int wrongLetterCount = -1;
	private char[] word;
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
		g.setFont(new Font("SansSerif", Font.BOLD, 40));

		g.setColor(Color.BLUE);
		int w = g.getFontMetrics().stringWidth(category);
		g.drawString(category, 600-w/2, 50);

		paintNext(g, wrongLetterCount);	//Paints the hanged man based on guessed progress
		drawWordLines(g, word, word.length);	//Paints the same amount of lines as letters in the word
		drawWord(g, word);				//Paints the progress of the word

		//rita h�r och kalla repaint f�r att anropa denna
	} 

	public void paintNext(Graphics g, int wrongLetterCount) {
		g.setColor(Color.BLACK);
		switch (wrongLetterCount) {
		case 0 : g.drawArc(100, 450, 200, 200, 0, 180); //rita halvcirkel (kulle)
		break;
		case 1 : {
			g.drawArc(100, 450, 200, 200, 0, 180);
			g.drawLine(200, 450, 200, 100);
		} //rita streck mitt upp fr�n halvcirkeln
		break;
		case 2 : {
			g.drawLine(200, 100, 400, 100);
			g.drawArc(100, 450, 200, 200, 0, 180);
			g.drawLine(200, 450, 200, 100); //rita streck till h�ger ut fr�n strecket i case 1.
		}
		break;
		case 3 : {
			g.drawLine(200, 150, 250, 100);
			g.drawLine(200, 100, 400, 100);
			g.drawArc(100, 450, 200, 200, 0, 180);
			g.drawLine(200, 450, 200, 100);
		}//rita snett streck mellan strecken fr�n case 1 & 2.
		break;
		case 4 : {
			g.drawLine(400, 100, 400, 150);
			g.drawLine(200, 150, 250, 100);
			g.drawLine(200, 100, 400, 100);
			g.drawArc(100, 450, 200, 200, 0, 180);
			g.drawLine(200, 450, 200, 100);//rita litet streck ner fr�n strecket i case 2.
		}
		break;
		case 5 : {
			g.drawOval(375, 150, 50, 50);
			g.drawLine(400, 100, 400, 150);
			g.drawLine(200, 150, 250, 100);
			g.drawLine(200, 100, 400, 100);
			g.drawArc(100, 450, 200, 200, 0, 180);
			g.drawLine(200, 450, 200, 100);//rita gubbens huvud.
		}
		break;
		case 6 : {
			g.drawLine(400, 200, 400, 330);
			g.drawOval(375, 150, 50, 50);
			g.drawLine(400, 100, 400, 150);
			g.drawLine(200, 150, 250, 100);
			g.drawLine(200, 100, 400, 100);
			g.drawArc(100, 450, 200, 200, 0, 180);
			g.drawLine(200, 450, 200, 100);// rita gubbens kropp.
		}
		break; 
		case 7 : {
			g.drawLine(400, 230, 360, 300);
			g.drawLine(400, 200, 400, 330);
			g.drawOval(375, 150, 50, 50);
			g.drawLine(400, 100, 400, 150);
			g.drawLine(200, 150, 250, 100);
			g.drawLine(200, 100, 400, 100);
			g.drawArc(100, 450, 200, 200, 0, 180);
			g.drawLine(200, 450, 200, 100);// rita v�nster arm.
		}
		break;
		case 8 : {
			g.drawLine(400, 230, 440, 300);
			g.drawLine(400, 230, 360, 300);
			g.drawLine(400, 200, 400, 330);
			g.drawOval(375, 150, 50, 50);
			g.drawLine(400, 100, 400, 150);
			g.drawLine(200, 150, 250, 100);
			g.drawLine(200, 100, 400, 100);
			g.drawArc(100, 450, 200, 200, 0, 180);
			g.drawLine(200, 450, 200, 100);//rita h�ger arm.
		}
		break;
		case 9 : {
			g.drawLine(400, 330, 360, 390);
			g.drawLine(400, 230, 440, 300);
			g.drawLine(400, 230, 360, 300);
			g.drawLine(400, 200, 400, 330);
			g.drawOval(375, 150, 50, 50);
			g.drawLine(400, 100, 400, 150);
			g.drawLine(200, 150, 250, 100);
			g.drawLine(200, 100, 400, 100);
			g.drawArc(100, 450, 200, 200, 0, 180);
			g.drawLine(200, 450, 200, 100);//rita v�nster ben.
		}
		break;
		case 10 : {
			g.drawLine(400, 330, 440, 390);
			g.drawLine(400, 330, 360, 390);
			g.drawLine(400, 230, 440, 300);
			g.drawLine(400, 230, 360, 300);
			g.drawLine(400, 200, 400, 330);
			g.drawOval(375, 150, 50, 50);
			g.drawLine(400, 100, 400, 150);
			g.drawLine(200, 150, 250, 100);
			g.drawLine(200, 100, 400, 100);
			g.drawArc(100, 450, 200, 200, 0, 180);
			g.drawLine(200, 450, 200, 100);//rita h�ger arm.
			
			g.setFont(new Font("SansSerif", Font.BOLD, 80));
			g.setColor(Color.RED);
			g.drawString("Bull läge", 600, 300);
		}
		break;
		}
		if (wrongLetterCount > 10) { //Detta ska inte kunna hända - knapparna ska dimmas vid förlust
			
			g.setFont(new Font("SansSerif", Font.BOLD, 80));
			g.setColor(Color.RED);
			g.drawString("Bull läge", 400, 300);
		}
	}

	/**
	 * Draws the same amount of lines as letters in the word 
	 * to guess.
	 * @param g Graphics object to draw with.
	 * @param length The amount of letters in the word.
	 */
	public void drawWordLines(Graphics g, char[] word, int length) {
		g.setColor(Color.BLACK);
		int x1 = 550;
		int x2 = x1+30;
		int y = 500;
		
		for (int i = 0; i < length; i++) {
			if (word[i] == ' ') {
				g.setColor(Color.WHITE);
				g.drawLine(x1, y, x2, y);
				x1 += 45;
				x2 += 45;
				g.setColor(Color.BLACK);
			} else {
				g.drawLine(x1, y, x2, y);
				x1 += 45;
				x2 += 45;
			}
		}
	}

	/**
	 * Draws the correctly guessed letters of the word.
	 * @param g
	 * @param word
	 */
	public void drawWord(Graphics g, char[] word) {
		g.setFont(new Font("SansSerif", Font.BOLD, 30));
		int x = 555;
		int y = 490;
		for (int i = 0; i < word.length; i++) {
			if (word[i] == '-') {
				g.setColor(Color.WHITE);
				String letter = String.valueOf(word[i]);
				g.drawString(letter, x, y);
			} else if (word[i] == ' ') {
				g.setColor(Color.WHITE);
				String space = String.valueOf(word[i]);
				g.drawString(space, x, y);
			} else {
				g.setColor(Color.BLACK);
				String letter = String.valueOf(word[i]);
				g.drawString(letter, x, y);
			}
			x += 45;
		}
	}

	public void setWord(char[] word) {
		this.word = word;
		repaint();
	}
	
	public char[] getWord() {
		return word;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Should only be used at the start of a round.
	 * @param difficulty The handicap that the player starts with.
	 */
	public void setWrongLetterCount(int difficulty) {
		wrongLetterCount = difficulty;
		repaint();
	}
	
	public int getWrong() {
		return wrongLetterCount;
	}

	public void incrementWrongLetterCount() {
		wrongLetterCount++;
		repaint();
	}
}