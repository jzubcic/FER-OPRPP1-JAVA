package hr.fer.oprpp1.custom.collections;

public class ObjectStack<T> {

	private ArrayIndexedCollection<T> array; 
	
	/**
	 * Constructor for ObjectStack. 
	 */
	public ObjectStack() {
		array = new ArrayIndexedCollection<T>(); 
	}
	
	/**
	 * Returns true if stack is empty.
	 * @return true if stack is empty, false otherwise
	 */
	public boolean isEmpty() {
		return array.isEmpty(); 
	}
	
	/**
	 * Returns size of stack.
	 * @return size of stack
	 */
	public int size() {
		return array.size(); 
	}
	
	/**
	 * Pushes given value on top of stack. 
	 * @param value to be pushed on top of stack
	 * @throws IllegalArgumentException if value is <code>null</code>
	 */
	public void push(T value) {
		if (value == null) throw new IllegalArgumentException("Value must not be null."); 
		
		array.add(value);
	}
	
	/**
	 * Removes and returns object on top of stack.
	 * @return object from top of stack
	 * @throws EmptyStackException if stack is empty
	 */
	public Object pop() {
		if (size() == 0) throw new EmptyStackException("Stack is empty."); 
		
		Object obj = array.get(size() - 1);
		array.remove(size() - 1);
		return obj; 
	}
	
	/**
	 * Returns object on top of stack without removing it.
	 * @return object from top of stack
	 * @throws EmptyStackException if stack is empty
	 */
	public T peek() {
		if (size() == 0) throw new EmptyStackException("Stack is empty.");
		
		T obj = array.get(size() - 1);
		return obj; 
	}
	
	/**
	 * Removes all objects from stack. 
	 */
	public void clear() {
		array.clear(); 
	}
}
