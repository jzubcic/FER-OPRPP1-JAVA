package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

public interface Command {

	public void execute(Context ctx, Painter painter); 
}
