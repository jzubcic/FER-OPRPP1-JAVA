package hr.fer.oprpp1.custom.scripting.elems;

public class ElementString extends Element {

	private String value; 
	
	public ElementString(String value) {
		this.value = value; 
	}
	
	/**
	 * Returns <code>value</code>.
	 * 
	 * @return <code>value</code> 
	 */
	public String asText() {
		return value; 
	}
}
