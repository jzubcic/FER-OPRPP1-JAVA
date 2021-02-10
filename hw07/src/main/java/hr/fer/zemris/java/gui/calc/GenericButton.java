package hr.fer.zemris.java.gui.calc;

import javax.swing.JButton;

/**
 *  Class which represents button used for generic functions, 
 *  such as adding a decimal point, or toggling sign of number in calculator.
 */
public class GenericButton extends JButton {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor. 
	 * 
	 * @param name - name of the button
	 * @param operation - operation which will be performed on click
	 */
	public GenericButton(String name, GenericOperation operation) {
		super(); 
		setText(name); 
		addActionListener(a -> operation.execute());
		setFont(getFont().deriveFont(20f));
	}
}
