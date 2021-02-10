package hr.fer.zemris.java.gui.calc;

/**
 * Strategy for applying unary operations.
 */
public interface UnaryOperation {

	/**
	 * Method performs unary operation. 
	 * @param number - number on which unary operation is performed
	 * @return result of unary operation
	 */
	public Double execute(Double number);
}
