package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Demo class. Gets path to config file from command line arguments
 * which contains description of BarChart, then draws it. 
 *
 */
public class BarChartDemo extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static JLabel pathLabel; 

	/**
	 * Constructor.
	 */
	public BarChartDemo() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(pathLabel, BorderLayout.PAGE_START);
		setSize(400, 400); 
		setTitle("Bar Chart Demo"); 
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
	}

	/**
	 * Main method.
	 * @param args - first argument is path to config file
	 */
	public static void main(String[] args) {
	
		Path path = Paths.get(args[0]); 
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String values = lines.get(2); 
		String[] strs = values.split("\\s+"); 
		
		pathLabel = new JLabel(path.toAbsolutePath().toString()); 
		pathLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		List<XYValue> xyValues = new ArrayList<>();
		for (String str : strs) {
			xyValues.add(new XYValue(Integer.parseInt(str.split(",")[0]), Integer.parseInt(str.split(",")[1])));
		}
		
		BarChart barChart = new BarChart(xyValues, lines.get(0), lines.get(1),
									Integer.parseInt(lines.get(3)), Integer.parseInt(lines.get(4)),
									Integer.parseInt(lines.get(5)));
		
		BarChartComponent barChartComponent = new BarChartComponent(barChart);
		 
		
		SwingUtilities.invokeLater(() -> {
			BarChartDemo barChartDemo = new BarChartDemo();
			barChartDemo.add(barChartComponent);
			barChartDemo.setVisible(true);
		});
	}
	
	
	
}
