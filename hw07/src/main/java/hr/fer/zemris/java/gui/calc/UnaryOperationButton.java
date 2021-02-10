package hr.fer.zemris.java.gui.calc;

import javax.swing.JButton;

/**
 * Class which represents button used for performing unary operations in calculator.
 */
public class UnaryOperationButton extends JButton {

	private static final long serialVersionUID = 1L;
	private String text1;
	private String text2; 
 
	/**
	 * Constructor. 
	 * 
	 * @param text1 - default name of button
	 * @param text2 - inverted name of button
	 * @param model - CalcModel which will be used for performing operations
	 * @param operation - unary operation which will be performed on click
	 */
	public UnaryOperationButton(String text1, String text2, CalcModelImpl model, UnaryOperation operation) {
		super(); 
		this.text1 = text1;
		this.text2 = text2; 
 
		setFont(getFont().deriveFont(20f));
		setText(CalculatorFrame.invertedBox.isSelected() ? text2 : text1); 
		if (text1.equals("log") || text1.equals("ln")) {
			addActionListener(l -> model.setValue(!CalculatorFrame.invertedBox.isSelected() ?
					operation.execute(model.getValue()) : Math.pow(text1.equals("log") ? 10 : Math.E, model.getValue())));
		} else {
			addActionListener(a -> model.setValue(!CalculatorFrame.invertedBox.isSelected() ? 
					operation.execute(model.getValue()) : 1 / operation.execute(model.getValue())));
		}
	}	
	
	/**
	 * Method used for toggling between two names of the button.
	 * 
	 */
	public void rename() {
		setText(CalculatorFrame.invertedBox.isSelected() ? text2 : text1);
	}
}
