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
    private ToggleButton valueToggleButton;
    @FXML
    private ToggleButton variableToggleButton;
    @FXML
    private Canvas canvas = new Canvas();
    @FXML
    private Text infoText;
    @FXML
    private Label errorLabel; 
	
    @FXML
    void calculate() {
    	//clearing the error label as triangle calculations are independant of each other
    	errorLabel.setText("");
    	
    	//creating a new triangle or VariableTriangle object for us to manipulate as needed
    	//triangle is for calculating based off numeric values, whereas VariableTriangle is 
    	//a child of Triangle and overrides certain methods in order to handle string entries.
    	if(variableToggleButton.isSelected()) triangle = new VariableTriangle();
    	else triangle = new Triangle();
    	
    	if(validateTotalInputs()) {
    		//computing for the missing values of the triangle using validated user inputs
    		calculateTriangle();
    		
    		//setting the current triangle sidelength/angle information to string values in the
    		//triangle before they are manipulated by further methods for display purposes
    		//This method is overridden in the VariableTriangle class in order to store 
    		//algebraic formulas instead of calculated values for the sidelengths/angles.
    		triangle.storeTriangleInfo();
    		
    		//scaling the triangle to a suitable size for the canvas
    		triangle.scaleTriangle();
    		
    		//calculating the coordinates of each of the (scaled) triangle's three corners.
    		triangle.calculatePointCoordinates();
    		
    		//moving each of the triangle's points to let us display triangles with points of 
    		//negative coordinates on the main quadrant of the canvas (the canvas only shows +,+)
    		triangle.moveToPositiveQuadrant();
    		
    		//validating that the triangle no longer has any sidelengths/angle at a value of 0
    		if(triangle.validateTriangle()) {
    			//creating the necessary component for drawing on the canvas
        		GraphicsContext gc = canvas.getGraphicsContext2D();
        		
        		//drawing the outline of the triangle
            	drawTriangle(gc);
            	
            	//moving and setting labels according to the values of the triangle
            	setTriangleLabels(gc);
            	
            	//putting the solve method in the text area of the GUI.
            	setInfoText();
    		} else {
        		if(errorLabel.getText().isEmpty()) 
            		//if the triangle has 0 for h/o/a values, no errorlabel message change is needed as the
        			//validateInput() method would already have set an errorlabel message, and should not be 
        			//overwritten. Therefore there is only an errorlabel message for the isNaN case of validateTriangle().
        			errorLabel.setText("Opp. and Adj. can't be larger than or equal to Hyp.");
    		}
    	}
    }
    
    /**
     * Validates the values in the text fields entered by the user, then asks the triangle
     * object to compute for it's own missing values.
     */
    void calculateTriangle() {
    	boolean degrees = degreesToggleButton.isSelected() ? true : false;
    	Double validatedH = validateInput("Hypotenuse", hTf.getText(), degrees);
    	Double validatedO = validateInput("Opposite", oTf.getText(), degrees);
    	Double validatedA = validateInput("Adjacent", aTf.getText(), degrees);
    	Double validatedT = validateInput("Angle θ", tTf.getText(), degrees);
    	triangle.calculateMissingValues(validatedH,validatedO,validatedA,validatedT, degrees); 
    }
    
    /**
     * @param textField - The label of the textField that is currently being validated
     * @param text - the text within the textField that is currently being validated
     * @param degrees - whether or not the the degreesToggleButton is currently selected
     * @return double value suitable for the triangle object; text parsed as a double or 
     * 1 (for triangles of VariableTriangle class) if the text was valid, otherwise 0.
     */
    double validateInput(String textField, String text, boolean degrees) {
    	//storing the user input for future use
		triangle.storeUserInputs(textField, text);
		
		//setting the value to 0 is my default for a value that must be calculated for,
		//this does not cause any issues since a valid triangle will never have 0 as a 
		//value for any of it's side lengths or angles.
		if(text.isEmpty()) return 0;
		
		//checkError is overridden in the child class of Triangle as the criteria for
		//valid inputs change based on whether it is solving using values or variables names.
    	String errorMessage = triangle.checkError(textField, text, degrees);
    	
    	//given that there is no error message, the input is valid, so a non-0 double will
    	//be returned, either to be used in calculations or to signify that the user did
    	//enter a value for this sidelength/angle.
    	if(errorMessage.equals("")) {
    		try {return Double.parseDouble(text);} 
    		catch(NumberFormatException e) {return 1;}
    	}
    	
    	//If the program has gotten to this point, the input is not valid, so the error 
    	//message will be displayed and the default value of 0 will be returned.
    	errorLabel.setText(errorMessage);
    	return 0.0;
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
		int overlapMinX = 100;
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
     * Toggles the value and variable button in the GUI such that one and only one is always active.
     * @param trigger - value or variable GUI button that is clicked by the user
     */
    @FXML
    void toggleValueMode(ActionEvent trigger) {
    	//determines which button was clicked, and sets it to true, 
    	//while also setting the other button false.
    	if(trigger.getSource().equals(valueToggleButton)) {
    		variableToggleButton.setSelected(false);
    		valueToggleButton.setSelected(true);
    	} else {
    		valueToggleButton.setSelected(false);
    		variableToggleButton.setSelected(true);
    	}
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


