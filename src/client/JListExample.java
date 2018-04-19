package client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

public class JListExample extends JList {
	private class Renderer extends JPanel implements ListCellRenderer<Object> {
		public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
			JPanel panel = new JPanel(new BorderLayout());
			panel.add(new JRadioButton(value.toString()));
			return panel;
		}
	}
	
	public static void main(String[] args) {
		DefaultListModel<String> users = new DefaultListModel<String>();
		users.addElement("AB");
		users.addElement("BC");
		users.addElement("HC");
		JListExample list = new JListExample();		
		list.setModel(users);
		
		
		JFrame frame = new JFrame();
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			private int counter = 1;
			@Override
			public void actionPerformed(ActionEvent e) {
				users.addElement("AB "+counter++);
			}			
		});
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int size = users.size();
				users.remove((int)(Math.random()*size));
			}
			
		});
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(btnAdd,BorderLayout.NORTH);
		frame.add(new JScrollPane(list), BorderLayout.CENTER);
		frame.add(btnRemove,BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}
}
