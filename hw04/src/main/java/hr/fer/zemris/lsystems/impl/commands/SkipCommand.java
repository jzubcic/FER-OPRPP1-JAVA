package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;

public class SkipCommand implements Command {

	private double step; 
	
	public SkipCommand(double step) {
		this.step = step; 
	}
	
	/**
	 * Calculates new TurtleState position
	 * and updates it.
	 * @param ctx stack from which TurtleState will be acquired
	 * @param painter
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		Vector2D vector = state.getDirection().scaled(state.getShift() * step);
		
		state.setCurrentPosition(new Vector2D(state.getCurrentPosition().getX() + vector.getX(),
											  state.getCurrentPosition().getY() + vector.getY())); 
	}

}
