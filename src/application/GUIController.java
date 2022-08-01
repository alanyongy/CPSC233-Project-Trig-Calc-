package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUIController {
	Stage applicationStage;
    Triangle triangle;
	
    @FXML
    private TextField hTf;
    @FXML
    private TextField aTf;
    @FXML
    private TextField oTf;
    @FXML
    private TextField tTf;
    @FXML
    private ToggleButton degreesToggleButton;
    @FXML
    private ToggleButton radiansToggleButton;
    @FXML
    private Canvas canvas = new Canvas();
    @FXML
    private Text infoText;
    @FXML
    private Label errorLabel; 
	
    @FXML
    void calculate() {
    	errorLabel.setText("");
    	if(validateTotalInputs()) {
    		createTriangle();
    		if(validateTriangle()) {
        		GraphicsContext gc = canvas.getGraphicsContext2D();
            	drawTriangle(gc);
    		}
    	}
    }
	/**
	 * Draws the triangle by stroking between triangle points.
	 * Moves and sets labels to their respective side-length/angle position and value.
	 * Displays the solve method information in the text area.
	 * @param gc - GraphicsContext object
	 */
	public void drawTriangle(GraphicsContext gc) {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		double haX = triangle.getHa().getX();
		double haY = triangle.getHa().getY();
		double hoX = triangle.getHo().getX();
		double hoY = triangle.getHo().getY();
		double oaX = triangle.getOa().getX();
		double oaY = triangle.getOa().getY();
		gc.strokeLine(haX, haY, hoX, hoY);
		gc.strokeLine(hoX, hoY, oaX, oaY);
		gc.strokeLine(oaX, oaY, haX, haY);
		
		int xBound = 335;
		int yBound = 10;
		Point h = Point.solveMidpoint(triangle.getHa(), triangle.getHo(), xBound, yBound);
		Point o = Point.solveMidpoint(triangle.getHo(), triangle.getOa(), xBound, yBound);
		Point a = Point.solveMidpoint(triangle.getHa(), triangle.getOa(), xBound, yBound);
		Point t = Point.solveMidpoint(triangle.getHa(), triangle.getHa(), xBound, yBound);
		
		moveOverlappingPoints(h, o, a, t);
		
		gc.fillText(triangle.getInfo("h"), h.getX(), h.getY());
		gc.fillText(triangle.getInfo("o"), o.getX(), o.getY());
		gc.fillText(triangle.getInfo("a"), a.getX(), a.getY());
		gc.fillText(triangle.getInfo("t"), t.getX(), t.getY());
		
    	infoText.setText("Hyponetuse: " + triangle.getInfo("h") + 
    			"\nOpposite: " + triangle.getInfo("o") + 
    			"\nAdjacent: " + triangle.getInfo("a") + 
    			"\nAngle θ: " + triangle.getInfo("t")  + 
    			"\n\nFormula Used: " + triangle.getInfo("solveMethod"));
	}
   
    /**
     * Validates the values in the text fields entered by the user, then calls the triangle constructor with those values.
     */
    void createTriangle() {
    	Double validatedH = validateInput("Hypotenuse", hTf.getText(), false, true);
    	Double validatedO = validateInput("Opposite", oTf.getText(), false, false);
    	Double validatedA = validateInput("Adjacent", aTf.getText(), false, false);
    	Double validatedT = validateInput("Angle θ", tTf.getText(), true, false);
    	boolean degrees = degreesToggleButton.isSelected() ? true : false;
    	triangle = Triangle.solveTriangle(validatedH,validatedO,validatedA,validatedT, degrees); 
    }
    
    
    /**
     * Toggles the degrees and radians button in the GUI such that one and only one is always active.
     * @param trigger - radians or degrees GUI button
     */
    @FXML
    void toggleAngleMode(ActionEvent trigger) {
    	if(trigger.getSource().equals(degreesToggleButton)) {
    		radiansToggleButton.setSelected(false);
    		degreesToggleButton.setSelected(true);
    	} else {
    		degreesToggleButton.setSelected(false);
    		radiansToggleButton.setSelected(true);
    	}
    }
    
    
    /**
     * Checks if the text field contains a valid side length value. 
     * Valid side lengths only contain digits, as well as up to one decimal and/or negative sign.
     * Valid angles are (90 > n > -90) in degrees mode, or (-π/2 > n > -π/2) in radians mode.
     * 0 Is not a valid side length or angle.
     * Sets the error label in GUI to a description of the error if the text field is invalid.
     * @param textField - input text field
     * @param text - text within the input text field
     * @param isAngle - whether the input is the value of an angle
     * @return double value of text, otherwise 0.0
     */
    Double validateInput(String textField, String text, boolean isAngle, boolean isH) {
    	int dotCount = 0;
    	int dashCount = 0;
    	int otherCount = 0;
		String errorDescription = "";
		
    	if(text.isEmpty()) return 0.0;
    	
    	for(int i = 0; i < text.length(); i++) {
    		if(!Character.isDigit(text.charAt(i))){
        		if(text.charAt(i) == '.') dotCount++;
        		else if(text.charAt(i) == '-' && i == 0) dashCount++;
        		else otherCount++;
    		}
    	}
    	
    	if(dotCount > 1 || dashCount > 1 || otherCount >= 1 
    			|| (isAngle && radiansToggleButton.isSelected() && Math.abs(Double.parseDouble(text)) >= Math.PI/2)
    			|| (isAngle && degreesToggleButton.isSelected() && Math.abs(Double.parseDouble(text)) >= 90) 
    			|| (isH && Double.parseDouble(text) < 0) 
    			|| Double.parseDouble(text) == 0) {
    		if (isAngle) errorDescription = " must be less than 90° or π/2";
    		else if(dotCount > 1) errorDescription = " can only contain one decimal point.";
    		else if(dashCount > 1) errorDescription = " can only contain one negative sign.";
    		else if(otherCount >= 1) errorDescription = " can only contain digits, decimals or neg. signs.";
    		else if(isH && Double.parseDouble(text) < 0) errorDescription = " can not be less than 0.";
    		else if(Double.parseDouble(text) == 0) errorDescription = " can not be equal to 0.";
    		errorLabel.setText(textField + errorDescription);
    		return 0.0;
    	}
    	
    	return Double.parseDouble(text);
    }
    
    /**
     * Checks if exactly two values were input by the user.
     * @return true if two values entered, otherwise false
     */
    boolean validateTotalInputs() {
    	int totalInputs = 0;
    	if (hTf.getText().isEmpty()) totalInputs++;
    	if (oTf.getText().isEmpty()) totalInputs++;
    	if (aTf.getText().isEmpty()) totalInputs++;
    	if (tTf.getText().isEmpty()) totalInputs++;
    	if (totalInputs != 2) {
    		errorLabel.setText("Only enter values for two components.");
    		return false;
    	}
    	return true;
    }
    
    
    /**
     * Checks if the created triangle has valid side lengths.
     * @return true if valid, otherwise false
     */
    boolean validateTriangle() {
    	//triangle.getH() will return NaN if it is unable to be calculated
    	//due to entering impossible values for triangle side lengths
    	if (Double.isNaN(triangle.getH()) && errorLabel.getText().isEmpty()) {
    		errorLabel.setText("Opp. and Adj. can't be larger than Hypotenuse");
    		return false;
    	}
    	return true;
    }
    
    
	/**
	 * Checks for points that are too close to each other and moves them to prevent overlapping text. 
	 * @param h - hypotenuse
	 * @param o - opposite
	 * @param a - adjacent
	 * @param t - theta - angle
	 **/
	static void moveOverlappingPoints(Point h, Point o, Point a, Point t) {
		Point[] p = {h,o,a,t};
		int minY = 15;
		int minX = 50;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(j==i) continue;
				double p1X = p[i].getX();
				double p1Y = p[i].getY();
				double p2X = p[j].getX();
				double p2Y = p[j].getY();
				//move point to minX/Y relative to other point if they are too close
				if(Math.abs(p1Y - p2Y) < minY && Math.abs(p1X - p2X) < minX) {
					if(p1Y <= p2Y) p[j].setY(p2Y + minY - (p2Y-p1Y));
					else p[i].setY(p1Y + minY - (p1Y-p2Y));
				}
			}
		}
	}
}


