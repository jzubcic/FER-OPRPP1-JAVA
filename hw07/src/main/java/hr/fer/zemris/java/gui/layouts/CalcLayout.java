package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

public class CalcLayout implements LayoutManager2 {
	
	private int numberOfRows = 5; 
	private int numberOfColumns = 7; 
	
	/**
	 * Map which keeps track of occupied position in layout.
	 */
	private Map<RCPosition, Component> occupiedPositions = new HashMap<>(); 
	
	private int spaceBetween; 
	
	/**
	 * Constructor which receives number of pixels between 
	 * rows and columns as argument.
	 * 
	 * @param spaceBetween - distance between rows and columns in pixels
	 */
	public CalcLayout(int spaceBetween) {
		this.spaceBetween = spaceBetween; 
	}
	
	/**
	 * Constructor which sets space between rows and columns to zero.
	 */
	public CalcLayout() {
		this(0); 
	}

	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("This operation is not supported."); 		
	}

	public void removeLayoutComponent(Component comp) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Method returns Dimension as greatest preferred width and greatest preferred height 
	 * of all components. 
	 * 
	 */
	public Dimension preferredLayoutSize(Container parent) {
		return calculateDimension(parent, "preferred"); 
	}

	public Dimension minimumLayoutSize(Container parent) {
		return calculateDimension(parent, "min");
	}

	public void layoutContainer(Container parent) {
		
		int componentWidth = (parent.getWidth() - spaceBetween * (numberOfColumns - 1)) / numberOfColumns;
		int componentHeight = (parent.getHeight() - spaceBetween * (numberOfRows - 1)) / numberOfRows;
		
		for (int j = 0; j < numberOfColumns; j++) {
			for (int i = 0; i < numberOfRows; i++) {				
				if (occupiedPositions.containsKey(new RCPosition(i+1, j+1))) {
					if (i == 0 && j == 0) {
						occupiedPositions.get(new RCPosition(i+1, j+1)).setBounds(0, 0, componentWidth * 5 + 4 * spaceBetween, componentHeight);
					} else {
						occupiedPositions.get(new RCPosition(i+1, j+1)).setBounds(j * componentWidth + (j-1) * spaceBetween, i * componentHeight + (i-1) * spaceBetween,
																			componentWidth, componentHeight);
					}
				}
			}
		}
	}

	public void addLayoutComponent(Component comp, Object constraints) {
		if (!(constraints instanceof String) && ! (constraints instanceof RCPosition)) {
			throw new IllegalArgumentException("Constraints must be String or RCPosition.");
		}
		if (comp == null || constraints == null) throw new NullPointerException("Arguments must not be null.");
		
		RCPosition rcPosition;
		if (constraints instanceof String) {
			rcPosition = RCPosition.parse((String) constraints);
		} else {
			rcPosition = (RCPosition) constraints; 
		}
		
		
		int row = rcPosition.getRow();
		int column = rcPosition.getColumn();
		
		if (row == 1 && column > 1 && column < 6) throw new CalcLayoutException("Position cannot be used."); 
		if (row > 5 || row < 1 || column > 7 || column < 1) throw new CalcLayoutException("Invalid constraints."); 
		if (occupiedPositions.containsKey(rcPosition)) throw new CalcLayoutException("Component with same constraints already exists."); 
		
		occupiedPositions.put(rcPosition, comp); 
	}

	public Dimension maximumLayoutSize(Container target) {
		return calculateDimension(target, "max");
	}

	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	public void invalidateLayout(Container target) {
		
	}
	 
	private Dimension calculateDimension(Container container, String size) {
		int maxWidth = 0;
		int maxHeight = 0; 
		
		for (Component c : container.getComponents()) {
			switch(size) {
			case "preferred": 
				if (c.getWidth() > maxWidth) maxWidth = c.getWidth();
				if (c.getHeight() > maxHeight) maxHeight = c.getHeight();	
				break;
			case "max": 
				if (c.getMaximumSize().getWidth() > maxWidth) maxWidth = (int) c.getMaximumSize().getWidth();
				if (c.getMaximumSize().getHeight() > maxHeight) maxHeight = (int) c.getMaximumSize().getHeight();
			case "min": 
				if (c.getMinimumSize().getWidth() > maxWidth) maxWidth = (int) c.getMinimumSize().getWidth();
				if (c.getMinimumSize().getHeight() > maxHeight) maxHeight = (int) c.getMinimumSize().getHeight();
			}
		}
		
		return new Dimension(maxWidth, maxHeight); 
	}

}
