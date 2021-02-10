package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 *  Class which draws bar chart.
 */
public class BarChartComponent extends JComponent{

	private static final long serialVersionUID = 1L;
	
	/**
	 * BarChart whose values will be used for drawing.
	 */
	private BarChart barChart; 
	
	/**
	 * Constructor.
	 * @param barChart - BarChart to be drawn.
	 */
	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
	}
		
	public void paintComponent(Graphics g) {
		g.setColor(Color.GRAY);
		g.drawLine(30, 30, 30, getHeight() - 30);
		g.drawLine(30, getHeight() - 30, getWidth() - 30, getHeight() - 30);
		
		g.drawLine(25, 40, 30, 30);
		g.drawLine(35, 40, 30, 30);
		
		g.drawLine(getWidth() - 30, getHeight() - 30, getWidth() - 35, getHeight() - 35);
		g.drawLine(getWidth() - 30, getHeight() - 30, getWidth() - 35, getHeight() - 25);
		
		Graphics2D g2d = (Graphics2D) g;
        AffineTransform defaultAt = g2d.getTransform();
        AffineTransform at = new AffineTransform();
        at.rotate(- Math.PI / 2);
        g2d.setTransform(at);
        g2d.drawString(barChart.getY(), -(getHeight()/2 + 50), 10);
        g2d.setTransform(defaultAt);
        g2d.drawString(barChart.getX(), getWidth()/2 - 40, getHeight() - 10);
        
        int dashesOnYAxis = barChart.getMaxY() / barChart.getGap(); 
        int pixelsBetweenDashes = (getHeight() - 60) / dashesOnYAxis; 
        
        for (int i = 0; i <= dashesOnYAxis; i++) {
        	g2d.drawLine(26, getHeight() - 30 - i * pixelsBetweenDashes, 
        				30, getHeight() - 30 - i * pixelsBetweenDashes);
        	g2d.drawString(String.valueOf(i * barChart.getGap()), 15, getHeight() - 28 - i * pixelsBetweenDashes);
        }
              
        int pixelsForEachValue = (getWidth() - 60) / barChart.getListOValues().size();
        int counter = 0; 
        for (XYValue xyValue : barChart.getListOValues()) {
        	g2d.drawLine(30 + counter * pixelsForEachValue, getHeight() - 30, 30 + counter * pixelsForEachValue, getHeight() - 24);
        	g2d.setColor(Color.PINK);
        	g2d.fillRect(30 + counter * pixelsForEachValue , getHeight() - 30 -  (pixelsBetweenDashes/barChart.getGap()) * xyValue.getY() , pixelsForEachValue - 2, (pixelsBetweenDashes/barChart.getGap()) * xyValue.getY());
        	g2d.setColor(Color.BLACK);
        	g2d.drawString(String.valueOf(xyValue.getX()), 30 + pixelsForEachValue/2 + counter * pixelsForEachValue,  getHeight() - 18);
        	counter++;
        }      
	}
}
