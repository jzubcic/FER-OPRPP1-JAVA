package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Class which contains all data for representing a bar chart.
 *
 */
public class BarChart {

	private List<XYValue> listOValues;
	private String x; 
	private String y; 
	private int minY; 
	private int maxY; 
	private int gap;
	
	/**
	 * Constructor.
	 * @param listOValues - list which contains values of barchart
	 * @param x - name of x-axis
	 * @param y - name of y-axis
	 * @param minY - minimum value on y-axis
	 * @param maxY - maximum value on y-axis
	 * @param gap - increments between values on y-axis
	 */
	public BarChart(List<XYValue> listOValues, String x, String y, int minY, int maxY, int gap) {
		super();
		this.listOValues = listOValues;
		this.x = x;
		this.y = y;
		this.minY = minY;
		this.maxY = maxY;
		this.gap = gap;
		while ((this.maxY - this.minY) % gap != 0) {
			this.maxY++;
		}
		
		for (XYValue value : listOValues) {
			if (value.getY() < minY) throw new IllegalArgumentException("Value must not be less than minimum.");
		}
	}

	public List<XYValue> getListOValues() {
		return listOValues;
	}

	public String getX() {
		return x;
	}

	public String getY() {
		return y;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getGap() {
		return gap;
	} 
	
	
	
	
}
