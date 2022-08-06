package application;

import java.text.DecimalFormat;
import java.util.HashMap;

public class Triangle {
	private double h, o, a, t;
	private boolean d;
	private Point ho, ha, oa;
	private HashMap<String, String> info = new HashMap<String, String>();
	//A constructor is not necessary, I will use the basic empty constructor to initialize the triangle object,
	//as all values of the triangle are dependent on user inputs or must be solved for through use of various methods below.
	
	/**
	 * Solves for the missing values of the triangle using trigonometry.
	 * @param h - hypotenuse side length of the triangle
	 * @param o - opposite side length of the triangle
	 * @param a - adjacent side length of the triangle
	 * @param t - theta - angle between hypotenuse and adjacent side lengths
	 * @param d - degree mode (degrees if true, radians if false) of value entered for theta
	 */
	void calculateMissingValues(double h, double o, double a, double t, boolean d){
		String solveMethod = "";
		
		//determining whether it is necessary to parse the angle given by the user to radians
		//since the Math library trigonometric functions only works in radians
		if(d) t = Math.toRadians(t);
		
		//if side length/angle values aren't equal to 0, they have a value given by the user,
		//and therefore should be used to calculate the values of the rest of the triangle.
		if(h!=0 && o!=0) {
			t = Math.asin(Math.abs(o)/Math.abs(h));
			solveMethod += "θ = aSin(o/h) \nRearranged from: sinθ = o/h";
			a = Math.sqrt(h*h - o*o);
		} else if(h!=0 && a!=0) {
			t = Math.acos(Math.abs(a)/Math.abs(h));
			solveMethod += "θ = aCos(a/h) \nRearranged from: cosθ = a/h";
			o = Math.sqrt(h*h - a*a);
		} else if(h!=0 && t!=0) {
			o = h*Math.sin(t);
			a = h*Math.cos(t);
			solveMethod += "o = h*sin(θ) \nRearranged from: sinθ = o/h \n\nFormula Used: a = h*cos(θ) \nRearranged from: cosθ = a/h";
		} else if(o!=0 && a!=0) {
			t = Math.atan(Math.abs(o)/Math.abs(a));
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
		
		//converting theta (back) into degrees if the user chose to calculate in degrees
		if(d) t = Math.toDegrees(t);
		
		//assigning the newly calculated values to the triangle
		this.h = h;
		this.o = o;
		this.a = a;
		this.t = t;
		this.d = d;
		
		//we can store the solve method here since it does not
		//need additional formatting to be displayed to the user
		info.put("solveMethod", solveMethod);
	}
	
	/**
	 * uses the triangle's current adjacent and opposite side length values
	 * to determine the coordinates of each of the triangle's three corners.
	 */
	void calculatePointCoordinates() {
		this.ho = new Point(a/2,-o/2);
		this.ha = new Point(-a/2,o/2);
		this.oa = new Point(a/2,o/2);
	}

	/**
	 *Scales up/down the size (point coordinates) of the triangle such that it's largest length 
	 *(relative to canvas edge length) uses the entire canvas (maxW, maxH)
	 */
	void scaleTriangle(){
		//maximum size that triangle should take up in x and y dimensions
		double maxW = 360;
		double maxH = 180;
		double scale = 1;
		
		//scale triangle size to use the entire canvas without distorting x:y ratio
		//by picking the most appropriate scale to use between the x and y dimension
		//by determining the side length with length closest to it's respective window side length
		if(Math.abs(o)/maxH > Math.abs(a)/maxW) scale = maxH / Math.abs(o);
		else scale = maxW / Math.abs(a);
		o *= scale;
		a *= scale;
		h = Math.sqrt(o*o + a*a);
	}
	
	
	/**
	 *Translates the points of the triangle over the x/y axis such that they are all positive. 
	 */
	void moveToPositiveQuadrant() {
		//translating triangle across x/y axis as needed to keep all triangle points on main quadrant (positive, positive)
		if(ho.getX() < 0) translatePoints(Math.abs(ho.getX()), 0);
		if(ho.getY() < 0) translatePoints(0, Math.abs(ho.getY()));
		if(ha.getX() < 0) translatePoints(Math.abs(ha.getX()), 0);
		if(ha.getY() < 0) translatePoints(0, Math.abs(ha.getY()));
		if(oa.getX() < 0) translatePoints(Math.abs(oa.getX()), 0);
		if(oa.getY() < 0) translatePoints(0, Math.abs(oa.getY()));
	}
	
	/**
	 * Moves all points of the triangle right by x, down by y.
	 * @param x - value of horizontal direction to move point by
	 * @param y - value of vertical direction to move point by
	 */
	void translatePoints(double x, double y){
		ho.setX(ho.getX()+x);
		ho.setY(ho.getY()+y);
		ha.setX(ha.getX()+x);
		ha.setY(ha.getY()+y);
		oa.setX(oa.getX()+x);
		oa.setY(oa.getY()+y);
	}

	/**
	 * takes the triangle's current sidelength and angle values,
	 * formats them into strings with 2 decimal places, and stores
	 * them in the triangle's information hashmap
	 */
	void storeTriangleInfo(){
		//creation of component to format doubles into 2 decimal places
		DecimalFormat dec2 = new DecimalFormat("#0.00");
		
		//converting the boolean degree mode used to calculate 
		//the triangle into text that makes sense to the user
		String angleMode = d ? "°" : "rad";
		
		//storing formatted information into the hashmap of the triangle object
		info.put("h", dec2.format(h));
		info.put("o", dec2.format(o));
		info.put("a", dec2.format(a));
		info.put("t", dec2.format(t)+angleMode);
	}
	
	/**
	 * @return hypotenuse side length value of triangle object
	 */
	public double getH() {
		return h;
	}

	/**
	 * @param hypotenuse side length value of triangle object to set
	 */
	public void setH(double h) {
		this.h = h;
	}

	/**
	 * @return opposite side length of triangle object
	 */
	public double getO() {
		return o;
	}

	/**
	 * @param opposite side length value of triangle object
	 */
	public void setO(double o) {
		this.o = o;
	}

	/**
	 * @return adjacent side length of triangle object
	 */
	public double getA() {
		return a;
	}

	/**
	 * @param adjacent side length value of triangle object
	 */
	public void setA(double a) {
		this.a = a;
	}

	/**
	 * @return point object representing triangle object's corner between opposite and hypotenuse side lengths
	 */
	public Point getHo() {
		Point newHo = new Point(ho.getX(), ho.getY());
		return newHo;
	}
	
	/**
	 * @return point object representing triangle object's corner between hypotenuse and adjacent side lengths
	 */
	public Point getHa() {
		Point newHa = new Point(ha.getX(), ha.getY());
		return newHa;
	}
	
	/**
	 * @return point object representing triangle object's corner between opposite and adjacent side lengths
	 */
	public Point getOa() {
		Point newOa = new Point(oa.getX(), oa.getY());
		return newOa;
	}

	/**
	 * @param key - key in hashmap to retrieve the value of.
	 * @return value of the string variable stored at key location in hashmap
	 */
	public String getInfo(String key) {
		String newStr = new String(info.get(key));
		return newStr;
	}
}