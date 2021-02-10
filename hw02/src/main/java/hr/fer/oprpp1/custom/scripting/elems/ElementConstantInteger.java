package hr.fer.oprpp1.custom.scripting.elems;

public class ElementConstantInteger extends Element {

	private int value; 
	
	public ElementConstantInteger(int value) {
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
