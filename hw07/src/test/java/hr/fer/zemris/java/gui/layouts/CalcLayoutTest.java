package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Container;

import org.junit.jupiter.api.Test;


public class CalcLayoutTest {

	@Test
	public void constraintOutOfBoundsShouldThrow() {
		assertThrows(CalcLayoutException.class, () -> {
			RCPosition position = new RCPosition(-1,8);
			CalcLayout calcLayout = new CalcLayout();
			calcLayout.addLayoutComponent(new Container(), position);
		});
	}
	
	@Test 
	public void firstRowWrongColumnShouldThrow() {
		assertThrows(CalcLayoutException.class, () -> {
			RCPosition position = new RCPosition(1,4);
			CalcLayout calcLayout = new CalcLayout();
			calcLayout.addLayoutComponent(new Container(), position);
		});
	}
	
	@Test
	public void addingSameConstraintsShouldThrow() {
		assertThrows(CalcLayoutException.class, () -> {
			RCPosition position = new RCPosition(2,2); 
			CalcLayout calcLayout = new CalcLayout(); 
			calcLayout.addLayoutComponent(new Container(), position);
			calcLayout.addLayoutComponent(new Container(), position);
		});
	}
}
