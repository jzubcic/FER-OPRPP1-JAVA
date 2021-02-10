package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;


import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;

public class JNotepadPP extends JFrame { 

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane = new JTabbedPane();
	private StatusBar statusBar = new StatusBar(); 
	private JMenuItem upperItem;
	private JMenuItem lowerItem;
	private JMenuItem invertItem;
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this); 
	private DefaultMultipleDocumentModel model;
	private Clock clock; 

	public JNotepadPP() {
		super(); 
		model = new DefaultMultipleDocumentModel();
		setTitle("JNotepad++");
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
				if (!areModificationsPresent()) {
					dispose(); 
					clock.killClock();
				} else { 
					int response = JOptionPane.showConfirmDialog(null, "There are unsaved files, are you sure you want to close?");
					if (response == JOptionPane.YES_OPTION) { 
						dispose();
						clock.killClock();
					}
				}
			}
		});
		initGUI(); 
		setSize(500,500);  
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				LocalizationProvider.getInstance().setLanguage("en");
				new JNotepadPP().setVisible(true);
			}
		});
	}
	
	/**
	 * Saves current document. 
	 */
	private Action saveAction = new LocalizableAction("save", "save_desc", flp) {
		
		private static final long serialVersionUID = 1L; 

		@Override
		public void actionPerformed(ActionEvent e) {
			Path filePath = model.getCurrentDocument().getFilePath();
			if (filePath == null) {
				JFileChooser jfc = new JFileChooser(); 
				jfc.setDialogTitle("Save document");
				if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(
							JNotepadPP.this, 
							"Ništa nije snimljeno.", 
							"Upozorenje", 
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				filePath = jfc.getSelectedFile().toPath(); 
			}
			
			model.saveDocument(model.getCurrentDocument(), filePath);
		}
	};
	
	/**
	 * Action which opens existing document in new editor tab.
	 */
	private Action openAction = new LocalizableAction("open", "open_desc", flp) {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser(); 
			jfc.setDialogTitle("Open file");
			if(jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			Path filePath = jfc.getSelectedFile().toPath(); 
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Datoteka "+jfc.getSelectedFile().getAbsolutePath()+" ne postoji!", 
						"Pogreška", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			byte[] data; 
			try {
				data = Files.readAllBytes(filePath); 
			} catch (IOException ioe) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Pogreška prilikom čitanja datoteke "+ jfc.getSelectedFile().getAbsolutePath()+".", 
						"Pogreška", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String text = new String(data, StandardCharsets.UTF_8); 
			SingleDocumentModel newDocument = model.loadDocument(filePath); 
			
			tabbedPane.add(newDocument.getTextComponent()); 
			tabbedPane.setSelectedComponent(newDocument.getTextComponent());
			model.getCurrentDocument().getTextComponent().setText(text);
			model.getCurrentDocument().setModified(false);
			model.getCurrentDocument().getTextComponent().addCaretListener(new MyCaretListener());
			model.getCurrentDocument().addSingleDocumentListener(new MySingleDocumentListener());
			MySingleDocumentListener listener = new MySingleDocumentListener(); 
			listener.documentModifyStatusUpdated(model.getCurrentDocument());
			listener.documentFilePathUpdated(model.getCurrentDocument());
		}
		
	};
	
	/**
	 * Action which creates new tab in text editor and switches to it.
	 */
	private Action newAction = new LocalizableAction("new", "new_desc", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel newDocument = model.createNewDocument(); 
			model.getCurrentDocument().addSingleDocumentListener(new MySingleDocumentListener());
			model.getCurrentDocument().getTextComponent().addCaretListener(new MyCaretListener());
			tabbedPane.add(newDocument.getTextComponent()); 
			tabbedPane.setSelectedComponent(newDocument.getTextComponent());
			tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), newDocument.getFilePath() != null ? 
								newDocument.getFilePath().getFileName().toString() : "(unnamed)");
			MySingleDocumentListener listener = new MySingleDocumentListener(); 
			listener.documentModifyStatusUpdated(model.getCurrentDocument());
			listener.documentFilePathUpdated(model.getCurrentDocument());
		}
		
	};
	
	/**
	 * Saves current document to location user chooses.
	 */
	private Action saveAsAction = new LocalizableAction("save_as", "save_as_desc", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {		
			JFileChooser jfc = new JFileChooser(); 
			jfc.setDialogTitle("Save document");
			if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Ništa nije snimljeno.", 
						"Upozorenje", 
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			Path filePath = jfc.getSelectedFile().toPath(); 
			
			model.saveDocument(model.getCurrentDocument(), filePath);
			
		}
		
	};
	
	/**
	 * Cuts selected text and puts it into clipboard.
	 */
	private Action cutAction = new LocalizableAction("cut", "cut_desc", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Action cut = new DefaultEditorKit.CutAction(); 
			cut.actionPerformed(e);
		}
		
	};
	
	/**
	 * Copies selected text into clipboard.
	 */
	private Action copyAction = new LocalizableAction("copy", "copy_desc", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Action copy = new DefaultEditorKit.CopyAction(); 
			copy.actionPerformed(e);			
		}
		
	};
	
	/**
	 * Inserts text from clipboard into current document.
	 */
	private Action pasteAction = new LocalizableAction("paste", "paste_desc", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Action paste = new DefaultEditorKit.PasteAction(); 
			paste.actionPerformed(e);
		}
		
	};
	
	/**
	 * Closes current tab. 
	 */
	private Action closeAction = new LocalizableAction("close", "close_desc", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model.getCurrentDocument().isModified()) {	
				int response = JOptionPane.showConfirmDialog(null, "There are unsaved files, are you sure you want to close?");
				if (response == JOptionPane.NO_OPTION || response == JOptionPane.CANCEL_OPTION || response == JOptionPane.CLOSED_OPTION) { 
					return; 
				} 
			}
			
			tabbedPane.remove(tabbedPane.getSelectedComponent()); 
			model.closeDocument(model.getCurrentDocument());
		}
		
	};
	
	/**
	 * Displays info about current document.
	 */
	private Action infoAction = new LocalizableAction("info", "info_desc", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = model.getCurrentDocument().getTextComponent().getDocument(); 
			int numberOfCharacters = doc.getLength(); 
			String docText = null; 
			try {
				docText = doc.getText(0, doc.getLength());
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			} 
			int nonBlank = docText.replaceAll("\\s+", "").length(); 
			String[] lines = docText.split("\r\n|\r|\n"); 
			JOptionPane.showMessageDialog(JNotepadPP.this, "Your message has " + numberOfCharacters + " characters, " 
											+ nonBlank + " non-blank characters and " + lines.length + " lines.");
			
		}
		
	};
			
	private Action toUpperAction = new LocalizableAction("to_upper_case", "to_upper_desc", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			String selectedText = model.getCurrentDocument().getTextComponent().getSelectedText(); 
			try {
				model.getCurrentDocument().getTextComponent().getDocument().remove(model.getCurrentDocument().getTextComponent().getSelectionStart(), selectedText.length());
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			model.getCurrentDocument().getTextComponent().insert(selectedText.toUpperCase(), model.getCurrentDocument().getTextComponent().getSelectionStart());
			
		}
		
	};
	
	/**
	 * Changes selected text to lower case.
	 */
	private Action toLowerAction = new LocalizableAction("to_lower_case", "to_lower_desc", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			String selectedText = model.getCurrentDocument().getTextComponent().getSelectedText(); 
			try {
				model.getCurrentDocument().getTextComponent().getDocument().remove(model.getCurrentDocument().getTextComponent().getSelectionStart(), selectedText.length());
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			model.getCurrentDocument().getTextComponent().insert(selectedText.toLowerCase(), model.getCurrentDocument().getTextComponent().getSelectionStart());
			
		}
		
	};
	
	private Action invertAction = (Action) new LocalizableAction("invert", "invert_desc", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			String selectedText = model.getCurrentDocument().getTextComponent().getSelectedText(); 
			try {
				model.getCurrentDocument().getTextComponent().getDocument().remove(model.getCurrentDocument().getTextComponent().getSelectionStart(), selectedText.length());
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			StringBuilder sb = new StringBuilder(); 
			for (Character c : selectedText.toCharArray()) {
				 if (Character.isUpperCase(c)) {
					 sb.append(Character.toLowerCase(c));
				 } else {
					 sb.append(Character.toUpperCase(c));
				 }
			}
			model.getCurrentDocument().getTextComponent().insert(sb.toString(), model.getCurrentDocument().getTextComponent().getSelectionStart());
		}
	};
	
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		
		createActions(); 
		  
		JMenuBar menuBar = new JMenuBar(); 
		
		LJMenu fileMenu = new LJMenu("file", flp); 
		JMenuItem newItem = new JMenuItem(newAction); 
		JMenuItem openItem = new JMenuItem(openAction);
		JMenuItem saveItem = new JMenuItem(saveAction);
		JMenuItem saveAsItem = new JMenuItem(saveAsAction);
		
		fileMenu.add(newItem); 
		fileMenu.add(openItem); 
		fileMenu.add(saveItem); 
		fileMenu.add(saveAsItem);
		menuBar.add(fileMenu);
		
		LJMenu optionMenu = new LJMenu("options", flp); 
		JMenuItem cutItem = new JMenuItem(cutAction); 
		JMenuItem copyItem = new JMenuItem(copyAction); 
		JMenuItem pasteItem = new JMenuItem(pasteAction); 
		JMenuItem infoItem = new JMenuItem(infoAction); 
		JMenuItem closeItem = new JMenuItem(closeAction); 
		
		optionMenu.add(cutItem);
		optionMenu.add(copyItem);
		optionMenu.add(pasteItem);
		optionMenu.add(infoItem);
		optionMenu.add(closeItem); 
		menuBar.add(optionMenu);
		
		LJMenu languageMenu = new LJMenu("languages", flp); 
		JMenuItem hrItem = new JMenuItem("Hr");
		JMenuItem enItem = new JMenuItem("En");
		JMenuItem deItem = new JMenuItem("De");
		
		hrItem.addActionListener(l -> {
			LocalizationProvider.getInstance().setLanguage("hr");
		});
		
		enItem.addActionListener(l -> {
			LocalizationProvider.getInstance().setLanguage("en");
		});
		
		deItem.addActionListener(l -> {
			LocalizationProvider.getInstance().setLanguage("de");
		});
		
		languageMenu.add(hrItem); 
		languageMenu.add(enItem);
		languageMenu.add(deItem);
		menuBar.add(languageMenu); 
		
		LJMenu toolMenu = new LJMenu("tools", flp); 
		LJMenu changeCaseMenu = new LJMenu("change_case", flp); 
		upperItem = new JMenuItem(toUpperAction);
		lowerItem = new JMenuItem(toLowerAction);
		invertItem = new JMenuItem(invertAction);
		changeCaseMenu.add(upperItem);
		changeCaseMenu.add(lowerItem);
		changeCaseMenu.add(invertItem);
		toolMenu.add(changeCaseMenu); 
		menuBar.add(toolMenu);
		
		JToolBar toolBar = new JToolBar("Alati"); 
		toolBar.setFloatable(true);
		
		toolBar.add(new JButton(newAction));
		toolBar.add(new JButton(openAction));
		toolBar.add(new JButton(saveAction));
		toolBar.add(new JButton(saveAsAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(cutAction));
		toolBar.add(new JButton(copyAction));
		toolBar.add(new JButton(pasteAction));
		toolBar.add(new JButton(infoAction));
		toolBar.add(new JButton(closeAction));

		
		clock = new Clock(); 
		clock.setOpaque(true);
		clock.setSize(300, 17);
		clock.setLocation(200, 0);
		clock.setVisible(true);
		
		statusBar.add(clock);  
	
		
		JPanel panel = new JPanel(); 
		JScrollPane scrollPane = new JScrollPane();
		
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabbedPane.getSelectedIndex() != -1) {
					model.setCurrentModel(model.getDocument(tabbedPane.getSelectedIndex()));
					JNotepadPP.this.setTitle(model.getCurrentDocument().getFilePath() != null ? model.getCurrentDocument().getFilePath().toString() 
							: "(unnamed)" + "- JNotepad++");
					tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), model.getCurrentDocument().getFilePath() != null ? 
							model.getCurrentDocument().getFilePath().getFileName().toString() : "(unnamed)");
					
					statusBar.setLength(model.getCurrentDocument().getTextComponent().getDocument().getLength());
					int caretPos = model.getCurrentDocument().getTextComponent().getCaretPosition();
					if (model.getCurrentDocument().getTextComponent().getSelectedText() != null)	
						statusBar.setSelection(model.getCurrentDocument().getTextComponent().getSelectionEnd() - model.getCurrentDocument().getTextComponent().getSelectionStart());
					else 
						statusBar.setSelection(0);
		 
					try {
						statusBar.setLine(model.getCurrentDocument().getTextComponent().getLineOfOffset(caretPos) + 1);
						statusBar.setColumn(caretPos - model.getCurrentDocument().getTextComponent().getLineStartOffset(model.getCurrentDocument().getTextComponent().getLineOfOffset(caretPos)) + 1);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
				}
			}	
		});

		panel.add(tabbedPane);
		scrollPane.add(panel);
		 
		
		
		this.setJMenuBar(menuBar);
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
		getContentPane().add(tabbedPane, BorderLayout.CENTER); 
		getContentPane().add(statusBar, BorderLayout.SOUTH); 
		
		
	}
	
	class MyCaretListener implements CaretListener {

		@Override
		public void caretUpdate(CaretEvent e) {
			statusBar.setLength(model.getCurrentDocument().getTextComponent().getDocument().getLength());
			int caretPos = model.getCurrentDocument().getTextComponent().getCaretPosition();
			if (model.getCurrentDocument().getTextComponent().getSelectedText() != null) {	
				upperItem.setEnabled(true);
				lowerItem.setEnabled(true);
				invertItem.setEnabled(true);
				statusBar.setSelection(model.getCurrentDocument().getTextComponent().getSelectionEnd() - model.getCurrentDocument().getTextComponent().getSelectionStart());
			} else {
				upperItem.setEnabled(false);
				lowerItem.setEnabled(false);
				invertItem.setEnabled(false);
				statusBar.setSelection(0);
			}
			
			try {
				statusBar.setLine(model.getCurrentDocument().getTextComponent().getLineOfOffset(caretPos) + 1);
				statusBar.setColumn(caretPos - model.getCurrentDocument().getTextComponent().getLineStartOffset(model.getCurrentDocument().getTextComponent().getLineOfOffset(caretPos)) + 1);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	class MySingleDocumentListener implements SingleDocumentListener {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			InputStream is = this.getClass().getResourceAsStream(model.isModified() ? 
					"icons/redDisk.png" : "icons/greenDisk.png");
			if (is == null) {
				System.out.println("Error with loading icon.");
				return; 
			}
			
			byte[] bytes = null; 
			try {
				bytes = is.readAllBytes();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
			ImageIcon icon = new ImageIcon(bytes); 
			
			tabbedPane.setIconAt(tabbedPane.getSelectedIndex(), icon);
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			JNotepadPP.this.setTitle(model.getFilePath() != null ? model.getFilePath().toString() 
					: "(unnamed)" + "- JNotepad++");
			tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), model.getFilePath() != null ? 
					model.getFilePath().getFileName().toString() : "(unnamed)");
			if (model.getFilePath() != null)
				tabbedPane.setToolTipTextAt(tabbedPane.getSelectedIndex(), model.getFilePath().toString());
		}
		
	}
	
	private void createActions() {
		newAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		newAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		
		openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

		saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		
		saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F12"));
		
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		
		infoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		infoAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		
		closeAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		
	}
	
	/**
	 * Method which checks if any documents are modified, returns true if they are,
	 * false otherwise.
	 */
	private boolean areModificationsPresent() {
		Iterator<SingleDocumentModel> iterator = model.iterator(); 
		while(iterator.hasNext()) {
			if (iterator.next().isModified()) return true; 
		}
		return false;  
	}

}
