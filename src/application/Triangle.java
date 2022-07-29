package application;

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
