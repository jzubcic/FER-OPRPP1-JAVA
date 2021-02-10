package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

public class ComplexRootedPolynomial {
	
	private Complex constant; 
	private Complex[] roots; 

	// constructor
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = roots; 
	}
	
	// computes polynomial value at given point z
	public Complex apply(Complex z) {
		Complex result = constant; 
		for (Complex c : roots) {
			result = result.multiply(z.sub(c)); 
		}
		return result; 
	}
	
	// converts this representation to ComplexPolynomial type
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial compPoly = new ComplexPolynomial(this.constant); 
		
		for (int i = 0; i < roots.length; i++) {
			compPoly = compPoly.multiply(new ComplexPolynomial(roots[i], Complex.ONE));
		}
		return compPoly;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(constant);
		for (Complex c : roots) {
			sb.append("*(z-" + c + ")");
		}
		return sb.toString();
	}
	
	// finds index of closest root for given complex number z that is within
	// threshold; if there is no such root, returns -1
	// first root has index 0, second index 1, etc
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int indMin = 0; 
		for (int i = 0; i < roots.length; i++) {
			if (roots[i].sub(z).module() < roots[indMin].sub(z).module()) {
				indMin = i; 
			}
		}
		
		
		if (roots[indMin].sub(z).module() > treshold) {	
			return -1;
		} else {
			return indMin;
		}
     
	}
}
