package application;

import java.awt.Font;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
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
    	//creating a new triangle object for us to manipulate as needed
    	triangle = new Triangle();
    	
    	if(validateTotalInputs()) {
    		//computes for the missing values of the triangle using validated user inputs
    		calculateTriangle();
    		
    		//sets the current triangle sidelength/angle information to string values in the
    		//triangle before they are manipulated by further methods for display purposes
    		triangle.storeTriangleInfo();
    		
    		//scales the triangle to a suitable size for the canvas
    		triangle.scaleTriangle();
    		
    		//calculates the coordinates of each of the triangle's three corners.
    		triangle.calculatePointCoordinates();
    		
    		//moves each of the triangle's points to let us display triangles 
    		//with points of negative coordinates on the main quadrant 
    		triangle.moveToPositiveQuadrant();
    		
    		if(validateTriangle()) {
    			//creates the necessary component for drawing on the canvas
        		GraphicsContext gc = canvas.getGraphicsContext2D();
        		
        		//draws the outline of the triangle
            	drawTriangle(gc);
            	
            	//moves and sets labels according to the values of the triangle
            	setTriangleLabels(gc);
            	
            	//puts the solve method in the text area of the GUI.
            	setInfoText();
    		}
    	}
    }
    
    /**
     * Validates the values in the text fields entered by the user, then asks the triangle
     * to compute it's missing values.
     */
    void calculateTriangle() {
    	Double validatedH = validateInput("Hypotenuse", hTf.getText());
    	Double validatedO = validateInput("Opposite", oTf.getText());
    	Double validatedA = validateInput("Adjacent", aTf.getText());
    	Double validatedT = validateInput("Angle θ", tTf.getText());
    	boolean degrees = degreesToggleButton.isSelected() ? true : false;
    	triangle.calculateMissingValues(validatedH,validatedO,validatedA,validatedT, degrees); 
    }
   
	/**
	 * Draws the triangle by stroking between triangle points.
	 * @param graphics - GraphicsContext object to draw with
	 */
	public void drawTriangle(GraphicsContext graphics) {
		//clears the canvas so that the previously drawn triangle (if applicable) is cleared.
		graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		//setting triangle values to variables to clean up further calculations
		double haX = triangle.getHa().getX();
		double haY = triangle.getHa().getY();
		double hoX = triangle.getHo().getX();
		double hoY = triangle.getHo().getY();
		double oaX = triangle.getOa().getX();
		double oaY = triangle.getOa().getY();
		
		//strokes between each point of the triangle
		graphics.strokeLine(haX, haY, hoX, hoY);
		graphics.strokeLine(hoX, hoY, oaX, oaY);
		graphics.strokeLine(oaX, oaY, haX, haY);
	}
	
	/**
	 * Moves and sets the labels for the triangle to their 
	 * respective side-length/angle positions and values.
	 * @param graphics - GraphicsContext object to draw with
	 */
	public void setTriangleLabels(GraphicsContext graphics) {
		//setting minimum bounds for labels (so they are fully visible)
		int xBound = 335;
		int yBound = 10;
		
		//setting values for overlap detection between labels
		int overlapMinX = 50;
		int overlapMinY = 15;
		
		//creating a new point for each label to be drawn at
		Point h = calculateMidpoint(triangle.getHa(), triangle.getHo(), xBound, yBound);
		Point o = calculateMidpoint(triangle.getHo(), triangle.getOa(), xBound, yBound);
		Point a = calculateMidpoint(triangle.getHa(), triangle.getOa(), xBound, yBound);
		Point t = calculateMidpoint(triangle.getHa(), triangle.getHa(), xBound, yBound);
		
		//move the newly created points such that they do not overlap to keep labels readable
		moveOverlappingPoints(h, o, a, t, overlapMinX, overlapMinY);
		
		//writes the label information at their respective locations
		graphics.setFill(Color.RED);
		graphics.fillText(triangle.getInfo("h"), h.getX(), h.getY());
		graphics.fillText(triangle.getInfo("o"), o.getX(), o.getY());
		graphics.fillText(triangle.getInfo("a"), a.getX(), a.getY());
		graphics.fillText(triangle.getInfo("t"), t.getX(), t.getY());
	}
	
	/**
	 * puts the solve method information for the triangle in the infoText area of the GUI.
	 */
	public void setInfoText() {
    	infoText.setText("Hyponetuse: " + triangle.getInfo("h") + 
    			"\nOpposite: " + triangle.getInfo("o") + 
    			"\nAdjacent: " + triangle.getInfo("a") + 
    			"\nAngle θ: " + triangle.getInfo("t")  + 
    			"\n\nFormula Used: " + triangle.getInfo("solveMethod"));
	}
    
    /**
     * Toggles the degrees and radians button in the GUI such that one and only one is always active.
     * @param trigger - radians or degrees GUI button that is clicked by the user
     */
    @FXML
    void toggleAngleMode(ActionEvent trigger) {
    	//determines which button was clicked, and sets it to true, 
    	//while also setting the other button false.
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
	 * @param textField - the label of the input text field being checked
	 * @param text - text within the input text field
	 * @param isAngle - whether the input is the value of an angle
	 * @return double value of text, otherwise 0.0
	 */
    Double validateInput(String textField, String text) {
    	//initializing variables that keep track of unallowed or partially allowed values
    	int dotCount = 0;
    	int dashCount = 0;
    	int otherCount = 0;
		String errorDescription = "";
		
		//values for the triangle not set by the user should 
		//be set to 0 to prevent Math class function errors.
    	if(text.isEmpty()) return 0.0;
    	
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
    			|| (textField == "Angle θ" && radiansToggleButton.isSelected() && Math.abs(Double.parseDouble(text)) >= Math.PI/2)
    			|| (textField == "Angle θ" && degreesToggleButton.isSelected() && Math.abs(Double.parseDouble(text)) >= 90) 
    			|| (textField == "Hypotenuse" && Double.parseDouble(text) < 0) 
    			|| Double.parseDouble(text) == 0) {
    		
    		//checks which above condition was violated, and sets the errorLabel message to an appropriate message.
    		if (textField == "Angle θ") errorDescription = " must be less than 90° or π/2";
    		else if(dotCount > 1) errorDescription = " can only contain one decimal point.";
    		else if(dashCount > 1) errorDescription = " can only contain one negative sign.";
    		else if(otherCount >= 1) errorDescription = " can only contain digits, decimals or neg. signs.";
    		else if(textField == "Hypotenuse" && Double.parseDouble(text) < 0) errorDescription = " can not be less than 0.";
    		else if(Double.parseDouble(text) == 0) errorDescription = " can not be equal to 0.";
    		errorLabel.setText(textField + errorDescription);
    		
    		//returns 0 instead of the value in the text field because the value entered was invalid.
    		return 0.0;
    	}
    	
    	//return the double value of the string if the input was valid
    	return Double.parseDouble(text);
    }
    
    /**
     * Checks if exactly two values were input by the user within the text fields.
     * @return true if exactly two values were entered, otherwise false
     */
    boolean validateTotalInputs() {
    	int totalInputs = 0;
    	
    	//checks each text field and increments the counter for each text field that isn't empty
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
     * 0 is not a valid side length.
     * @return true if the triangle has valid side lengths, otherwise false
     */
    boolean validateTriangle() {
    	//triangle.getH() will return NaN if it is unable to be calculated
    	//due to entering impossible values for triangle side lengths (ie. opposite larger than hypotenuse)
    	if (Double.isNaN(triangle.getH()) || triangle.getH() == 0 || triangle.getO() == 0 || triangle.getA() == 0) {
    		//if the triangle has 0 for h/o/a values, no errorlabel message change is needed as the validateInput() 
    		//method would already have set an errorlabel message. However, it still needs to return false here so 
    		//that the triangle is not attempted to be drawn. Therefore there is only an errorlabel message for the isNaN case.
    		if(errorLabel.getText().isEmpty()) errorLabel.setText("Opp. and Adj. can't be larger than Hypotenuse");
    		return false;
    	}
    	return true;
    }
    
	/**
	 * Checks for points that are too close to each other and moves them to prevent overlapping text. 
	 * @param h - hypotenuse label point
	 * @param o - opposite label point
	 * @param a - adjacent label point
	 * @param t - theta - angle label point
	 **/
	void moveOverlappingPoints(Point h, Point o, Point a, Point t, int minX, int minY) {
		//creation of array to loop through
		Point[] p = {h,o,a,t};
		
		//2d for loop such that all points are compared to each other
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				// no need to compare the same point to itself
				if(j==i) continue;
				
				//retrieving x and y coordinates of each point to reduce code clutter in next action
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
	 
	/**
	 * Uses the average x and y coordinate from p1 and p2 to create a new point.
	 * Sets coordinates to xBound/yBound if they do not fall within those values (causing them to not appear in canvas)
	 * @param p1 - first point to take coordinates from
	 * @param p2 - second point to take coordinates from
	 * @param xBound - maximum x coordinate of new point
	 * @param yBound - minimum y coordinate of new point
	 * @return new point object with coordinates averaged from the two points
	 */
	static Point calculateMidpoint(Point p1, Point p2, int xBound, int yBound) {
		//get the average X coordinate between the two points
		double newX = (p1.getX()+p2.getX())/2;
		
		//get the average X coordinate between the two points
		double newY = (p1.getY()+p2.getY())/2;
		
		//set coordinate to max/min bound if outside of bound
		double x = (newX > xBound) ? xBound : newX;
		double y = (newY < yBound) ? yBound : newY;
		
		Point point = new Point(x,y);
		return point;
	}
}


