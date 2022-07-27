package application;

import java.text.DecimalFormat;

public class Calc {
	static Triangle solveValues(double h, double o, double a, double t, boolean d){
		String formula = "";
		String angleMode = d ? "°" : "rad";
		
		if(d) t = Math.toRadians(t);
		
		if(h!=0 && o!=0) {
			t = Math.asin(o/h);
			formula += "θ = aSin(o/h)";
			formula += "\nRearranged from: sinθ = o/h";
			a = Math.sqrt(h*h - o*o);
		} else if(h!=0 && a!=0) {
			t = Math.acos(a/h);
			formula += "θ = aCos(a/h)";
			formula += "\nRearranged from: cosθ = a/h";
			o = Math.sqrt(h*h - a*a);
		} else if(h!=0 && t!=0) {
			o = h*Math.sin(t);
			formula += "o = h*sin(θ)";
			formula += "\nRearranged from: sinθ = o/h";
			a = h*Math.cos(t);
			formula += "\n\nFormula Used: a = h*cos(θ)";
			formula += "\nRearranged from: cosθ = a/h";
		} else if(o!=0 && a!=0) {
			t = Math.atan(o/a);
			formula += "θ = aTan(o/a)";
			formula += "\nRearranged from: tanθ = o/a";
			h = Math.sqrt(a*a + o*o);
		} else if(o!=0 && t!=0) {
			a = o/Math.tan(t);
			formula += "a = o/tan(θ)";
			formula += "\nRearranged from: tanθ = o/a";
			h = Math.sqrt(a*a + o*o);
		} else if(a!=0 && t!=0) {
			o = a*Math.tan(t);
			formula += "o = a*tan(θ)";
			formula += "\nRearranged from: tanθ = o/a";
			h = Math.sqrt(a*a + o*o);
		}
		
		if(d) t = Math.toDegrees(t);

		String info = "Hyponetuse: " + format(h) + "\nOpposite: " + format(o) + "\nAdjacent: " + format(a) + "\nAngle θ: " + format(t) + angleMode + "\n\nFormula Used: " + formula;
		Triangle triangle = new Triangle(h,o,a,info);
		return triangle;
	}
	
	static String format(double n){
		DecimalFormat dec2 = new DecimalFormat("#0.00");
		return ((Double.toString(n).length() - Double.toString(n).indexOf('.')) > 2) ? dec2.format(n) : Double.toString(n);
	}
	
	static Point visibleMidPoint(Point p1, Point p2) {
		Point midPoint = new Point((p1.getX()+p2.getX())/2, (p1.getY()+p2.getY())/2);
		if (midPoint.getY() < 10) midPoint.setY(10);
		return midPoint;
	}
}
