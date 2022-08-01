package application;

public class Point {
	private double x, y;
	
	
	/**
	 * Point constructor. Sets the x and y value of the point.
	 * @param setX
	 * @param setY
	 */
	Point(double setX, double setY){
		x = setX;
		y = setY;
	}
	
	/**
	 * Uses the average x and y coordinate from p1 and p2 to create a new point.
	 * Sets coordinates to xBound/yBound if they do not fall within those values (causing them to not appear in canvas)
	 * @param p1 - first point
	 * @param p2 - second point
	 * @param xBound - maximum x value
	 * @param yBound - minimum y value
	 * @return new point object
	 */
	static Point solveMidpoint(Point p1, Point p2, int xBound, int yBound) {
		double newX = (p1.getX()+p2.getX())/2;
		double newY = (p1.getY()+p2.getY())/2;
		double x = (newX > xBound) ? xBound : newX;
		double y = (newY < yBound) ? yBound : newY;
		Point point = new Point(x,y);
		return point;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
}
