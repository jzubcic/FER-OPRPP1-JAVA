package hr.fer.oprpp1.math;

public class Vector2D {

	private double x; 
	private double y; 
	
	public Vector2D(double x, double y) {
		this.x = x; 
		this.y = y; 
	}
	
	/**
	 * Returns x component of vector.
	 * @return x component of vector
	 */
	public double getX() {
		return x; 
	}
	
	/**
	 * Returns y component of vector.
	 * @return y component of vector
	 */
	public double getY() {
		return y; 
	}
	
	/**
	 * Performs addition of given vector to curent vector.
	 * @param offset vector to be added
	 */
	public void add(Vector2D offset) {
		this.x += offset.x; 
		this.y += offset.y; 
	}
	
	/**
	 * Returns new vector which is product of addition of <code>offset</code> 
	 * to current vector
	 * @param offset vector to be added 
	 * @return product of addition
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(this.x + offset.x, this.y + offset.y); 
	}
	
	/**
	 * Performs rotation for given angle on vector.
	 * @param angle angle by which the vector will be rotated
	 */
	public void rotate(double angle) {
		double x1 = this.x; 
		double y1 = this.y; 
		this.x = x1*Math.cos(angle) - y1*Math.sin(angle);
		this.y = x1*Math.sin(angle) + y1*Math.cos(angle); 
	}
	
	/**
	 * Returns new vector which is the product of rotation by given angle
	 * on current vector.
	 * @param angle angle by which the vector will be rotated
	 * @return vector product of rotation
	 */
	public Vector2D rotated(double angle) {
		return new Vector2D (this.x*Math.cos(angle) - this.y*Math.sin(angle),
								this.x*Math.sin(angle) + this.y*Math.cos(angle));			
	}
	
	/**
	 * Performs scaling by given scaler on current vector.
	 * @param scaler scaler which will scale vector
	 */
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler; 
	}
	
	/**
	 * Returns new vector which is the product of scaling current vector
	 * by given scaler.
	 * @param scaler scaler which will scale vector
	 * @return vector product of scaling
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.x * scaler, this.y * scaler); 
	}
	
	/**
	 * Returns new vector which is equal to current one.
	 * @return copy of current vector
	 */
	public Vector2D copy() {
		return new Vector2D(x, y); 
	}
}
