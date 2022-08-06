package application;

public class Point {
	private double x, y;
	
	/**
	 * Point constructor. Sets the x and y value of the point.
	 * @param setX - x coordinate of point to set
	 * @param setY - y coordinate of point to set
	 */
	Point(double setX, double setY){
		x = setX;
		y = setY;
	}
	
	/**
	 * @return x coordinate value of Point object
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x coordinate value of Point object to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return y coordinate value of Point object
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y coordinate value of Point Object to set
	 */
	public void setY(double y) {
		this.y = y;
	}
	
}
