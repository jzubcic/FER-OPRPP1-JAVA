package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	List<ILocalizationListener> listOfListeners = new ArrayList<>(); 
	
	public void addLocalizationListener(ILocalizationListener listener) {
		listOfListeners.add(listener);
	}
	
	public void removeLocalizationListener(ILocalizationListener listener) {
		listOfListeners.remove(listener); 
	}
	
	public void fire() {
		for (ILocalizationListener listener : listOfListeners) {
			listener.localizationChanged();
		}
	}
}
