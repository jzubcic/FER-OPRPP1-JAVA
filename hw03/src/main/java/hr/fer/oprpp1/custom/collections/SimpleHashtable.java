package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	public static class TableEntry<K, V> {
		private K key;
		private V value; 
		private TableEntry<K,V> next;
		
		public TableEntry(K key, V value) {
			this.key = key; 
			this.value = value; 
			this.next = null; 
		}
		
		public K getKey() {
			return key; 
		}
		
		public void setValue(V value) {
			this.value = value; 
		}
		
		public V getValue() {
			return value; 
		}
	}
	
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		private TableEntry<K, V> current; 
		private int currentPosition; 
		private int lastKnownSlot; 
		private int depthInSlot; 
		private int savedModCount; 
		
		public IteratorImpl() {
			this.current = null; 
			currentPosition = 0; 
			lastKnownSlot = 0; 
			depthInSlot = 0; 
			savedModCount = modificationCount; 
		}
		
		public boolean hasNext() {
			if (savedModCount != modificationCount) throw new ConcurrentModificationException("Map was modified from outside."); 
			if (currentPosition < SimpleHashtable.this.size()) return true; 
			return false; 
		}
		
		public SimpleHashtable.TableEntry next() {
			if (!hasNext()) throw new NoSuchElementException("Map has no next element.");
			if (savedModCount != modificationCount) throw new ConcurrentModificationException("Map was modified from outside."); 
			
			if (current == null) { 
			
				current = table[lastKnownSlot];
				while (current == null) {
					current = table[++lastKnownSlot];
				}
				
				for (int i = 0; i < depthInSlot; i++) {
					current = current.next; 
				}
				currentPosition++;
				return current; 
			} else { 
				if (current.next != null) {
					current = current.next; 
					depthInSlot++;
					currentPosition++;
					return current; 
				}
				depthInSlot = 0; 
				if (lastKnownSlot != table.length -1) current = table[++lastKnownSlot];
				while (current == null) {
					current = table[++lastKnownSlot];
				}
				currentPosition++;
				return current; 
			}
			
		}
		
		public void remove() {
			if (current == null) throw new IllegalStateException("Remove cannot be called before next() is called at least once.");
			if (savedModCount != modificationCount) throw new ConcurrentModificationException("Map was modified from outside."); 
			
			lastKnownSlot = current.getKey().hashCode() % table.length; 
			if (current.next == null) {
				lastKnownSlot++;
				depthInSlot = 0; 
			}
			
			savedModCount++;
			V checkNull = SimpleHashtable.this.remove(current.getKey());
			current = null; 
			currentPosition--;
		}
	}
	private int size; 
	private TableEntry<K,V>[] table; 
	private int modificationCount; 
	
	public SimpleHashtable() {
		this(16); 
	}
	
	public SimpleHashtable(int capacity) {
		if (capacity < 1) throw new IllegalArgumentException("Capacity must be 1 or greater."); 
		table = (TableEntry<K,V>[]) new TableEntry[(int) Math.pow(2.0,(double) Math.log(capacity) / Math.log(2) + 1)];
		size = 0; 
		modificationCount = 0; 
	}
	
	/**
	 * Method puts Entry with given key and value into map. If an Entry with given 
	 * key already exists in map, method overwrites value and returns old value.
	 * If an Entry with given key did not exist in map, method returns null.
	 * 
	 * @param key key of Entry to be inserted
	 * @param value value of Entry to be inserted
	 * @throws NullPointerException if key is <code>null</code>
	 * @return overwritten value if entry with given key already existed, otherwise <code>null</code>
	 */
	public V put(K key, V value) {
		if (key == null) throw new NullPointerException("Key must not be null.");
		int position = Math.abs(key.hashCode()) % table.length; 
		
		if (containsKey(key)) {
			TableEntry<K,V> temp = table[position]; 
			while (!temp.getKey().equals(key)) {
				temp = temp.next; 
			}
			
			V oldValue = temp.getValue(); 
			temp.setValue(value);
			return oldValue;
		} else {
			if (size/table.length >= 0.75) {
				doubleCapacity();
				position = Math.abs(key.hashCode()) % table.length;
			}
			
			if (table[position] == null) {
				table[position] = new TableEntry<K, V>(key, value);			 
			} else {			
				TableEntry<K,V> temp = table[position]; 
				while (temp.next != null) {
					temp = temp.next; 
				}
				temp.next = new TableEntry<K,V>(key, value);
			}
			
			modificationCount++;
			size++;
			return null;
		}
	}
	
	/**
	 * Returns value from Entry with given key.
	 * @param key key whose value will be returned
	 * @return value of given key's entry
	 */
	public V get(Object key) {
		if (key == null || !containsKey(key)) return null; 
		int position = Math.abs(key.hashCode()) % table.length;
		
		TableEntry<K,V> temp = table[position]; 
		while (temp.next != null) {
			if (temp.getKey().equals(key)) {
				return temp.getValue(); 
			}
			temp = temp.next;
		}
		
		if (temp.getKey().equals(key)) {
			return temp.getValue(); 
		}
		
		return null; 
	}
	
	/**
	 * Returns number of elements in map.
	 * @return number of elements in map
	 */
	public int size() {
		return this.size; 
	}

	/**
	 * Returns true if given key is present in map
	 * @param key key whose presence in map is checked
	 * @return true if key is present, otherwise false
	 */
	public boolean containsKey(Object key) {
		if (key == null) return false; 
		
		int position = Math.abs(key.hashCode()) % table.length; 
		TableEntry<K,V> temp = table[position];
		if (temp == null) return false; 
		
		while (temp != null) {
			if (temp.getKey().equals(key)) {
				return true; 
			}
			temp = temp.next; 
		}
		
		
		return false; 
	}
	
	/**
	 * Returns true if value is present in map
	 * @param value value whose presence in map is checked
	 * @return true if value is present, otherwise false
	 */
	public boolean containsValue(Object value) {
		for (TableEntry<K,V> temp : table) {
			while (temp != null) {
				if (temp.getValue().equals(value)) {
					return true; 
				}
				temp = temp.next; 
			}
			
		}
		
		return false; 
	}
	
	/**
	 * Removes Entry with given key from map.
	 * @param key key of Entry to be removed
	 * @return value of removed Entry, null if no Entry was removed
	 */
	public V remove(Object key) {
		if (!containsKey(key)) return null; 
		
		int position = Math.abs(key.hashCode()) % table.length;
		TableEntry<K,V> temp = table[position];
		
		if (temp.getKey().equals(key)) {
			V oldValue = temp.getValue(); 
			table[position] = temp.next; 
			modificationCount++;
			size--;
			return oldValue; 
		}
		
		while (temp.next != null) {
			if (temp.next.getKey().equals(key)) {
				V oldValue = temp.next.getValue(); 
				temp.next = temp.next.next; 
				modificationCount++;
				size--;
				return oldValue; 
			}
			temp = temp.next; 
		}
		
		return null; 
	}
	
	/**
	 * Returns true if map has no entries.
	 * @return true if map is empty, otherwise false
	 */
	public boolean isEmpty() {
		return this.size == 0; 
	}
	
	/**
	 * Returns map in String format. 
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		sb.append("["); 
		
		for (TableEntry<K,V> temp : table) {
			while (temp != null) {
				sb.append(temp.getKey()); 
				sb.append("="); 
				sb.append(temp.getValue());
				sb.append(", "); 
				temp=temp.next; 
			} 
		}
		sb.replace(sb.length()-2, sb.length(), "]"); 
		 
		
		return sb.toString(); 
	}
	
	/**
	 * Returns map in array format.
	 * @return content of map as array
	 */
	public TableEntry<K,V>[] toArray() {
		TableEntry<K,V>[] array = (TableEntry<K,V>[]) new TableEntry[size]; 
		
		int i = 0; 
		for (TableEntry<K,V> temp : table) {
			while (temp != null && temp.next != null) {
				if (i < table.length - 1) {
					array[i++] = temp;
				} else {
					break; 
				}
				temp = temp.next; 
			}
			if (temp != null) array[i++] = temp; 
		}
		return array; 
	}
	
	/**
	 * Removes all elements from map.
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++) {
			table[i] = null; 
		}
	}
	
	private void doubleCapacity() {
		TableEntry<K,V>[] array = this.toArray();
		table = (TableEntry<K,V>[]) new TableEntry[table.length * 2];
		size = 0; 
		
		for (TableEntry<K,V> entry : array) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl(); 
	}


	
}
