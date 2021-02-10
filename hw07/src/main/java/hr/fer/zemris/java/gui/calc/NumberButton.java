package hr.fer.zemris.java.gui.calc;

import javax.swing.JButton;

/**
 * Class which represents buttons for inputing numbers in calculator.
 */
public class NumberButton extends JButton {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * @param number - name of button and value it will be inputting to calculator on click
	 * @param model - model of calculator to which button will be inputting given number
	 */
	public NumberButton(int number, CalcModelImpl model) {
		super(); 
		addActionListener(a -> model.insertDigit(number));
		setFont(getFont().deriveFont(30f));
		setText(String.valueOf(number)); 
	}
	
	
}
