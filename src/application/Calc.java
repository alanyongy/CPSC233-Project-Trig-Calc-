package application;

public class Calc {
	static String solveValues(double h, double o, double a, double t, boolean d){
		String test = "";
		
		if(h!=0 && o!=0) {
			t = Math.asin(o/h);
			a = Math.sqrt(h*h - o*o);
		} else if(h!=0 && a!=0) {
			t = Math.acos(a/h);
			o = Math.sqrt(h*h - a*a);
		} else if(h!=0 && t!=0) {
			o = h*Math.sin(t);
			a = h*Math.cos(a);
		} else if(o!=0 && a!=0) {
			t = Math.atan(o/a);
			h = Math.sqrt(a*a + o*o);
		} else if(o!=0 && t!=0) {
			h = o/Math.sin(t);
			a = o/Math.tan(t);
		} else if(a!=0 && t!=0) {
			o = a*Math.tan(t);
			h = Math.sqrt(a*a + o*o);
		}
		if(d) t = Math.toDegrees(t);
		
		return h+" "+o+" "+a+" "+t;
	}
	
}
