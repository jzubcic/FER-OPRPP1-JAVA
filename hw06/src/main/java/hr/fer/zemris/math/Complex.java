package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

public class Complex {

	public static final Complex ZERO = new Complex(0,0);
	public static final Complex ONE = new Complex(1,0);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex IM = new Complex(0,1);
	public static final Complex IM_NEG = new Complex(0,-1);
	
	private double re; 
	private double im; 
	
	public Complex() {
		
	}
	
	public Complex(double re, double im) {
		this.re = re; 
		this.im = im;
	}
	
	public double getRe() {
		return re; 
	}
	
	public double getIm() {
		return im; 
	}
	
	// returns module of complex number
	public double module() {
		return Math.abs(Math.sqrt(re * re + im * im));
	}
	
	// returns this*c
	public Complex multiply(Complex c) {
		return new Complex(this.re * c.re - this.im * c.im,
						this.im * c.re + this.re * c.im);
	}
	
	public double getAngle() {
		return Math.atan2(im, re);
	}
	
	// returns this/c
	public Complex divide(Complex c) {
		return new Complex((this.re * c.re + this.im * c.im) 
				/ (c.re * c.re + c.im * c.im),
				(this.im * c.re - this.re * c.im)
				/ (c.re * c.re + c.im * c.im));
	}
	
	// returns this+c
	public Complex add(Complex c) {
		return new Complex(this.re + c.getRe(), this.im + c.getIm());
	}
	
	// returns this-c
	public Complex sub(Complex c) {
		return new Complex(this.re - c.getRe(), this.im - c.getIm()); 
	}
	
	// returns -this
	public Complex negate() {
		return new Complex(-this.re, -this.im);
	}
	
	// returns this^n, n is non-negative integer
	public Complex power(int n) {
		if (n < 0) throw new IllegalArgumentException("Power n must be non-negative."); 
        
        return new Complex(Math.pow(module(), n), 0).
        		multiply(new Complex(Math.cos(n*getAngle()), Math.sin(n*getAngle())));
	}
	
	// returns n-th root of this, n is positive integer
	public List<Complex> root(int n) {
		if (n <= 0) throw new IllegalArgumentException("n must be greater than zero");
		List<Complex> list = new ArrayList<>();
		
		for (int i = 0; i < n; i++) {
			list.add(new Complex(Math.pow(module(), 1/(float)n) * Math.cos((getAngle() + 2*i*Math.PI)/n), 
								 Math.pow(module(), 1/(float)n) * Math.sin((getAngle() + 2*i*Math.PI)/n)));
		}
		return list;		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("("); 
		sb.append(re);
		
		if (im >= 0) {
			sb.append("+");
		} else {
			sb.append("-");
		}
		sb.append("i" + Math.abs(im));
		sb.append(")");
		return sb.toString();
	}

}
