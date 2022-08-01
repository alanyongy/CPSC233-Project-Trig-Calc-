package application;

public class Point {
	private double x, y;
	
	Point(double setX, double setY){
		x = setX;
		y = setY;
	}
	
	
	/**
	 * Point constructor. Uses the average x and y coordinate from the two points.
	 * Sets coordinates to xBound/yBound if they do not fall within those values (causing them to not appear in canvas)
	 * @param p1 - Point object 1
	 * @param p2 - Point object 2
	 * @param xBound - maximum x coordinate
	 * @param yBound - minimum y coordinate
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
