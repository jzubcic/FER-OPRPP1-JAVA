package hr.fer.zemris.java.gui.charts;

/**
 * Class which represents pair of values; 
 * value on x-axis and value on y-axis.
 *
 */
public class XYValue {

	/**
	 * Value on x-axis.
	 */
	private int x; 
	/**
	 * Value on y-axis
	 */
	private int y;
	
	/**
	 * Constructor
	 * @param x - value on x-axis
	 * @param y - value on y-axis
	 */
	public XYValue(int x, int y) {
		this.x = x; 
		this.y = y; 
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	} 
	
	
}
