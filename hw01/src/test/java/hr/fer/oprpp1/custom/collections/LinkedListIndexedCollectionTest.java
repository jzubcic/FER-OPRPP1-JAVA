package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkedListIndexedCollectionTest {
	
	@Test
	public void testAddToCollection() {
		Object[] expected = {1, 2, 3};
		
		LinkedListIndexedCollection array = new LinkedListIndexedCollection(); 
		array.add(1);
		array.add(2);
		array.add(3);
		Object[] result = array.toArray();
		assertArrayEquals(expected, result); 	
	}
	
	@Test
	public void testGetBeginningMiddleEnd() {
		LinkedListIndexedCollection array = new LinkedListIndexedCollection();
		for (int i = 0; i < 4; i++) {
			array.add(i);
		}
		
		Integer[] expected = {0, 1, 3};
		Integer[] result = new Integer[3];
		result[0] = (Integer) array.get(0);
		result[1] = (Integer) array.get(1);
		result[2] = (Integer) array.get(3);
		
		assertArrayEquals(expected, result);
		
	}
	
	@Test
	public void testGetShouldThrow() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			LinkedListIndexedCollection array = new LinkedListIndexedCollection();
			array.add(0);
			array.add(1);
			array.add(2);
			int x =(int) array.get(5);
		});
	}
	
	@Test
	public void testClear() {
		LinkedListIndexedCollection array = new LinkedListIndexedCollection();
		array.add(1);
		array.add(2);
		
		array.clear();
				
		assertEquals(0, array.size());
		
	}
	
	@Test
	public void testInsertBeginningMiddleEnd() {
		LinkedListIndexedCollection array = new LinkedListIndexedCollection();
		array.add(0);
		array.add(1);
		array.add(2);
		
		array.insert(3, 0);
		array.insert(4, 1);
		array.insert(5, 5);
		
		Object[] expected = {3, 4, 5};
		Object[] result = {
			array.get(0),
			array.get(1),
			array.get(5)
		};
	
		assertArrayEquals(expected, result);
		
	}
	
	@Test
	public void testInsertShouldThrowNull() {
		assertThrows(NullPointerException.class, () -> {
			LinkedListIndexedCollection array = new LinkedListIndexedCollection();
			array.insert(null, 0);
		});
	}
	
	@Test
	public void testInsertShouldThrowIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			LinkedListIndexedCollection array = new LinkedListIndexedCollection();
			array.insert(1, -1);
		});
	}
	
	@Test
	public void testIndexOf() {
		LinkedListIndexedCollection array = new LinkedListIndexedCollection(); 
		array.add(1);
		array.add(2);
		array.add(3);
		
		int expected = 1; 
		int result = array.indexOf(2); 
		assertEquals(expected, result); 
	}
	
	@Test
	public void testRemoveShoudThrow() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			LinkedListIndexedCollection array = new LinkedListIndexedCollection(); 
			array.add(1);
			
			array.remove(-23);
		});
	}
	
	@Test
	public void testRemoveEnd() {
		LinkedListIndexedCollection array = new LinkedListIndexedCollection();
		array.add(0);
		array.add(1);
		array.add(2);
		
		array.remove(2);
		int[] expected = { 0, 1};
		int[] result = {
				array.indexOf(0),
				array.indexOf(1)
		};
	
		assertArrayEquals(expected, result); 
	}
	
	@Test
	public void testRemoveStart() {
		LinkedListIndexedCollection array = new LinkedListIndexedCollection();
		array.add(0);
		array.add(1);
		array.add(2);
		
		array.remove(0);
		int[] expected = { 0, 1};
		int[] result = {
				array.indexOf(1),
				array.indexOf(2)
		};
	
		assertArrayEquals(expected, result); 
	}
	
	@Test
	public void testRemoveMiddle() {
		LinkedListIndexedCollection array = new LinkedListIndexedCollection();
		array.add(0);
		array.add(1);
		array.add(2);
		
		array.remove(1);
		int[] expected = { 0, 1};
		int[] result = {
				array.indexOf(0),
				array.indexOf(2)
		};
	
		assertArrayEquals(expected, result); 
	}
}
