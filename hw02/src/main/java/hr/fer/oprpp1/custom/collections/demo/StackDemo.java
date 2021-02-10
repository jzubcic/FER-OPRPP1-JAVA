package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {

	public static void main(String[] args) {
		ObjectStack stack = new ObjectStack(); 
		String[] input = args[0].split("\\s+"); 
		int operand1, operand2;
		
		for (String s : input) {
			System.out.println(s);
			if (isNumber(s)) {
				System.out.println("Usao sam ovdje :)");
				stack.push(Integer.parseInt(s));
			} else {
				operand2 = (int) stack.pop();
				operand1 = (int) stack.pop();
				
				switch (s) {
					case "+":
						stack.push(operand1 + operand2); 
						break;
					case "-":
						stack.push(operand1 - operand2);
						break;
					case "/":
						if (operand2 == 0) throw new ArithmeticException("Cannot divide by zero.");
						stack.push(operand1 / operand2);
						break; 
					case "*":
						stack.push(operand1 * operand2);
						break;
					case "%":
						if (operand2 == 0) throw new ArithmeticException("Cannot divide by zero.");
						stack.push(operand1 % operand2);
						break;					
				}
			}
		}
		
		if (stack.size() != 1) {
			System.err.println("Error.");
		} else {
			System.out.println("Expression evaluates to " + stack.pop());
		}		
	}
	
	public static boolean isNumber(String s) {
		if (s == null) return false; 
		try {
			@SuppressWarnings("unused")
			int n = Integer.parseInt(s);
		} catch (NumberFormatException ex) {
			return false; 
		}
		return true; 
	}
}
