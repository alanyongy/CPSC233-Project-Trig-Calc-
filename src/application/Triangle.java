package application;

import java.text.DecimalFormat;
import java.util.HashMap;

public class Triangle {
	private double h, o, a, t;
	private boolean degreeMode;
	private String errorDescription = "";
	private Point ho, ha, oa;
	private HashMap<String, String> info = new HashMap<String, String>();
	
	/* A constructor is not necessary - the default constructor will be used to
	 * initialize the triangle object, as all values of the triangle are dependent
	 * on user inputs or must be solved for through the use of the various methods
	 * below.*/
	
	/**
	 * Solves for the missing values of the triangle using trigonometry. The solved 
	 * values are assigned to the triangle object's instance variables, and the 
	 * method/formula used is also put into the triangle's hashmap under the key "solveMethod"
	 * Unknown parameters are entered as 0, and will be solved for.
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
		this.degreeMode = d;
		
		//the solve method may be stored here since it does not
		//need additional formatting before being displayed to the user
		info.put("solveMethod", solveMethod);
	}
	
	/**
	 * Checks if the text field contains a valid side length value. 
	 * Valid side lengths only contain digits, as well as up to one decimal and/or negative sign.
	 * Valid angles are (90 > n > -90) in degrees mode, or (-π/2 > n > -π/2) in radians mode.
	 * 0 is not a valid side length or angle.
	 * @param textField - the label of the input text field being checked
	 * @param text - text within the input text field
	 * @param degreesMode - whether the program is currently calculating in degrees or radians
	 * @return String value describing the error causing the input to be invalid (empty string if no error) 
	 */
    String checkError(String textField, String text, boolean degreesMode) {
    	//initializing variables that keep track of unallowed or limited quantity allowed characters
    	int dotCount = 0;
    	int dashCount = 0;
    	int otherCount = 0;
    	
    	//loops through the string to tally up previously initialized counters.
    	for(int i = 0; i < text.length(); i++) {
    		if(!Character.isDigit(text.charAt(i))){
        		if(text.charAt(i) == '.') dotCount++;
        		else if(text.charAt(i) == '-' && i == 0) dashCount++;
        		else otherCount++;
    		}
    	}
    	
    	//large amount of conditions that cover the many ways which a user may input an invalid value.
    	if(dotCount > 1 || dashCount > 1 || otherCount >= 1 
    			|| (textField == "Angle θ" && !degreesMode && Math.abs(Double.parseDouble(text)) >= Math.PI/2)
    			|| (textField == "Angle θ" && degreesMode && Math.abs(Double.parseDouble(text)) >= 90) 
    			|| (textField == "Hypotenuse" && Double.parseDouble(text) < 0) 
    			|| Double.parseDouble(text) == 0) {
    		//checks which above condition was violated, and sets the errorLabel message to an appropriate message.
    		if (textField == "Angle θ") errorDescription = " must be less than 90° or π/2";
    		else if(dotCount > 1) errorDescription = " can only contain one decimal point.";
    		else if(dashCount > 1) errorDescription = " can only contain one negative sign.";
    		else if(otherCount >= 1) errorDescription = " can only contain digits, decimals or neg. signs.";
    		else if(textField == "Hypotenuse" && Double.parseDouble(text) < 0) errorDescription = " can not be less than 0.";
    		else if(Double.parseDouble(text) == 0) errorDescription = " can not be equal to 0.";
    		errorDescription = textField + errorDescription;
    	}
    	
    	return errorDescription;
    }
    
    
	/**
	 * takes the triangle's current sidelength and angle values, formats them 
	 * into strings with 2 decimal places, and stores them in the triangle's information hashmap.
	 * These are the final values that will be assigned to the labels and displayed to the user.
	 */
	void storeTriangleInfo(){
		//creation of component to format doubles into 2 decimal places
		DecimalFormat dec2 = new DecimalFormat("#0.00");
		
		//converting the boolean degree mode used to calculate 
		//the triangle into text that makes sense to the user
		String angleMode = degreeMode ? "°" : "rad";
		
		//storing formatted information into the hashmap of the triangle object
		info.put("h", dec2.format(h));
		info.put("o", dec2.format(o));
		info.put("a", dec2.format(a));
		info.put("t", dec2.format(t)+angleMode);
	}
	
	
	/**
	 * uses the triangle's current adjacent and opposite side length values
	 * to determine the coordinates of each of the triangle's three corners.
	 */
	void calculatePointCoordinates() {
		/* opposite value for the hypotenuse is flipped to counter-act the java
		 * canvas' orientation of heading top to bottom as the y value increases. The
		 * values are divided by two since otherwise, the points would have two times
		 * the opposite/adjacent sidelength distances away from each other due to
		 * having a negative/positive difference between them. */		
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
		
		//recalculating the hypotenuse length since the opposite/adjacent lengths have changed.
		h = Math.sqrt(o*o + a*a);
	}
	
	
	/**
	 *Translates the points of the triangle over the x/y axis such that they are all positive. 
	 */
	void moveToPositiveQuadrant() {
		//translating triangle across x/y axis as needed to keep all 
		//triangle points on main quadrant (positive, positive)
		if(ho.getX() < 0) translatePoints(Math.abs(ho.getX()), 0);
		if(ho.getY() < 0) translatePoints(0, Math.abs(ho.getY()));
		if(ha.getX() < 0) translatePoints(Math.abs(ha.getX()), 0);
		if(ha.getY() < 0) translatePoints(0, Math.abs(ha.getY()));
		if(oa.getX() < 0) translatePoints(Math.abs(oa.getX()), 0);
		if(oa.getY() < 0) translatePoints(0, Math.abs(oa.getY()));
	}
	
	
	/**
	 * Moves all points of the triangle right by x, down by y.
	 * @param x - value of horizontal direction to move all points by
	 * @param y - value of vertical direction to move all points by
	 */
	void translatePoints(double x, double y){
		//all points must be moved so that the triangle is never distorted 
		//from it's original opposite:adjacent ratio.
		ho.setX(ho.getX()+x);
		ho.setY(ho.getY()+y);
		ha.setX(ha.getX()+x);
		ha.setY(ha.getY()+y);
		oa.setX(oa.getX()+x);
		oa.setY(oa.getY()+y);
	}

	
    /**
     * Checks if the created triangle has valid side lengths and angle. 0 is not a valid side length/angle. 
     * A NaN may also be calculated for the hypotenuse in cases where the user inputs can not create a valid
     * triangle, and similarly marks the triangle as invalid. Having non-empty error description also voids validity.
     * @return true if the triangle has valid side lengths/angle and no errors, otherwise false
     */
    boolean validateTriangle() {
    	//triangle.getH() will return NaN if it is unable to be calculated due to entering 
    	//impossible values for triangle side lengths (ie. opposite larger than hypotenuse)
    	if (Double.isNaN(getH()) || getH() == 0 || getO() == 0 || getA() == 0 || !errorDescription.isEmpty())
    		return false;
    	return true;
    }
    
    
	/**
	 * Stores the user inputs in a <String,String> hashmap for future reference.
	 * The label of the text field is used as a key, and the text is stored as the value.
	 * @param textField - Label of text field
	 * @param text - String value of text within text field
	 */
	void storeUserInputs(String textField, String text){
		info.put(textField+"Input", text);
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
	 * @return degree mode that the triangle was calculated in (true for degrees, false for radians)
	 */
	public boolean getDegreeMode() {
		return degreeMode;
	}
	
	/**
	 * @return String value of the error description (empty if no errors occurred) assigned
	 * to the object while validating the user's inputs for it's sidelength or angle values.
	 */
	public String getErrorDescription() {
		String newStr = new String(errorDescription);
		return newStr;
	}
	
	/**
	 * @param newStr - String to set the error description of the object to.
	 */
	public void setErrorDescription(String newStr) {
		errorDescription = new String(newStr);
	}

	/**
	 * @param key - key in the hashmap to retrieve the value of.
	 * @return value of the string variable stored at key location in hashmap
	 */
	public String getInfo(String key) {
		String newStr = new String(info.get(key));
		return newStr;
	}
	
	public void setInfo(String key, String value) {
		HashMap<String, String> newInfo = new HashMap<String, String>(info);
		newInfo.put(key, value);
		info = newInfo;
	}
}