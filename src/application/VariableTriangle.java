package application;

//child class of Triangle, used to override methods such that unknown 
//side lengths/angle are solved for in terms of the given variables,
//outputting algebraic formulas instead of concrete values.
public class VariableTriangle extends Triangle {
	String hInfo = "", oInfo = "", aInfo = "", tInfo = "";
	
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
			tInfo = "aSin(" + oInput + "/" + hInput + ")";
			solveMethod += "θ = aSin(o/h) \nRearranged from: sinθ = o/h";
			aInfo = "sqrt(" + hInput + "^2 + " + oInput + "^2)";
		} else if(h!=0 && a!=0) {
			tInfo = "aCos(" + aInput + "/" + hInput + ")";
			solveMethod += "θ = aCos(a/h) \nRearranged from: cosθ = a/h";
			oInfo = "sqrt(" + hInput + "^2 + " + aInput + "^2)";
		} else if(h!=0 && t!=0) {
			oInfo = hInput + "*sin(" + tInput + ")";
			aInfo = hInput + "*cos(" + tInput + ")";
			solveMethod += "o = h*sin(θ) \nRearranged from: sinθ = o/h \n\nFormula Used: a = h*cos(θ) \nRearranged from: cosθ = a/h";
		} else if(o!=0 && a!=0) {
			tInfo = "aTan(" + oInput + "/" + aInput + ")";
			solveMethod += "θ = aTan(o/a) \nRearranged from: tanθ = o/a";
			hInfo = "sqrt(" + aInput + "^2 + " + oInput + "^2)";
		} else if(o!=0 && t!=0) {
			aInfo = oInput + "/tan(" + tInput + ")";
			solveMethod += "a = o/tan(θ) \nRearranged from: tanθ = o/a";
			hInfo = "sqrt(" + aInput + "^2 + " + oInput + "^2)";
		} else if(a!=0 && t!=0) {
			oInfo = aInput + "*tan(" + tInput + ")";
			solveMethod += "o = a*tan(θ) \nRearranged from: tanθ = o/a";
			hInfo = "sqrt(" + aInput + "^2 + " + oInput + "^2)";
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
	 * Checks if the text field contains a valid side length/angle for a variable name based 
	 * triangle. The user may want to solve for a algebraic formula for a language outside of 
	 * java, so all variable names are valid, except for those which start with numbers (to 
	 * prevent confusion in understanding the output formula) Sets the error label in GUI to 
	 * a description of the error if the text field is invalid.
	 * @param textField - the label of the input text field being checked
	 * @param text - text within the input text field being checked
	 * @param degreesMode - whether the program is currently calculating in degrees or radians
	 * @return String value describing the error causing the input to be invalid (empty string if no error) 
	 */
	@Override
    String checkError(String textField, String text, boolean degreesMode) {
    	if(text.length() > 0 && Character.isDigit(text.charAt(0)))
    		setErrorDescription(textField + " should not be a variable name starting with a number.");
    	return getErrorDescription();
    }
	
	
	/**
	 * Stores values to the hashmap under the keys "h", "o", "a", "t" using either the calculated values 
	 * currently assigned to the triangle's sidelengths/angle or the previously stored validated user input. 
	 * These are the final values that will be assigned to the labels and displayed to the user.
	 */
	@Override
	void storeTriangleInfo(){
		if(!hInfo.isEmpty()) setInfo("h", hInfo);
		if(!oInfo.isEmpty()) setInfo("o", oInfo);
		if(!aInfo.isEmpty()) setInfo("a", aInfo);
		if(!tInfo.isEmpty()) setInfo("t", tInfo);
		if(!getInfo("HypotenuseInput").isEmpty()) setInfo("h", getInfo("HypotenuseInput"));
		if(!getInfo("OppositeInput").isEmpty()) setInfo("o", getInfo("OppositeInput"));
		if(!getInfo("AdjacentInput").isEmpty()) setInfo("a", getInfo("AdjacentInput"));
		if(!getInfo("Angle θInput").isEmpty()) setInfo("t", getInfo("Angle θInput"));
	}
}
