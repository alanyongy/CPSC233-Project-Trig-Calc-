package application;

import java.text.DecimalFormat;

public class Calc {
	static Triangle solveValues(double h, double o, double a, double t, boolean d){
		String solveMethod = "";
		String angleMode = d ? "°" : "rad";
		
		if(d) t = Math.toRadians(t);
		
		if(h!=0 && o!=0) {
			t = Math.asin(o/h);
			solveMethod += "θ = aSin(o/h) \nRearranged from: sinθ = o/h";
			a = Math.sqrt(h*h - o*o);
		} else if(h!=0 && a!=0) {
			t = Math.acos(a/h);
			solveMethod += "θ = aCos(a/h) \nRearranged from: cosθ = a/h";
			o = Math.sqrt(h*h - a*a);
		} else if(h!=0 && t!=0) {
			o = h*Math.sin(t);
			a = h*Math.cos(t);
			solveMethod += "o = h*sin(θ) \nRearranged from: sinθ = o/h \n\nFormula Used: a = h*cos(θ) \nRearranged from: cosθ = a/h";
		} else if(o!=0 && a!=0) {
			t = Math.atan(o/a);
			solveMethod += "θ = aTan(o/a) \nRearranged from: tanθ = o/a";
			h = Math.sqrt(a*a + o*o);
		} else if(o!=0 && t!=0) {
			a = o/Math.tan(t);
			solveMethod += "a = o/tan(θ) \nRearranged from: tanθ = o/a";
			h = Math.sqrt(a*a + o*o);
		} else if(a!=0 && t!=0) {
			o = a*Math.tan(t);
			solveMethod += "o = a*tan(θ) \nRearranged from: tanθ = o/a";
			h = Math.sqrt(a*a + o*o);
		}
		
		if(d) t = Math.toDegrees(t);
		
		Triangle triangle = new Triangle(h,o,a, format(h), format(o), format(a), format(t) + angleMode, solveMethod);
		return triangle;
	}
	
	static String format(double n){
		DecimalFormat dec2 = new DecimalFormat("#0.00");
		//DecimalFormat dec0 = new DecimalFormat("#0");
		//return ((Double.toString(n).length() - Double.toString(n).indexOf('.')) > 2) ? dec2.format(n) : dec0.format(n);
		return dec2.format(n);
	}
	
	static Point createMidpoint(Point p1, Point p2) {
		Point midPoint = new Point((p1.getX()+p2.getX())/2, (p1.getY()+p2.getY())/2);
		if (midPoint.getY() < 10) midPoint.setY(10);
		if (midPoint.getX() > 335) midPoint.setX(335);
		return midPoint;
	}
	
	static void moveOverlappingPoints(Point h, Point o, Point a, Point t) {
		Point[] p = {h,o,a,t};
		int marginY = 15;
		int marginX = 50;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(j==i) continue;
				if(Math.abs(p[i].getY() - p[j].getY()) < marginY && Math.abs(p[i].getX() - p[j].getX()) < marginX) {
					if(p[i].getY() <= p[j].getY()) p[j].setY(p[j].getY() + marginY + 2 - (p[j].getY()-p[i].getY()));
					else p[i].setY(p[i].getY() + marginY + 2 - (p[i].getY()-p[j].getY()));
				}
			}
		}
	}
}
