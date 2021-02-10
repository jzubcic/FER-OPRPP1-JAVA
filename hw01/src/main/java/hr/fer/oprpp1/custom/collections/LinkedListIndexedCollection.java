package hr.fer.oprpp1.custom.collections;

public class LinkedListIndexedCollection extends Collection {

	private static class ListNode {
		Object value; 
		ListNode previous; 
		ListNode next; 
		
		/**
		 * Constructor for ListNode which is used in LinkedListIndexedCollection
		 * @param value value of node
		 * @param previous previous node
		 * @param next next node
		 */
		private ListNode(Object value, ListNode previous, ListNode next) {
			this.value = value; 
			this.previous = previous; 
			this.next = next; 
		}		
	}
	
	private int size; 
	private ListNode first; 
	private ListNode last; 
	
	/**
	 * Constructor for LinkedListIndexedCollection which copies collection
	 * into itself.
	 * @param collection to be copied into instance
	 */
	public LinkedListIndexedCollection(Collection collection) { 		 
		size = 0;  
		this.addAll(collection); 		 
	}
	
	/**
	 * Constructor for LinkedlistIndexedCollection.
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
		size = 0; 
	}
	
	public int size() {
		return size; 
	}
	
	/**
	 * Adds given value into collection at the end of the collection.
	 * @param value value to be added
	 */
	public void add(Object value) {
		if (value == null) throw new NullPointerException(); 
		
		if (first == null) {
			ListNode node = new ListNode(value, null, null);
			first = last = node; 
		} else {
			ListNode node = new ListNode(value, last, null); 
			last.next = node; 
			last = node;
		}
		size++;
	}
	
	/**
	 * Returns object stored at position index.
	 * @param index position of object to be returned
	 * @return object at index
	 */
	public Object get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException(); 
		} 	
		ListNode temp; 
		if (index <= size / 2) {
			temp = first; 
			for (int i = 0; i < index; i++) {
				temp = temp.next; 
			}
			return temp.value; 
		} else {
			temp = last; 
			for (int i = size - 1; i > index; i--) {
				temp = temp.previous; 
			}
			return temp.value; 
		}
	}
	
	public void clear() {
		first = last = null;
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
		if (position < 0 || position > size) throw new IndexOutOfBoundsException("Position is invalid.");
		if (value == null) throw new NullPointerException("Value must not be null.");
		
		add(last.value);
		ListNode temp = last; 
		for (int i = size - 1; i > position; i--) {
			temp.value = temp.previous.value; 
			temp = temp.previous;
		}
		temp.value = value; 
	}
	
	/**
	 * Returns the index of the first occurence of given value in collection
	 * or -1 if value is not found.
	 * 
	 * @param value - value whose index is to be found
	 * @return index at which value is found, otherwise -1
	 */
	public int indexOf(Object value) {
		ListNode temp = first; 
		for (int i = 0; i < size; i++) {
			if (temp.value.equals(value)) return i; 
			temp = temp.next; 
		}
		return -1;
	}
	
	/**
	 * Removes element at specified index from collection.
	 * All elements at greater positions will be shifted one place down.
	 * @param index position from which an element will be removed
	 * @throws IndexOutOfBoundsException if <code>index</code> is not in interval
	 * 0 to <code>size-1</code>
	 */
	public void remove(int index) {
		if (index < 0 || index > size - 1) throw new IndexOutOfBoundsException("Index is invalid.");
			
		ListNode temp = first; 
		if (index == 0) {
			first = first.next;  
			first.previous = null;
			size--;
			return; 
		}
		if (index == size - 1) {
			last = last.previous; 
			last.next = null;
			size--;
			return; 
		}
		for (int i = 0; i < index - 1; i++) {
			temp = temp.next; 
		}
		temp.next = temp.next.next; 
		temp = temp.next;  
		temp.previous = temp.previous.previous; 
		size--;
	}
	
	public boolean contains(Object value) {
		if (indexOf(value) != -1) {
			return true; 
		} else {
			return false; 
		}
	}
	
	public Object[] toArray() {
		Object[] array = new Object[size];
		ListNode temp = first; 
		for (int i = 0; i < size; i++) {
			array[i] = temp.value; 
			temp = temp.next;
		}
		return array; 
	}
	
	public void forEach(Processor processor) {
		ListNode temp = first; 
		for (int i = 0; i < size; i++) {
			processor.process(temp.value);
			temp = temp.next; 
		}
	}
	
}
