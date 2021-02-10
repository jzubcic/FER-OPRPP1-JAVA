package hr.fer.zemris.java.gui.layouts;

/**
 *  Exception to be thrown by CalcLayout class, for instance when
 *  trying to add two components on the same position. 
 */
public class CalcLayoutException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * @param message - message which describes the error
	 */
	public CalcLayoutException(String message) {
		super(message); 
	}
}
