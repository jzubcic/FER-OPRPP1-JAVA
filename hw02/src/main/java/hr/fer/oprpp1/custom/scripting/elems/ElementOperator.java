package hr.fer.oprpp1.custom.scripting.elems;

public class ElementOperator extends Element {
	
	private String symbol; 
	
	public ElementOperator(String symbol) {
		this.symbol = symbol; 
	}
	
	/**
	 * Returns <code>symbol</code> as String.
	 * 
	 * @return <code>symbol</code> as String
	 */
	public String asText() {
		return symbol; 
	}
}
