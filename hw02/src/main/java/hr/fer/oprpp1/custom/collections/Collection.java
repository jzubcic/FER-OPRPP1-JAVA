package hr.fer.oprpp1.custom.collections;

public interface Collection {
	
	/**
	 * Returns <code>true</code> if collection contains no objects.
	 * 
	 * @return true if collection is empty, otherwise false
	 */
	public default boolean isEmpty() {
		if (size() == 0) return true; 
		return false; 
	}
	
	/**
	 * Method returns the current size of the collection.
	 * 
	 * @return size of this collection
	 */
	public int size();
	
	public void add(Object value); 
	
	/**
	 * Method returns true if the collection contains
	 * the given value, otherwise it returns false.
	 * 
	 * @param value - value whose presence in this
	 * 		  collection is to be tested
	 * @return true if this collection contains the value
	 */
	public boolean contains(Object value);
	
	public boolean remove(Object value);
	
	/**
	 * Method returns new array with its size and contents equal
	 * to this collection.
	 * 
	 * @throws UnsupportedOperationException if collection is empty
	 * @return array with size and content of this collection
	 */
	public Object[] toArray();
	
	/** 
	 * Method calls <code>processor.process()</code> for each element 
	 * of this collection. 
	 * 
	 * @param processor the processor whose <code>process()</code> method
	 * will be called
	 */
	public default void forEach(Processor processor) {
		ElementsGetter getter = this.createElementsGetter();
		while (getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}
	
	public default void addAll(Collection other) {
		class LocalProcessor implements Processor {
			
			public LocalProcessor() {
			}
			
			public void process(Object value) {
				add(value);  
			}
			
		}
		LocalProcessor localProcessor = new LocalProcessor();
		other.forEach(localProcessor); 
	}
	
	/**
	 * Method removes all elements from the collection.
	 */
	public void clear();
	
	public ElementsGetter createElementsGetter(); 
	
	public default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter getter = col.createElementsGetter(); 
		
		while (getter.hasNextElement()) {
			Object temp = getter.getNextElement(); 
			if (tester.test(temp)) {
				this.add(temp);
			}
		}
	}
}
