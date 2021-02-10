package hr.fer.zemris.java.gui.calc;
 
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class CalcModelImpl implements CalcModel {

	/**
	 * Flag which tells if calculator is editable.
	 */
	private boolean editable = true; 
	
	/**
	 * Flag which tracks whether value if positive.
	 */
	private boolean positive = true; 
	
	/**
	 * Current value in string format.
	 */
	private String stringValue = new String(); 
	
	/**
	 * Current value.
	 */
	private Double value = 0.0;
	
	private String frozenValue; 
	
	/**
	 * First operand, second operand will be value.
	 */
	private Double activeOperand;
	
	/**
	 * Operation which is to be executed.
	 */
	private DoubleBinaryOperator pendingBinaryOperation; 
	
	/**
	 * List which keeps track of all subscribed listeners. 
	 */
	private List<CalcValueListener> listeners = new ArrayList<>();
	
	
	public CalcModelImpl() {
		
	}
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value; 
		if (!positive) {
			this.value *= -1;
		}
		stringValue = String.valueOf(value); 
		editable = false; 
		notifyListeners();
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		value = null;
		stringValue = new String();
		notifyListeners();
		editable = true; 
	}

	@Override
	public void clearAll() {
		value = null; 
		stringValue = new String(); 
		frozenValue = null; 
		activeOperand = null; 
		pendingBinaryOperation = null; 
		notifyListeners();
		editable = true; 
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!editable) throw new CalculatorInputException("Calculator is not editable."); 
		positive = !positive; 
		value *= -1;
		stringValue = String.valueOf(value);
		notifyListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!editable) throw new CalculatorInputException("Calculator is not editable."); 
		if (stringValue.isEmpty() && value == 0) throw new CalculatorInputException("Value must be initialized before decimal point.");
		if (stringValue.contains(".")) throw new CalculatorInputException("Value already contains decimal point.");
		
		if (stringValue.equals("") && value == 0.0) {
			stringValue += "0.";
		} else {
			stringValue += ".";
		}
		
		notifyListeners();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!editable) throw new CalculatorInputException("Calculator is not editable.");
		if (digit > 9 || digit < 0) throw new IllegalArgumentException("Digit must be in range 0-9.");
		if ((value == null || value == 0.0) && digit == 0 && !stringValue.isEmpty() && !stringValue.contains(".")) return;  
		if (stringValue.isEmpty() || stringValue.equals("0")) {
			stringValue = String.valueOf(digit); 
		} else {
			stringValue += String.valueOf(digit);
		}
		value = Double.parseDouble(stringValue); 
		if (value.equals(Double.POSITIVE_INFINITY)) throw new CalculatorInputException("Number is too big.");
		frozenValue = null; 
		notifyListeners();
	}

	@Override
	public boolean isActiveOperandSet() {
		if (activeOperand != null) {
			return true; 
		} 
		return false; 
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveOperandSet()) throw new IllegalStateException("Active operand is not set.");
		
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null; 
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingBinaryOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingBinaryOperation = op; 
	}
	
	/**
	 * Sets frozenValue to given value.
	 * @param value - what frozenValue will be set to
	 */
	public void freezeValue(String value) {
		frozenValue = value; 
	}
	
	/**
	 * Method returns true if frozenValue exists.
	 * 
	 * @return true if frozenValue is not <code>null</code>, false otherwise.
	 */
	public boolean hasFrozenValue() {
		if (frozenValue != null) {
			return true; 
		}
		return false; 
	}
	
	public String toString() {
		if (frozenValue != null) {
			return frozenValue;					
		} else {
			if (value == null || value == 0.0) {
				return positive ? "0" : "-0"; 
			}
			if (stringValue.endsWith(".0")) {
				return stringValue.substring(0, stringValue.length()-2);
			}
			
			return stringValue;
		}
	}
	
	/**
	 * Method notifies all listeners that a change occured.
	 */
	public void notifyListeners() {
		for (CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}

}
