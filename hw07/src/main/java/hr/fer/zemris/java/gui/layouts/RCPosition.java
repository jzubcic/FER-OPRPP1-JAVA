package hr.fer.zemris.java.gui.layouts;

/**
 * Class which models position of components in CalcLayout.
 *
 */
public class RCPosition {

	/**
	 * Row of RCPosition in layout.
	 */
	private int row; 
	
	/**
	 * Column of RCPosition in layout.
	 */
	private int column; 
		
	/**
	 * Constructor
	 * @param row
	 * @param column
	 */
	public RCPosition(int row, int column) {
		this.row = row; 
		this.column = column; 
	}
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	/**
	 * Method which creates RCPosition from String.
	 * @param text - String in format "x,y", where x is row and y is column
	 * 				of RCPosition to be created
	 * @return RCPosition with row x and column y
	 */
	public static RCPosition parse(String text) {
		String strs[] = text.split(","); 
		int newRow = Integer.parseInt(strs[0]); 
		int newColumn = Integer.parseInt(strs[1]);
		return new RCPosition(newRow, newColumn);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	
}
