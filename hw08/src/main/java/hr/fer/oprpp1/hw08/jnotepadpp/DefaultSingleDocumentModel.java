package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DefaultSingleDocumentModel implements SingleDocumentModel {

	private Path path;  
	private JTextArea textArea; 
	private boolean modified; 
	private List<SingleDocumentListener> listOfListeners = new ArrayList<>(); 
	

	public DefaultSingleDocumentModel(Path path, String textContent) {
		this.path = path;
		textArea = new JTextArea(textContent);
		textArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true); 
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true); 
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true); 
			}
			
		});
	}
	
	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	@Override
	public Path getFilePath() {
		return path;
	}

	@Override
	public void setFilePath(Path path) {
		if (path == null) throw new IllegalArgumentException("Path cannot be null.");
		this.path = path;
		notifyListenersPathChanged();
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		boolean oldModifiedStatus = isModified();
		this.modified = modified;			
		if (oldModifiedStatus != modified) notifyListenersModified();
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listOfListeners.add(l); 
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listOfListeners.remove(l); 
	}
	
	private void notifyListenersModified() {
		for (SingleDocumentListener listener : listOfListeners) {
			listener.documentModifyStatusUpdated(this);
		}
	}
	
	private void notifyListenersPathChanged() {
		for (SingleDocumentListener listener : listOfListeners) {
			listener.documentFilePathUpdated(this);
		}
	}
}
