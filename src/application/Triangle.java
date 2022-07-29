package application;

import java.text.DecimalFormat;
import java.util.HashMap;

public class Triangle {
	private double h, o, a;
	private Point ho, ha, oa;
	private HashMap<String, String> info = new HashMap<String, String>();
	
	Triangle(double setH, double setO, double setA, String setInfoH, String setInfoO, String setInfoA, String setInfoT, String setF){	
		h = setH;
		o = setO;
		a = setA;	
		info.put("h", setInfoH);
		info.put("o", setInfoO);
		info.put("a", setInfoA);
		info.put("t", setInfoT);
		info.put("solveMethod", setF);
		normalizePoints();
	}
	
	Triangle(double h, double o, double a, double t, boolean d){
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
		
		this.h = h;
		this.o = o;
		this.a = a;	
		info.put("h", format(h));
		info.put("o", format(o));
		info.put("a", format(a));
		info.put("t", format(t) + angleMode);
		info.put("solveMethod", solveMethod);
		normalizePoints();
	}
	
	static String format(double n){
		DecimalFormat dec2 = new DecimalFormat("#0.00");
		//DecimalFormat dec0 = new DecimalFormat("#0");
		//return ((Double.toString(n).length() - Double.toString(n).indexOf('.')) > 2) ? dec2.format(n) : dec0.format(n);
		return dec2.format(n);
	}
	//translates points of triangles such that they are all positive
	//scales up/down triangle such that triangle's largest length uses entire canvas (maxW, maxH)
	void normalizePoints(){
		double maxW = 360;
		double maxH = 180;
		double scale = 1;
		//which side length is closer to its side of the window's maximum size
		if(Math.abs(o)/maxH > Math.abs(a)/maxW) scale = maxH / Math.abs(o);
		else scale = maxW / Math.abs(a);
		o *= scale;
		a *= scale;
		h = Math.sqrt(o*o + a*a);
		
		//o is reversed to counter-act canvas' flipped y axis
		this.ho = new Point(a/2,-o/2);
		this.ha = new Point(-a/2,o/2);
		this.oa = new Point(a/2,o/2);
		
		if(ho.getX() < 0) translatePoints(Math.abs(ho.getX()), 0);
		if(ho.getY() < 0) translatePoints(0, Math.abs(ho.getY()));
		if(ha.getX() < 0) translatePoints(Math.abs(ha.getX()), 0);
		if(ha.getY() < 0) translatePoints(0, Math.abs(ha.getY()));
		if(oa.getX() < 0) translatePoints(Math.abs(oa.getX()), 0);
		if(oa.getY() < 0) translatePoints(0, Math.abs(oa.getY()));
	}
	
	void translatePoints(double x, double y){
		ho.setX(ho.getX()+x);
		ho.setY(ho.getY()+y);
		ha.setX(ha.getX()+x);
		ha.setY(ha.getY()+y);
		oa.setX(oa.getX()+x);
		oa.setY(oa.getY()+y);
	}
	
	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public double getO() {
		return o;
	}

	public void setO(double o) {
		this.o = o;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public Point getHo() {
		Point newHo = new Point(ho.getX(), ho.getY());
		return newHo;
	}
	
	public Point getHa() {
		Point newHa = new Point(ha.getX(), ha.getY());
		return newHa;
	}
	
	public Point getOa() {
		Point newOa = new Point(oa.getX(), oa.getY());
		return newOa;
	}

	public String getInfo(String key) {
		String newStr = new String(info.get(key));
		return newStr;
	}

//	//currently unused
//	public void setInfo(String key, String value) {
//		HashMap<String, String> newInfo = new HashMap<String, String>(info);
//		newInfo.replace(key, value);
//		info = newInfo;
//	}
}
