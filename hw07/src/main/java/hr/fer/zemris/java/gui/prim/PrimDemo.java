package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 *  Demo class which extends JFrame.
 */
public class PrimDemo extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 *  Constructor.
	 */
	public PrimDemo() {
		super(); 
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Prim"); 
		setLocation(20,20); 
		setSize(500, 500); 
		initGUI(); 
	}
	
	/**
	 *  Method which initializes GUI. 
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel primList = new PrimListModel();
		
		JList<Integer> list1 = new JList<>(primList); 
		JList<Integer> list2 = new JList<>(primList); 
		list1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		list2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JPanel p = new JPanel(new GridLayout(1, 0));
		p.add(new JScrollPane(list1)); 
		p.add(new JScrollPane(list2));
		
		cp.add(p, BorderLayout.CENTER); 
		
		JButton nextButton = new JButton("Next"); 
		cp.add(nextButton, BorderLayout.PAGE_END);
		
		nextButton.addActionListener(l ->{
			primList.next(); 
		});
		
		
	}

	/**
	 * Main method.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}
}
