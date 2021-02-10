package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

public class Node {

	private ArrayIndexedCollection children; 
	
	/**
	 * Adds given node into Node's backing collection.
	 * @param child node to be added to collection
	 */
	public void addChildNode(Node child) {
		if (children == null) {
			children = new ArrayIndexedCollection(); 
		}
		
		children.add(child);
	}
	
	/**
	 * Returns the number of nodes in Node's collection
	 * @return size of Node's collection
	 */
	public int numberOfChildren() {
		return children.size(); 
	}
	
	/**
	 * Returns Node at given position from Node's collection
	 * @param index position in collection from which to return Node
	 * @return Node at given position
	 */
	public Node getChild(int index) {
		return (Node) children.get(index);  
	}
	
}
