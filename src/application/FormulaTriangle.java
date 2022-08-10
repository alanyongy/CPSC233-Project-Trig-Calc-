package application;

import javafx.scene.canvas.Canvas;

//child class of Triangle, used to override methods such that unknown 
//side lengths/angle are solved for in terms of the given values treated 
//as variables, outputting algebraic formulas instead of concrete values.
public class FormulaTriangle extends Triangle {
	
	/**FormulaTriangle Constructor. All values are passed to the parent class Triangle,
	 * as any differences in the process between the two triangle types are handled
	 * with overridden methods rather than differences in the constructor.
	 * @param inputH - value entered for hypotenuse side length
	 * @param inputO - value entered for opposite side length
	 * @param inputA - value entered for adjacent side length
	 * @param inputT - value entered for angle theta
	 * @param angleModeDegrees - degree mode - true for degrees, false for radians
	 */
	FormulaTriangle(String inputH, String inputO, String inputA, String inputT, boolean angleModeDegrees, Canvas canvasToDrawOn){
		super(inputH, inputO, inputA, inputT, angleModeDegrees, canvasToDrawOn);
	}
	
	/**
	 * Solves for a formula representing the missing values of the triangle's sidelengths or angle
	 * using trigonometry. String values containing such formulas are put into the triangle's hashmap 
	 * under the keys "hInfo", "oInfo", "aInfo", etc. along with the method/formula used under the key 
	 * "solveMethod". Unknown parameters are entered as 0, and will be solved for.
	 * @param h - hypotenuse side length of the triangle
	 * @param o - opposite side length of the triangle
	 * @param a - adjacent side length of the triangle
	 * @param t - theta - angle between hypotenuse and adjacent side lengths
	 * @param d - degree mode (degrees if true, radians if false) of value entered for theta
	 */
    @Override
	void calculateMissingValues(double h, double o, double a, double t, boolean d) {
		String solveMethod = "";
		String hInput = getInfo("HypotenuseInput");
		String oInput = getInfo("OppositeInput");
		String aInput = getInfo("AdjacentInput");
		String tInput = getInfo("Angle θInput");
		
		//if side length/angle values aren't equal to 0, they have a value given by the user,
		//and therefore should be used to calculate the values of the rest of the triangle.
		if(h!=0 && o!=0) {
			setInfo("t", "aSin(" + oInput + " / " + hInput + ")");
			solveMethod += "θ = aSin(o/h) \nRearranged from: sinθ = o/h";
			setInfo("a", "sqrt(" + hInput + "² - " + oInput + "²)");
		} else if(h!=0 && a!=0) {
			setInfo("t", "aCos(" + aInput + " / " + hInput + ")");
			solveMethod += "θ = aCos(a/h) \nRearranged from: cosθ = a/h";
			setInfo("o", "sqrt(" + hInput + "² - " + aInput + "²)");
		} else if(h!=0 && t!=0) {
			setInfo("o", hInput + " * sin(" + tInput + ")");
			setInfo("a", hInput + " * cos(" + tInput + ")");
			solveMethod += "o = h*sin(θ) \nRearranged from: sinθ = o/h \n\nTrig. Formula Used: a = h*cos(θ) \nRearranged from: cosθ = a/h";
		} else if(o!=0 && a!=0) {
			setInfo("t", "aTan(" + oInput + " / " + aInput + ")");
			solveMethod += "θ = aTan(o/a) \nRearranged from: tanθ = o/a";
			setInfo("h", "sqrt(" + aInput + "² + " + oInput + "²)");
		} else if(o!=0 && t!=0) {
			setInfo("a", oInput + " / tan(" + tInput + ")");
			solveMethod += "a = o/tan(θ) \nRearranged from: tanθ = o/a";
			setInfo("h", "sqrt(" + aInput + "² + " + oInput + "²)");
		} else if(a!=0 && t!=0) {
			setInfo("o", aInput + " * tan(" + tInput + ")");
			solveMethod += "o = a*tan(θ) \nRearranged from: tanθ = o/a";
			setInfo("h", "sqrt(" + aInput + "² + " + oInput + "²)");
		}
		
		//setting the triangle's actual sidelengths to a default of 1,1 for the
		//opposite and adjacent, so that a triangle may be drawn later to be labeled.
		setO(1);
		setA(1);
		setH(Math.sqrt(o*o + a*a));
		
		//we can store the solve method here since it does not
		//need additional formatting or checks to be displayed to the user
		setInfo("solveMethod", solveMethod);
	}
    
	/**
	 * The user may want to solve for a algebraic formula for a language outside of 
	 * java, so all variable names are valid.
	 * @param textField - the label of the input text field being checked
	 * @param text - text within the input text field being checked
	 * @param degreesMode - whether the program is currently calculating in degrees or radians
	 * @return String value describing the error causing the input to be invalid (empty string if no error) 
	 */
	@Override
    String checkError(String textField, String text, boolean degreesMode) {
    	return getErrorDescription();
    }
	
	
	/**
	 * Stores values to the hashmap under the keys "h", "o", "a", "t" using either the calculated values 
	 * currently assigned to the triangle's sidelengths/angle or the previously stored validated user input. 
	 * These are the final values that will be assigned to the labels and displayed to the user.
	 */
	@Override
	void storeInfo(){
		if(!getInfo("HypotenuseInput").isEmpty()) setInfo("h", getInfo("HypotenuseInput"));
		if(!getInfo("OppositeInput").isEmpty()) setInfo("o", getInfo("OppositeInput"));
		if(!getInfo("AdjacentInput").isEmpty()) setInfo("a", getInfo("AdjacentInput"));
		if(!getInfo("Angle θInput").isEmpty()) setInfo("t", getInfo("Angle θInput"));
	}
}
