package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.Newton.MojProducer;
import hr.fer.zemris.java.fractals.NewtonParallel.PosaoIzracuna;
import hr.fer.zemris.java.fractals.mandelbrot.Mandelbrot;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class NewtonParallel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int workers = Runtime.getRuntime().availableProcessors(); 
		int tracks = 4 *  workers; 
		switch(args.length) {
			case 0: 
				//koristi defaultne vrijednosti
				break; 
			case 1:
				//--workers=2
				//--tracks=4
				if (args[0].startsWith("--workers")) {
					workers = Integer.parseInt(args[0].split("=")[1]); 
				}
				if (args[0].startsWith("--tracks")) {
					tracks = Integer.parseInt(args[0].split("=")[1]); 
				}
				break;
			case 2: 
				//--workers=2 --tracks=10
				if (args[0].startsWith("--workers")) {
					workers = Integer.parseInt(args[0].split("=")[1]); 
				}
				if (args[0].startsWith("--tracks")) {
					tracks = Integer.parseInt(args[0].split("=")[1]); 
				}
				
				if (args[1].startsWith("--workers")) {
					workers = Integer.parseInt(args[1].split("=")[1]); 
				}
				if (args[1].startsWith("--tracks")) {
					tracks = Integer.parseInt(args[1].split("=")[1]); 
				}
				//-t K 
				if (args[0].equals("-t")) {
					tracks = Integer.parseInt(args[1]); 
				}
				//-w K
				if (args[0].equals("-w")) {
					workers = Integer.parseInt(args[1]); 
				}
				break;
			case 4: 
				//-w K -t J
				if (args[0].equals("-w")) {
					workers = Integer.parseInt(args[1]);
				}		
				if (args[0].equals("-t")) {
					tracks = Integer.parseInt(args[1]);
				}			
				if (args[2].equals("-w")) {
					workers = Integer.parseInt(args[3]);
				}
				if (args[2].equals("-t")) {
					tracks = Integer.parseInt(args[3]);
				}			
		}
		
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
		
		FractalViewer.show(new MojProducer(new ComplexRootedPolynomial(Complex.ONE, factors), workers, tracks));	
		
	}

	public static class PosaoIzracuna implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();
		
		private PosaoIzracuna() {
		}
		
		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
		}
		
		@Override
		public void run() {
			NewtonParallel.produce(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data, cancel);		
		}
			
	}
	
	public static void produce(double reMin, double reMax, double imMin, double imMax, 
			int width, int height, int yMin, int yMax, short[] data, AtomicBoolean cancel) {
		
		int offset = yMin * width;
		
		double convergenceTreshold = 0.002;
		int maxIter = 16 * 16;
		double rootThreshold = 0.001;
		for(int y = yMin; y <= yMax; y++) {
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
					numerator = MojProducer.polynomial.apply(zn);
					denominator = MojProducer.derived.apply(zn);
					znold = zn;
					fraction = numerator.divide(denominator);
					zn = zn.sub(fraction);
					module = znold.sub(zn).module();
					iters++;
				} while(module > convergenceTreshold && iters < maxIter);
				 int index = MojProducer.compRootPoly.indexOfClosestRootFor(zn, rootThreshold);
				 data[offset++]=(short) (index+1);
			}
		}

		//System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
		//observer.acceptResult(data, (short)(MojProducer.polynomial.order()+1), requestNo);
	}
	
	
	
	public static class MojProducer implements IFractalProducer {
		
		public static ComplexRootedPolynomial compRootPoly;
		public static ComplexPolynomial polynomial; 
		public static ComplexPolynomial derived; 
		public int workers;
		public int tracks; 
		
		public MojProducer(ComplexRootedPolynomial compRootPoly, int workers, int tracks) {
			this.compRootPoly = compRootPoly;
			polynomial = compRootPoly.toComplexPolynom();
			derived = polynomial.derive();
			this.workers = workers;
			this.tracks = tracks; 
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int m = 16*16*16;
			short[] data = new short[width * height];
			final int brojTraka = tracks; //broj traka
			int brojYPoTraci = height / brojTraka;
			System.out.println("Broj dretava je: " + workers + ", a broj traka je: " + brojTraka);
			
			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

			Thread[] radnici = new Thread[workers]; //broj radnika
			for(int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if(p==PosaoIzracuna.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for(int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}
			
			for(int i = 0; i < brojTraka; i++) { 
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci-1;
				if(i==brojTraka-1) {
					yMax = height-1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
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
