package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *  Class for displaying current value of calculator.
 */
public class CalculatorDisplay extends JLabel implements CalcValueListener {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	public CalculatorDisplay() {
		setHorizontalAlignment(SwingConstants.RIGHT);
		setBackground(Color.YELLOW);
		setOpaque(true);
		setFont(getFont().deriveFont(30f));
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
	}

	@Override
	public void valueChanged(CalcModel model) {
		setText(model.toString());
	}
}
