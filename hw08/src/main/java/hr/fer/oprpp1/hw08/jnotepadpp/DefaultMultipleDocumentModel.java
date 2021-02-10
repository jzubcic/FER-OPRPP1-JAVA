package hr.fer.oprpp1.hw08.jnotepadpp;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTabbedPane;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
	
	private static final long serialVersionUID = 1L;
	private List<SingleDocumentModel> singleDocumentModels = new ArrayList<>();
	private SingleDocumentModel currentModel; 
	private List<MultipleDocumentListener> multipleDocumentListeners = new ArrayList<>();
	
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		Iterator<SingleDocumentModel> myIterator = new Iterator<SingleDocumentModel>() {
			int counter = 0; 
			
			@Override
			public boolean hasNext() {
				if (counter < singleDocumentModels.size()) {
					return true; 
				} else {
					return false;
				}
			}

			@Override
			public SingleDocumentModel next() {				
				return singleDocumentModels.get(counter++);
			}
		};
		
		return myIterator; 
	}
	
	public void setCurrentModel(SingleDocumentModel model) {
		currentModel = model; 
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newModel = new DefaultSingleDocumentModel(null, ""); 
		newModel.setModified(true);
		singleDocumentModels.add(newModel);
		currentModel = newModel; 
		return newModel;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentModel;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if (path == null) throw new IllegalArgumentException("Path must not be null.");
		
		String textContent = new String(); 
		try (FileInputStream in = new FileInputStream(path.toString());
				ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			byte[] byteArray = new byte[512]; 
			
			int noOfBytes; 
			while ((noOfBytes = in.read(byteArray)) > - 1) {
				out.write(byteArray, 0, noOfBytes);
			}
			
			textContent = out.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SingleDocumentModel loadedModel = new DefaultSingleDocumentModel(path, textContent);
		
		singleDocumentModels.add(loadedModel); 
		loadedModel.setModified(false);
		currentModel = loadedModel;
		return loadedModel;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if (newPath == null) newPath = model.getFilePath();
		
		byte[] data = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8); 
		
		try {
			Files.write(newPath, data);
		} catch (IOException ioe) {
			System.out.println("Pogreska pri spremanju datoteke.");
			return; 
		}
		model.setFilePath(newPath);
		model.setModified(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		singleDocumentModels.remove(model);
		if (singleDocumentModels.size() != 0) {
			currentModel = singleDocumentModels.get(0); 
		} else {
			currentModel = null; 
		}
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		multipleDocumentListeners.add(l); 
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		multipleDocumentListeners.remove(l); 
	}

	@Override
	public int getNumberOfDocuments() {
		return singleDocumentModels.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return singleDocumentModels.get(index);
	}

	
}
