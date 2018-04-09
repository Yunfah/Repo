package client;


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class ViewerGame extends JPanel {
	private List<JButton> letterButtons = new ArrayList<>();
	private DrawingPanel drawingPanel = new DrawingPanel();
	private ContinueListener continueListener;
	private Controller controller;
	private JButton btnBack = new JButton("<-- BACK");
	private JButton btnSave = new JButton("Save");
	private ButtonGroup rbGroup = new ButtonGroup();
	private JRadioButton rbShowWord = new JRadioButton("Show word on loss");
	private JRadioButton rbHideWord = new JRadioButton("Hide word on loss");
	
	private boolean[] buttonEnabled = new boolean[26];

	public ViewerGame() {
		setPreferredSize(new Dimension(1200, 800));
		JPanel letterButtonPanel = new JPanel(new GridLayout(3, 0, 3, 3));
		letterButtonPanel.setBorder(BorderFactory.createTitledBorder("Letters"));
		ButtonListener buttonListener = new ButtonListener();
		int counter = 0;
		for (char c = 'A'; c <= 'Z'; c++) {
			String text = String.valueOf(c);
			JButton button = new JButton(text);
			button.addActionListener(buttonListener);
			letterButtons.add(button); // add JButton to List<JButton>
			letterButtonPanel.add(button);  // and add to GridLayout-using JPanel
			buttonEnabled[counter] = true;
			counter++;
		}	
		setupTopOptions();
		// JPanel to hold non-letter JButtons
		JPanel specialBtnsPanel = new JPanel(new GridLayout(1, 0, 3, 3));
		specialBtnsPanel.add(new JButton(new ResetAction("Reset", KeyEvent.VK_R)));
		specialBtnsPanel.add(new JButton(new NewWordAction("New Word", KeyEvent.VK_N)));
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

	private void setupTopOptions() {	
		btnBack.setBounds(10, 20, 200, 75);
		btnBack.setBackground(Color.WHITE);
		btnBack.setFont(new Font("SansSerif", Font.BOLD, 30));
		btnBack.setBorderPainted(false);
		btnSave.setBounds(935, 10, 100, 60); 
		btnSave.setEnabled(false);
		btnSave.setFont(new Font("SansSerif", Font.BOLD, 20));
		setupTopListeners();

		rbShowWord.setBounds(1040, 10, 150, 30);
		rbShowWord.setSelected(true);
		rbShowWord.setBackground(Color.PINK);
		rbHideWord.setBounds(1040, 40, 150, 30);
		rbHideWord.setBackground(Color.PINK);

		drawingPanel.add(btnBack);
		drawingPanel.add(btnSave);
		drawingPanel.add(rbShowWord);
		drawingPanel.add(rbHideWord);
		rbGroup.add(rbHideWord);
		rbGroup.add(rbShowWord);
	}

	private void setupTopListeners() {
		BackSaveListener backSaveListener = new BackSaveListener();
		btnBack.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {
				btnBack.setForeground(Color.RED);
			}
			public void mouseExited(MouseEvent arg0) {
				btnBack.setForeground(Color.BLACK);
			}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		});
		btnBack.addActionListener(backSaveListener);

		btnSave.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {
				btnSave.setForeground(Color.GREEN);
			}
			public void mouseExited(MouseEvent arg0) {
				btnSave.setForeground(Color.BLACK);
			}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		});
		btnSave.addActionListener(backSaveListener);
	}

	public void setListener(ContinueListener listener) {
		continueListener = listener;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	private class BackSaveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnBack) {
				reset();
				continueListener.goBack();
			} else if (e.getSource() == btnSave) {
				controller.saveGameProgress();
			}
		}
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Button pressed: " + e.getActionCommand() +"\nWrong: " + drawingPanel.getWrongLetterCount());
			((AbstractButton) e.getSource()).setEnabled(false);
			int indexOfLetter = letterButtons.indexOf(e.getSource());
			buttonEnabled[indexOfLetter] = false;
			btnSave.setEnabled(true);
			controller.checkLetter(e.getActionCommand().charAt(0));			
			
			if (drawingPanel.getWrongLetterCount() == 10) {
				for(JButton btn : letterButtons)
					btn.setEnabled(false);
				btnSave.setEnabled(false);
				if (rbShowWord.isSelected())
					controller.setWordGuessed();
			}
			
			drawingPanel.repaint();
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
	private class NewWordAction extends AbstractAction {
		public NewWordAction(String name, int mnemonic) {
			super(name);
			putValue(MNEMONIC_KEY, mnemonic);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			reset();
			// GÖR DET HÄR
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
	
	public void setTurn(boolean myTurn) {
		if (myTurn == true) {
			for (int i = 0; i < buttonEnabled.length; i++) {
				letterButtons.get(i).setEnabled(buttonEnabled[i]);
			}
		} else if (myTurn == false) {
			for (JButton btn : letterButtons) {
				btn.setEnabled(false);
			}
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

	/**
	 * Returns amount of mistakes the player has made. 
	 * @return Amount of mistakes made. 
	 */
	public int getWrongLetterCount() {
		return drawingPanel.getWrongLetterCount();
	}

	public void setDifficulty(int difficulty) {
		drawingPanel.setWrongLetterCount(difficulty);
	}

	public void setWin(boolean win) {
		for (JButton btn : letterButtons)
			btn.setEnabled(false);
		btnSave.setEnabled(false);
		drawingPanel.setWin(win);	
	}

	public int displayLife () {
		// TODO: display life count in the GUI
		// Method to show how many tries the player have left. Should show in the window. 		
		return 0;
	}

	/**
	 * Resets all progress for the current word. Restarts
	 * the game with the same settings and same word.
	 */
	public void reset() {
		for (JButton btn : letterButtons)
			btn.setEnabled(true);
		btnSave.setEnabled(false);
		int length = drawingPanel.getWord().length;
		char[] resetWord = new char[length];
		for (int i = 0; i < length; i++)
			resetWord[i] = '-';
		drawingPanel.setWrongLetterCount(controller.getDifficulty());
		drawingPanel.setWord(resetWord);
		drawingPanel.setWin(false);
		controller.setEncodedWord(resetWord);
	}
}

class DrawingPanel extends JPanel {
	private int wrongLetterCount = -1;
	private char[] word;
	private String category;
	private boolean win = false;	//Represents if the word has been completely guessed or not

	public DrawingPanel() {
		setLayout(null);
		setBorder(BorderFactory.createTitledBorder("Hang Man"));
		setBackground(Color.WHITE);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		((Graphics2D)g).setStroke(new BasicStroke(3));
		g.setFont(new Font("SansSerif", Font.BOLD, 40));

		g.setColor(Color.BLUE);
		int w = g.getFontMetrics().stringWidth(category);
		g.drawString(category, 600-w/2, 70);

		paintNext(g, wrongLetterCount);	
		drawWordLines(g, word);	
		drawWord(g, word);

		if (win) {
			g.setFont(new Font("SansSerif", Font.BOLD, 50));
			g.setColor(Color.CYAN);
			g.drawString("YOU WIN", 500, 400);
		} 	
	} 

	/**
	 * Paints the hanged man based on the progress made on the 
	 * word to guess.
	 * @param g Graphics object to draw with. 
	 * @param wrongLetterCount Amount of incorrect guesses the player has made. 
	 */
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
	}

	/**
	 * Draws the same amount of lines as letters in the word 
	 * to guess.
	 * @param g Graphics object to draw with.
	 * @param length The amount of letters in the word.
	 */
	public void drawWordLines(Graphics g, char[] word) {
		g.setColor(Color.BLACK);
		int x1 = 550;
		int x2 = x1+30;
		int y = 500;

		for (int i = 0; i < word.length; i++) {
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
	 * Draws the correctly guessed letters (the progress) of the word.
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

	/**
	 * Sets the current progress of the word to guess.
	 * @param word Current progress of the word.
	 */
	public void setWord(char[] word) {
		this.word = word;
		repaint();
	}

	public char[] getWord() {
		return word;
	}

	/**
	 * Sets the name of the current category.
	 * @param category The name of the current category. 
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Sets the wrongLetterCount to a custom number.
	 * Should only be used at the start of a round.
	 * @param difficulty The handicap that the player starts with.
	 */
	public void setWrongLetterCount(int difficulty) {
		if (difficulty > 10) {
			System.out.println("INVALID NUMBER");
		} else {
			wrongLetterCount = difficulty;
			repaint();
		}
	}

	public int getWrongLetterCount() {
		return wrongLetterCount;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public void incrementWrongLetterCount() {
		wrongLetterCount++;
		repaint();
	}
}