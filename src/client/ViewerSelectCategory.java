package client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;

/**
 * This class contains the frame for selecting the category of word
 * being guessed in the game
 * @author Jakob Kennerberg
 *
 */
public class ViewerSelectCategory extends JPanel {
	private JButton btnRandom = new JButton("Random");
	private JButton btnCities = new JButton("Cities"); //change button names when categories have been identified.
	private JButton btnAnimals = new JButton("Animals");
	private JButton btnBrands = new JButton("Brands");
	private JButton btnBack = new JButton("<-- BACK");
	private JLabel lblCategory = new JLabel("CATEGORY    ", SwingConstants.CENTER);
	private ButtonListener listener = new ButtonListener();
	private BackListener bListener = new BackListener();
	private ContinueListener continueListener;
	private Controller controller;

	/**
	 * Constructor
	 */
	public ViewerSelectCategory() {
		setPreferredSize(new Dimension(1200,800));
		setLayout(new BorderLayout());

		add(pnlNorth(), BorderLayout.NORTH);
		add(pnlButtons(), BorderLayout.CENTER);

		btnRandom.addActionListener(listener);
		btnCities.addActionListener(listener);
		btnAnimals.addActionListener(listener);
		btnBrands.addActionListener(listener);
		btnBack.addActionListener(listener);
		btnBack.addMouseListener(bListener);
		btnRandom.addMouseListener(bListener);
		btnCities.addMouseListener(bListener);
		btnAnimals.addMouseListener(bListener);
		btnBrands.addMouseListener(bListener);
	}

	/**
	 * Method which creates the panel containing all the category buttons
	 * @return A JPanel with category-buttons
	 */
	private JPanel pnlButtons() {
		JPanel panel = new JPanel(null);
		Font btnFont = new Font("SansSerif", Font.BOLD, 30);

		btnRandom.setBounds(450, 50, 300, 100);
		btnCities.setBounds(450, 200, 300, 100);
		btnAnimals.setBounds(450, 350, 300, 100);
		btnBrands.setBounds(450, 500, 300, 100);
		btnRandom.setFont(btnFont);
		btnCities.setFont(btnFont);
		btnAnimals.setFont(btnFont);
		btnBrands.setFont(btnFont);
		btnRandom.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black, Color.black));
		btnRandom.setBackground(Color.white);
		btnRandom.setOpaque(true);
		btnCities.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black, Color.black));
		btnCities.setBackground(Color.white);
		btnCities.setOpaque(true);
		btnAnimals.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black, Color.black));
		btnAnimals.setBackground(Color.white);
		btnAnimals.setOpaque(true);
		btnBrands.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black, Color.black));
		btnBrands.setBackground(Color.white);
		btnBrands.setOpaque(true);

		panel.setBackground(Color.DARK_GRAY);
		panel.add(btnRandom);
		panel.add(btnCities);
		panel.add(btnAnimals);
		panel.add(btnBrands);

		return panel;
	}

	/**
	 * Method which creates the upper panel, containing the title and 
	 * back button
	 * @return A JPanel With a header and back-button
	 */
	private JPanel pnlNorth() {
		JPanel panel = new JPanel();
		Font btnFont = new Font("SansSerif", Font.BOLD, 30);

		panel.setBackground(Color.DARK_GRAY);
		panel.setLayout(new BorderLayout());
		panel.add(lblCategory, BorderLayout.CENTER);
		panel.add(btnBack, BorderLayout.WEST);

		btnBack.setFont(btnFont);
		btnBack.setPreferredSize(new Dimension(200,100));
		btnBack.setForeground(Color.WHITE);
		btnBack.setContentAreaFilled(false);
		btnBack.setBorderPainted(false);

		lblCategory.setFont(new Font("SansSerif", Font.PLAIN, 125));
		lblCategory.setForeground(Color.WHITE);

		return panel;
	}

	/**
	 * Method which sets the listener(interface) to the frame
	 * @param listener The continueListener to control the flow from/to this frame
	 */
	public void setListener(ContinueListener listener) {
		continueListener = listener;
	}

	/**
	 * Method which sets the controller to the frame
	 * @param controller The controller this frame will use
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * An inner class which listens to the input made by clicking on the buttons
	 * and performs actions according to this.
	 * @author Jakob Kennerberg
	 *
	 */
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnRandom) {
				continueListener.nextPanel();
				controller.setCategoryWord("files/Random.txt", "RANDOM");
				
			} else if (e.getSource() == btnCities) {
				continueListener.nextPanel();
				controller.setCategoryWord("files/Cities.txt", "CITIES");
				
			} else if (e.getSource() == btnAnimals) {
				continueListener.nextPanel();
				controller.setCategoryWord("files/Animals.txt", "ANIMALS");
				
			} else if (e.getSource() == btnBrands) {
				continueListener.nextPanel();
				controller.setCategoryWord("files/Brands.txt", "BRANDS");
				
			} else if (e.getSource() == btnBack) {
				continueListener.goBack();
			}		
		}
	}

	/**
	 * An inner class which listens to the input made by hovering over buttons
	 * and stopping to do this.
	 * @author Jakob Kennerberg
	 *
	 */
	private class BackListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {
			if(e.getSource()==btnBack) {
				btnBack.setForeground(Color.RED);
			}
			if(e.getSource()==btnRandom) {
				btnRandom.setForeground(Color.RED);
			}
			if(e.getSource()==btnAnimals) {
				btnAnimals.setForeground(Color.RED);
			}
			if(e.getSource()==btnBrands) {
				btnBrands.setForeground(Color.RED);
			}
			if(e.getSource()==btnCities) {
				btnCities.setForeground(Color.RED);
			}
		}
		public void mouseExited(MouseEvent e) {
			if(e.getSource()==btnBack) {
				btnBack.setForeground(Color.WHITE);
			}
			if(e.getSource()==btnRandom) {
				btnRandom.setForeground(Color.BLACK);
			}
			if(e.getSource()==btnCities) {
				btnCities.setForeground(Color.BLACK);
			}
			if(e.getSource()==btnAnimals) {
				btnAnimals.setForeground(Color.BLACK);
			}
			if(e.getSource()==btnBrands) {
				btnBrands.setForeground(Color.BLACK);
			}
		}
	
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
}
