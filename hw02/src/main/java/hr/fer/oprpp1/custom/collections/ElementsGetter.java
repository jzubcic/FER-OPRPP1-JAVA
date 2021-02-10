package hr.fer.oprpp1.custom.collections;

public interface ElementsGetter {
	
	public boolean hasNextElement();
	
	public Object getNextElement();
	
	public default void processRemaining(Processor p) {
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
