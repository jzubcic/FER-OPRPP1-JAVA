package hr.fer.oprpp1.custom.scripting.elems;

public class ElementVariable extends Element {

	private String name; 
	
	public ElementVariable (String name) {
		this.name = name; 
	}
	
	/**
	 * Returns <code>name</code>.
	 * 
	 * @return <code>name</code> 
	 */
	public String asText() {
		return name; 
	}
}
