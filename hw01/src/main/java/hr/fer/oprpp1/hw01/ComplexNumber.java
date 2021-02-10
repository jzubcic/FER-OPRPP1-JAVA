package hr.fer.oprpp1.hw01;

public class ComplexNumber {

	private double real; 
	private double imaginary; 
	
	public ComplexNumber(double real, double imaginary) {
		this.real = real; 
		this.imaginary = imaginary; 
	}
	
	public static ComplexNumber fromReal (double real) {
		return new ComplexNumber(real, 0);
	}
	
	public static ComplexNumber fromImaginary (double imaginary)  {
		return new ComplexNumber(0,imaginary); 
	}
	
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		double re = magnitude * Math.cos(angle); 
		double im = magnitude * Math.sin(angle);
		return new ComplexNumber(re, im); 
	}
	

	public static ComplexNumber parse (String s) {
		if (!s.contains("i")) {
			try {
				double real = Double.parseDouble(s); 
				return fromReal(real); 
			} catch (NumberFormatException ex) {
				System.err.println("Input is invalid.");
			}
		}
		
		if (s.equals("i")) 
			return new ComplexNumber(0, 1);
		if (s.equals("-i"))
			return new ComplexNumber(0, -1); 
		
		String broj = new String(); 
		double realni = 0; 
		double imaginarni = 0; 
		int brojac = 0;  
		char[] array = s.toCharArray(); 
		for (char c : array) {
			if (String.valueOf(c).equals("-")) {
				if (brojac == 1) {
					realni = Double.parseDouble(broj);
					broj = ""; 
				}				
				broj += "-"; 				
				brojac++;
			}
			if (String.valueOf(c).equals("+")) {
				if (brojac == 1) {
					realni = Double.parseDouble(broj);
					broj = ""; 
				}
				brojac++;
			}
			if (String.valueOf(c).equals("i")) {
				imaginarni = Double.parseDouble(broj); 
			}
			if (Character.isDigit(c) || String.valueOf(c).equals(".")) {
				broj += c;
				if (brojac == 0) {
					brojac = 1; 
				}
			}
		}
		return new ComplexNumber(realni, imaginarni); 
	}
	
	/**
	 * Returns real part of complex number.
	 * @return real part 
	 */
	public double getReal() {
		return real; 
	}
	
	/**
	 * Returns imaginary part of complex number.
	 * @return imaginary part
	 */
	public double getImaginary() {
		return imaginary; 
	}
	
	
	/**
	 * Returns magnitude of complex number.
	 * @return magnitude
	 */
	public double getMagnitude() {
		return Math.sqrt(real * real + imaginary * imaginary); 
	}
	
	/**
	 * Returns angle of complex number.
	 * @return angle
	 */
	public double getAngle() {
		return Math.atan(imaginary / real); 
	}
	
	/**
	 * Adds complex number c to current complex number and returns the result. 
	 * @param c complex number to be added 
	 * @return complex number which is result of addition
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}
	
	/**
	 * Subtracts complex number c from current complex number.
	 * @param c complex number to be subtracted
	 * @return complex number which is result of subtraction
	 */
	public ComplexNumber sub (ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}
	
	/**
	 * Multiplies complex number c with current complex number.
	 * @param c complex number with which to multiply
	 * @return complex number which is result of multiplication
	 */
	public ComplexNumber mul (ComplexNumber c) {
		return new ComplexNumber(this.real * c.real - this.imaginary * c.imaginary,
				this.imaginary * c.real + this.real * c.imaginary);
	}
	
	/**
	 * Divides complex number with complex number c.
	 * @param c complex number to be divided with.
	 * @return result of multiplication as new ComplexNumber
	 */
	public ComplexNumber div (ComplexNumber c) {
		return new ComplexNumber((this.real * c.real + this.imaginary * c.imaginary) 
								/ (c.real * c.real + c.imaginary * c.imaginary),
								(this.imaginary * c.real - this.real * c.imaginary)
								/ (c.real * c.real + c.imaginary * c.imaginary));
	}	
	
	/**
	 * Returns complex number to the power of n as new ComplexNumber. 
	 * @param n power to be used in calculation
	 * @return result of applying power of n to complex number
	 */
	public ComplexNumber power (int n) {
		if (n < 0) throw new UnsupportedOperationException("Power must be zero or greater.");
		
		return new ComplexNumber(Math.pow(getMagnitude(), n) * Math.cos(getAngle() * n), 
									Math.pow(getMagnitude(), n) * Math.sin(getAngle() * n)); 
	}
	
	/**
	 * Returns nth roots of complex number as array of ComplexNumbers.
	 * @param n number of roots
	 * @return roots of complex number
	 * @throws IllegalArgumentException if n is zero or less
	 */
	public ComplexNumber[] root (int n) {
		if (n <= 0) throw new IllegalArgumentException("Argument must be greater than zero");
		ComplexNumber[] array = new ComplexNumber[n-1];  
		for (int i = 0; i < n - 1; i++) {
		array[i] = new ComplexNumber(Math.pow(getMagnitude(), 1/(float)n) * Math.cos((getAngle() + 2*i*Math.PI)/n), 
										Math.pow(getMagnitude(), 1/(float)n) * Math.sin((getAngle() + 2*i*Math.PI)/n));
		}
		return array; 
	}
	
	/**
	 * Returns complex number in string format. 
	 * @return string notation of complex number
	 */
	public String toString() {
		String str = new String(); 
		if (real != 0) {
			str += real; 
		}
		if (imaginary != 0) {
			if (imaginary > 0) {
				str += "+";
			} 
			str += imaginary;
			str += "i";
		}		 
		return str;
	}
}
