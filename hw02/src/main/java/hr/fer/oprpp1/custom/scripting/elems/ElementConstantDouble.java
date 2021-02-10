package hr.fer.oprpp1.custom.scripting.elems;

public class ElementConstantDouble extends Element {

	private double value; 
	
	public ElementConstantDouble(double value) {
		this.value = value; 
	}
	
	/**
	 * Returns <code>value</code> as String.
	 * 
	 * @return <code>value</code> as String
	 */
	public String asText() {
		return String.valueOf(value); 
	}
}
