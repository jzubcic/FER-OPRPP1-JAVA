package hr.fer.zemris.math;

public class ComplexPolynomial {

	private Complex[] factors; 

	public ComplexPolynomial(Complex ...factors) {
		this.factors = factors; 
	}
	
	
	// returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	public short order() {
		return (short) (factors.length - 1);
	}
	
	// computes a new polynomial this*p
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		int n = this.order() + p.factors.length; 
		Complex[] factorsArray = new Complex[n];
		for (int i = 0; i < n; i++) {
			factorsArray[i] = Complex.ZERO;
		}
		
		for (int i = 0; i <= this.order(); i++) {
			for (int j = 0; j <= p.order(); j++) {
				factorsArray[i + j] = factorsArray[i + j].add(this.factors[i].multiply(p.factors[j])); 
			}
		}
		
		return new ComplexPolynomial(factorsArray);
	}
	
	// computes first derivative of this polynomial; for example, for
	// (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[this.order()]; 
		
		for (int i = 0; i < this.order(); i++) {
			newFactors[i] = this.factors[i + 1].multiply(new Complex(i + 1, 0));
		}
		
		return new ComplexPolynomial(newFactors);
	}
	
	// computes polynomial value at given point z
	public Complex apply(Complex z) {
		Complex c = factors[0];
		for (int i = 1; i < factors.length; i++) { 
			c = c.add(factors[i].multiply(z.power(i)));
		}
		return c; 
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = factors.length - 1; i > 0; i--) {
			sb.append(factors[i] + "*z^" + i);
			if (i >= 1) sb.append("+"); 
		}
		sb.append(factors[0]); 
		return sb.toString();
	}
}
