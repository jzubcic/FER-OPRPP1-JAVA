package hr.fer.oprpp1.custom.collections;

public class Dictionary<K,V> {

	private class Entry<K,V> {
		private K key; 
		private V value; 
		
		public Entry(K key, V value) {
			this.key = key;
			this.value = value; 
		}
		
		public K getKey() {
			return key; 
		}
		
		public V getValue() {
			return value; 
		}
		
		public void setValue(V value) {
			this.value = value; 
		}
	}
	
	private ArrayIndexedCollection<Entry<K,V>> coll; 
	
	public Dictionary() {
		coll = new ArrayIndexedCollection<Entry<K,V>>();
	}
	
	/**
	 * Returns true if Dictionary has no entries.
	 * @return true if Dictionary is empty, otherwise false
	 */
	public boolean isEmpty() {
		return coll.isEmpty(); 
	}
	
	/**
	 * Returns the number of entries in Dictionary.
	 * @return size of dictionary
	 */
	public int size() {
		return coll.size();
	}
	
	/**
	 * Removes all entries from Dictionary.
	 */
	public void clear() {
		coll.clear(); 
	}
	
	/**
	 * Adds Entry with key <code>key</code> and value <code>value</code>. 
	 * If an Entry with same key already exists in Dictionary, method replaces its value with 
	 * given value. 
	 * 
	 * @param key key of given Entry
	 * @param value value of given Entry
	 * @return value of given Entry
	 * @throws NullPointerException if key is <code>null</code>
	 */
	public V put(K key, V value) {
		if (key == null) throw new NullPointerException("Key must not be null.");
		
		boolean contains = false;
		ElementsGetter<Entry<K, V>> elGetter = coll.createElementsGetter();
		
		while (elGetter.hasNextElement()) {
			Entry<K, V> current = elGetter.getNextElement(); 
			if (current.getKey().equals(key)) {
				V oldValue = current.getValue();
				current.setValue(value);
				return oldValue; 
			}
		}
		
		coll.add(new Entry<K,V>(key, value));
		return null; 
	}
	
	/**
	 * Returns value of Entry with given key.
	 * @param key key of Entry whose value is to be returned
	 * @return value of Entry with given key, <code>null</code> if it doesn't exist
	 * @throws NullPointerException if key is <code>null</code>
	 */
	public V get(Object key) {
		if (key == null) throw new NullPointerException("Key must not be null.");
		
		ElementsGetter<Entry<K, V>> elGetter = coll.createElementsGetter();
		
		while (elGetter.hasNextElement()) {
			Entry<K, V> current = elGetter.getNextElement(); 
			if (current.getKey().equals(key)) {
				return current.getValue(); 
			}
		}
		return null; 
	}
	
	/**
	 * Method removes Entry with given key from Dictionary.
	 * @param key key of Entry to be removed
	 * @return value that was in dictionary under given key, 
	 * null if there was no Entry with given key
	 * @throws NullPointerException if key is <code>null</code>
	 */
	public V remove(K key) {
		if (key == null) throw new NullPointerException("Key must not be null.");
		
		ElementsGetter<Entry<K, V>> elGetter = coll.createElementsGetter();
		
		while (elGetter.hasNextElement()) {
			Entry<K, V> current = elGetter.getNextElement(); 
			if (current.getKey().equals(key)) {
				V oldValue = current.getValue(); 
				coll.remove(current);
				return oldValue;						
			}
		}
		return null;
	}
	
	
}
