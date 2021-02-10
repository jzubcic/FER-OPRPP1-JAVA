package hr.fer.zemris.java.gui.calc;

import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Main class, used for starting Calculator frame.
 *
 */
public class CalculatorFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 *  Checkbox which defines if certain operations will be regular
	 *  or inverted.
	 */
	public static JCheckBox invertedBox = new JCheckBox("Inv");

	/**
	 *  Constructor, initializes GUI. 
	 */
	public CalculatorFrame() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		setSize(600, 400);
	}
	
	/**
	 *  Method which initializes GUI; defines the layout for calculator, creates model of calculator 
	 *  and adds all buttons to calculator.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(0));
		
		CalcModelImpl model = new CalcModelImpl(); 
		
		
		CalculatorDisplay calcDisplay = new CalculatorDisplay();
		model.addCalcValueListener(calcDisplay);
		cp.add(calcDisplay, new RCPosition(1,1)); 
		
		
		 
		cp.add(invertedBox, new RCPosition(5,7)); 
		
		
		
		String[] rcPositions = {
				"5,3", "4,3", "4,4", "4,5",
				"3,3", "3,4", "3,5", "2,3",
				"2,4", "2,5"
		};
		 
		int n = 0; 
		for (String rcPosition : rcPositions) {
			cp.add(new NumberButton(n++, model), RCPosition.parse(rcPosition));
		}
		
		List<UnaryOperationButton> unaryButtons = new ArrayList<>();
		
		UnaryOperationButton sinButton = new UnaryOperationButton("sin", "arcsin", model, (number) -> {
													return Math.sin(number); 
													});
		unaryButtons.add(sinButton); 
		UnaryOperationButton cosButton = new UnaryOperationButton("cos", "arccos", model, (number) -> {
													return Math.cos(number); 
													});
		unaryButtons.add(cosButton); 
		UnaryOperationButton tanButton = new UnaryOperationButton("tan", "arctan", model, (number) -> {
													return Math.tan(number); 
													});
		
		unaryButtons.add(tanButton); 
		UnaryOperationButton ctgButton = new UnaryOperationButton("ctg", "arcctg", model, (number) -> {
													return 1 / Math.tan(number); 
													});
		
		unaryButtons.add(ctgButton); 
		UnaryOperationButton logButton = new UnaryOperationButton("log", "10^x", model, (number) -> {
													return Math.log10(number); 
													});
		
		unaryButtons.add(logButton); 
		
		UnaryOperationButton lnButton = new UnaryOperationButton("ln", "e^x", model, (number) -> {
													return Math.log(number); 
													});
		unaryButtons.add(lnButton); 
		
		JButton oneOverXButton = new JButton("1/x"); 
		oneOverXButton.addActionListener(l -> model.setValue(1/model.getValue()));
		
		
														
		
		GenericButton swapButton = new GenericButton("+/-", () -> model.swapSign());
		GenericButton pointButton = new GenericButton(".", () -> model.insertDecimalPoint());
		GenericButton clrButton = new GenericButton("clr", () -> {
														model.clear();
														model.freezeValue(null);
														});
		GenericButton resetButton = new GenericButton("reset", () -> model.clearAll()); 
		GenericButton equalsButton = new GenericButton("=", () -> model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue())));
	
		BinaryOperationButton divButton = new BinaryOperationButton("/", model, (operand1, operand2) -> {
													return operand1/operand2;	
													});
		BinaryOperationButton mulButton = new BinaryOperationButton("*", model, (operand1, operand2) -> {
													return operand1*operand2;	
													});
		BinaryOperationButton subButton = new BinaryOperationButton("-", model, (operand1, operand2) -> {
													return operand1-operand2;	
													});
		BinaryOperationButton addButton = new BinaryOperationButton("+", model, (operand1, operand2) -> {
													return operand1+operand2;	
													});
		BinaryOperationButton powButton = new BinaryOperationButton("x^n", model, (operand1, operand2) -> {
													return !invertedBox.isSelected() ? Math.pow(operand1, operand2) : Math.pow(operand1, 1/operand2);
													});
		
		
		
		invertedBox.addActionListener(l -> {
			for (UnaryOperationButton uob : unaryButtons) {
				uob.rename();
			}
			powButton.setText(powButton.getText().equals("x^n") ? "x^(1/n)" : "x^n");
		});

		Stack<Double> stack = new Stack<>(); 
		
		JButton pushButton = new JButton("Push"); 
		pushButton.addActionListener(l -> stack.push(model.getValue()));
		
		JButton popButton = new JButton("pop");
		popButton.addActionListener(l -> model.setValue(stack.pop()));
		
		cp.add(oneOverXButton, new RCPosition(2, 1)); 
		cp.add(powButton, new RCPosition(5, 1)); 
		
		cp.add(sinButton, new RCPosition(2,2)); 
		cp.add(cosButton, new RCPosition(3,2)); 
		cp.add(tanButton, new RCPosition(4, 2)); 
		cp.add(ctgButton, new RCPosition(5, 2)); 
		cp.add(logButton, new RCPosition(3,1)); 
		cp.add(lnButton, new RCPosition(4,1));
		
		cp.add(swapButton, new RCPosition(5, 4));
		cp.add(pointButton, new RCPosition(5, 5)); 
		
		cp.add(divButton, new RCPosition(2, 6));
		cp.add(mulButton, new RCPosition(3, 6));
		cp.add(subButton, new RCPosition(4, 6));
		cp.add(addButton, new RCPosition(5, 6));
		
		cp.add(clrButton, new RCPosition(1, 7)); 
		cp.add(resetButton, new RCPosition(2, 7));
		cp.add(equalsButton, new RCPosition(1, 6)); 
		
		cp.add(pushButton, new RCPosition(3, 7)); 
		cp.add(popButton, new RCPosition(4, 7)); 
		
	}
	
	/**
	 * Main method.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new CalculatorFrame().setVisible(true);
		});
	}
}
