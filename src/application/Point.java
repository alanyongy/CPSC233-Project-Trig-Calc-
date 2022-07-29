package application;

public class Point {
	private double x, y;
	
	Point(double setX, double setY){
		x = setX;
		y = setY;
	}
	
	Point(Point p1, Point p2, int xBound, int yBound) {
		double newX = (p1.getX()+p2.getX())/2;
		double newY = (p1.getY()+p2.getY())/2;
		x = (newX > xBound) ? xBound : newX;
		y = (newY > yBound) ? yBound : newY;
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
