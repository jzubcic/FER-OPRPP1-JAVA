package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class Newton {

	
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		Scanner sc = new Scanner(System.in);
		
		int counter = 1;
		List<String> input = new ArrayList<>(); 
		String line;
		System.out.print("Root " + counter +"> ");
		while (!(line = sc.nextLine()).equals("done")) {
			input.add(line);
			System.out.print("Root " + ++counter +"> ");
		}
		
		Complex[] factors = new Complex[input.size()];
		int i = 0; 
		for (String s : input) {
			factors[i++] = parse(s); 
		}
		sc.close();
		
		FractalViewer.show(new MojProducer(new ComplexRootedPolynomial(Complex.ONE, factors)));	
	}

	public static class MojProducer implements IFractalProducer {
		
		private ComplexRootedPolynomial compRootPoly;
		private ComplexPolynomial polynomial; 
		private ComplexPolynomial derived; 
		
		public MojProducer(ComplexRootedPolynomial compRootPoly) {
			this.compRootPoly = compRootPoly;
			polynomial = compRootPoly.toComplexPolynom();
			derived = polynomial.derive();
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			System.out.println("CRP je: " + compRootPoly);
			System.out.println("Polynomial je: " + polynomial);
			System.out.println("Derived je: " + derived);
			int offset = 0;
			short[] data = new short[width * height];
			double convergenceTreshold = 0.002;
			int maxIter = 16 * 16;
			double rootThreshold = 0.001;
			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					double cre = x / (width-1.0) * (reMax - reMin) + reMin;
					double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
					double module = 0;
					int iters = 0;
					Complex zn = new Complex(cre, cim); 
					Complex znold;
					Complex numerator; 
					Complex denominator;
					Complex fraction;
					do {				
						numerator = polynomial.apply(zn);
						denominator = derived.apply(zn);
						znold = zn;
						fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iters++;
					} while(module > convergenceTreshold && iters < maxIter);
					 int index = compRootPoly.indexOfClosestRootFor(zn, rootThreshold);
					 data[offset++]=(short) (index+1);
				}
			}

			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order()+1), requestNo);
		}
	}
	
	public static Complex parse(String s) {
		if (!s.contains("i")) {
			try {
				double real = Double.parseDouble(s); 
				return new Complex(real, 0); 
			} catch (NumberFormatException ex) {
				System.err.println("Input is invalid.");
			}
		}
		
		if (s.equals("i")) 
			return new Complex(0, 1);
		if (s.equals("-i"))
			return new Complex(0, -1); 
		
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
		return new Complex(realni, imaginarni);
	}
}
