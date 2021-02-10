package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

public class TurtleState {

	private Vector2D currentPosition;
	private Vector2D direction;
	private Color color; 
	private double shift; 
	
	
	
	public TurtleState(Vector2D currentPosition, Vector2D direction, Color color, double shift) {
		super();
		this.currentPosition = currentPosition;
		this.direction = direction;
		this.color = color;
		this.shift = shift;
	}



	public TurtleState copy() {
		return new TurtleState(new Vector2D(currentPosition.getX(), currentPosition.getY()),
								new Vector2D(direction.getX(), direction.getY()), new Color(color.getRGB()),
								Double.valueOf(shift));
	}



	public Vector2D getCurrentPosition() {
		return currentPosition;
	}



	public void setCurrentPosition(Vector2D currentPosition) {
		this.currentPosition = currentPosition;
	}



	public Vector2D getDirection() {
		return direction;
	}



	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}



	public Color getColor() {
		return color;
	}



	public void setColor(Color color) {
		this.color = color;
	}



	public double getShift() {
		return shift;
	}



	public void setShift(double shift) {
		this.shift = shift;
	}
	
	
	
}
