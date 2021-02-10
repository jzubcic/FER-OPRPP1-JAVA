package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Class which is a ListModel of prime numbers. 
 */
public class PrimListModel implements ListModel<Integer> {

	/**
	 *  List of prime numbers.
	 */
	private List<Integer> listOfPrims = new ArrayList<>();
	
	/**
	 *  List of listeners to be notified of changes.
	 */
	private List<ListDataListener> listOfListeners = new ArrayList<>(); 
	
	/**
	 * Constructor.
	 */
	public PrimListModel() {
		super(); 
		listOfPrims.add(1); 
	}
	
	@Override
	public int getSize() {
		return listOfPrims.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return listOfPrims.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listOfListeners.add(l); 
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listOfListeners.remove(l); 
		
	}
	
	/**
	 * Adds next prime number into list and notifies listeners.
	 */
	public void next() { 
		int n = listOfPrims.get(getSize() - 1); 
		
		boolean found = false; 		
		while(!found) {
			if (isPrime(++n)) {
				listOfPrims.add(n); 
				found = true; 
			}
		}
		
		for (ListDataListener l : listOfListeners) {
			l.intervalAdded(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, getSize() - 1, getSize() - 2));
		}
	}
	
	private boolean isPrime(int n) {
		
		for (int i = 2; i <= n / 2; i++) {
			if (n % i == 0) return false; 
		}
		
		return true; 
	}

	
}
