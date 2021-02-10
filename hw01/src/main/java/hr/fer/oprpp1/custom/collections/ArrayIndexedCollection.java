package hr.fer.oprpp1.custom.collections;

public class ArrayIndexedCollection extends Collection {

	private int size; 
	private Object[] elements; 
	
	public int capacity;
	
	/**
	 * Constructor for class ArrayIndexedCollection.
	 * 
	 * @param initialCapacity initial capacity of backing array
	 * @throws IllegalArgumentException if <code>initialCapacity</code> is less than 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		super(); 
		if (initialCapacity < 1) throw new IllegalArgumentException("Initial capacity must be 1 or greater.");
		capacity = initialCapacity;
		size = 0; 
		elements = new Object[capacity]; 
	}
	
	/**
	 * Constructor for class ArrayIndexedCollection. Creates an instance 
	 * with capacity of backing array of 16.
	 */
	public ArrayIndexedCollection() {
		this(16); 
	}
	 
	/**
	 * Constructor for class ArrayIndexedCollection.
	 * 
	 * @param collection collection to be copied into created instance
	 * @param initialCapacity initial capacity of backing array
	 * @throws NullPointerException if collection is <code>null</code>
	 */
	public ArrayIndexedCollection(Collection collection, int initialCapacity) { 
		if (collection == null) throw new NullPointerException();
		if (collection.size() > initialCapacity) initialCapacity = collection.size();
		
		elements = collection.toArray(); 
		capacity = initialCapacity;
		size = collection.size(); 
	}
	
	/**
	 * Constructor for class ArrayIndexedCollection.
	 * 
	 * @param collection collection to be copied into created instance
	 * @throws NullPointerException if collection is <code>null</code>
	 */
	//nisam bio siguran treba li napomenuti bacanje NullPointerExceptiona u 
	//konstruktoru kojemu se delegira posao, pa sam naveo za svaki slucaj
	public ArrayIndexedCollection(Collection collection) {
		this(collection, collection.size());
	}
	
	/**
	 * Method adds given Object into the first 
	 * empty place in the collection.
	 *
	 * @param value - value to be added into collection
	 * @throws NullPointerException if value is null
	 */
	public void add(Object value) {
		if (value == null) throw new NullPointerException();
		for (int i = 0; i < capacity; i++) {
			if (elements[i] == null) {
				elements[i] = value; 
				size++;
				return; 
			}
		}
		
		doubleCapacity(); 
		elements[capacity/2] = value; 
		size++;	 	 		 
	}
	
	/**
	 * Method doubles the capacity of the collection.
	 */
	public void doubleCapacity() {
		Object[] temp = new Object[capacity*2]; 
		for (int i = 0; i < capacity; i++) {
			temp[i] = elements[i]; 
		}		
		elements = temp;
		capacity *= 2;
	}
	
	public boolean contains(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) return true; 
		}
		return false; 
	}
	
	public int size() {
		return size; 
	}
	
	public Object[] toArray() {
		if (isEmpty()) throw new UnsupportedOperationException();
		
		Object[] array = new Object[size]; 
		array = elements;
		return array; 
	}
	
	
	public void forEach(Processor processor) {
		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}
	
	/**
	 * Method returns object stored in backing array at position <code>index</code>.
	 * 
	 * @param index position in array of the object that will be returned
	 * @return Object stored at backing array at given position
	 * @throws IndexOutOfBoundsException if <code>index</code> is not in interval
	 * 0 to <code>size-1</code>
	 */
	public Object get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException(); 
		}
		return elements[index]; 
	}
		
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null; 
		}
		size = 0; 
	}
	
	/**
	 * Inserts given value at the given position in array.
	 * All elements at greater positions will be shifted one place toward the end.
	 * 
	 * @param value value that will be inserted
	 * @param position position at which value will be inserted
	 * @throws IndexOutOfBoundsException if <code>position</code> is less than 0
	 * or greater than <code>size</code>
	 * @throws NullPointerException if value is <code>null</code>
	 */
	public void insert(Object value, int position) {
		if (value == null) throw new NullPointerException("Value must not be null.");
		if (position < 0 || position > size) throw new IndexOutOfBoundsException("Position is invalid.");
		if (size == capacity) doubleCapacity();
		
		for (int i = size; i > position; i--) {
			elements[i] = elements[i-1]; 
		}
		elements[position] = value; 
		size++;
	}
	
	/**
	 * Returns the index of the first occurence of given value in collection
	 * or -1 if value is not found.
	 * 
	 * @param value - value whose index is to be found
	 * @return index at which value is found, otherwise -1
	 */
	public int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) return i; 
		}
		return -1; 
	}
	
	/**
	 * Removes element at specified index from collection.
	 * All elements at greater positions will be shifted one place down.
	 *  
	 * @param index position from which an element will be removed
	 * @throws IndexOutOfBoundsException if <code>index</code> is not in interval
	 * 0 to <code>size-1</code>
	 */
	public void remove(int index) {
		if (index < 0 || index > size - 1) throw new IndexOutOfBoundsException(); 
		
		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i+1]; 
		}
		elements[size - 1] = null; 
		size--;
	}
	
	/**
	 * Method returns true if it successfully removes one occurence of given value
	 * in collection.
	 * 
	 * @param value value to be removed
	 * @return true if it successfully removed one occurence of value, otherwise false
	 */	
	public boolean remove(Object value) {
		int index = indexOf(value); 
		if (index != -1) {
			remove(index); 
			return true; 
		} else {
			return false; 
		}
	}	
}