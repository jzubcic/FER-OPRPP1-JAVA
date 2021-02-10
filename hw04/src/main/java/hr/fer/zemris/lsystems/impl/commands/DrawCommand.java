package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;

public class DrawCommand implements Command {

	private double step; 
	
	public DrawCommand(double step) {
		this.step = step; 
	}
	
	/**
	 * Calculates new TurtleState position, draws the line
	 * and updates TurtleState's position
	 * @param ctx stack from which TurtleState will be acquired
	 * @param painter Painter which will be used for drawing
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		Vector2D vector = state.getDirection().scaled(state.getShift() * step);
		
		painter.drawLine(state.getCurrentPosition().getX(),
						 state.getCurrentPosition().getY(),
						 state.getCurrentPosition().getX() + vector.getX(), 
						 state.getCurrentPosition().getY() + vector.getY(), 
						 state.getColor(), 1f);
		
		state.setCurrentPosition(new Vector2D(state.getCurrentPosition().getX() + vector.getX(),
											  state.getCurrentPosition().getY() + vector.getY())); 
	}

}
