package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class StatusBar extends JLabel {

	private static final long serialVersionUID = 1L;
	private int length; 
	private int line; 
	private int column; 
	private int selection; 
	
	public StatusBar() {
		super(); 
		length = 0; 
		line = 0; 
		column = 0; 
		selection = 0; 
		updateText();
	}
	
	
	
	public StatusBar(int length, int line, int column, int selection) {
		super();
		this.length = length;
		this.line = line;
		this.column = column;
		this.selection = selection;
		setBorder(BorderFactory.createLineBorder(Color.black));
		updateText();
	}



	private void updateText() {
		this.setText("length : " + length + " Ln : " + line + " Col : " + column + " Sel : " + selection);
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
		updateText();
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
		updateText();
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
		updateText();
	}

	public int getSelection() {
		return selection;
	}

	public void setSelection(int selection) {
		this.selection = selection;
		updateText();
	}
	
	
}
