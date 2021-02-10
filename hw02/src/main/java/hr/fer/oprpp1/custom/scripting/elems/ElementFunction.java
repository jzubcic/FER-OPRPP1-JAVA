package hr.fer.oprpp1.custom.scripting.elems;

public class ElementFunction extends Element {

	private String name; 
	
	public ElementFunction(String name) {
		this.name = name; 
	}
	
	/**
	 * Returns <code>name</code> as String.
	 * 
	 * @return <code>name</code> as String
	 */
	public String asText() {
		return name; 
	}
}
