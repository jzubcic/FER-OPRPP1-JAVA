package hr.fer.zemris.java.gui.calc;

/**
 *  Strategy for applying binary operations.
 */
public interface BinaryOperation {

	/**
	 * Method performs binary operation.
	 * @param operand1 - first operand 
	 * @param operand2 - second operand
	 * @return result of binary operation
	 */
	public Double calculate(Double operand1, Double operand2); 
	
}
