package hr.fer.oprpp1.custom.collections;

public interface ElementsGetter<T> {
	
	public boolean hasNextElement();
	
	public T getNextElement();
	
	public default void processRemaining(Processor p) {
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
