package hr.fer.oprpp1.custom.collections;


public class EvenIntegerTester implements Tester {
	public EvenIntegerTester() {
		
	}
	
	public boolean test(Object obj) {
		 if(!(obj instanceof Integer)) return false;
		 Integer i = (Integer)obj;
		 return i % 2 == 0;
	}
}
