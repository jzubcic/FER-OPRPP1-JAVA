package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

/**
 * Class which represents button used for performing unary operations in calculator.
 */
public class BinaryOperationButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor. 
	 * 
	 * @param text1 - name of button
	 * @param model - CalcModel which will be used for performing operations
	 * @param operation - binary operation which will be performed on click
	 */
	public BinaryOperationButton(String text, CalcModelImpl model, DoubleBinaryOperator operation) {
		super(); 
		setText(text); 
		setFont(getFont().deriveFont(20f));
		addActionListener(a -> {
			if (model.isActiveOperandSet()) {	
				double tempValue = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
				model.setActiveOperand(tempValue);
				model.freezeValue(String.valueOf(tempValue));
			} else {
				model.setActiveOperand(model.getValue());
				model.freezeValue(String.valueOf(model.getValue()));
			}
			model.setPendingBinaryOperation(operation);			
			model.clear();
		});
	}
}
