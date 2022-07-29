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
		
		Point h = new Point(triangle.getHa(), triangle.getHo(), 335, 10);
		Point o = new Point(triangle.getHo(), triangle.getOa(), 335, 10);
		Point a = new Point(triangle.getHa(), triangle.getOa(), 335, 10);
		Point t = new Point(triangle.getHa(), triangle.getHa(), 335, 10);
		
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
   
    void createTriangle() {
    	Double validatedH = validateInput("Hypotenuse", hTf.getText(), false);
    	Double validatedO = validateInput("Opposite", oTf.getText(), false);
    	Double validatedA = validateInput("Adjacent", aTf.getText(), false);
    	Double validatedT = validateInput("Angle θ", tTf.getText(), true);
    	boolean degrees = degreesToggleButton.isSelected() ? true : false;
    	triangle = new Triangle(validatedH,validatedO,validatedA,validatedT, degrees); 
    }
    
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
    
    Double validateInput(String textField, String text, boolean isAngle) {
    	int dotCount = 0;
    	int dashCount = 0;
		String errorDescription = "";
    	if(text.isEmpty()) return 0.0;
    	for(int i = 0; i < text.length(); i++) {
    		if(!Character.isDigit(text.charAt(i))){
        		if(text.charAt(i) == '.') dotCount++;
        		else if(text.charAt(i) == '-' && i == 0) dashCount++;
        		else return 0.0;
    		}
    	}
    	if((isAngle && radiansToggleButton.isSelected() && Math.abs(Double.parseDouble(text)) >= Math.PI/2)
    			|| (isAngle && degreesToggleButton.isSelected() && Math.abs(Double.parseDouble(text)) >= 90) 
    			|| dotCount > 1 || dashCount > 1) {
    		if (isAngle) errorDescription = " must be less than 90° or π/2";
    		else if(dotCount > 1) errorDescription = " can only contain one decimal point.";
    		else if(dashCount > 1) errorDescription = " can only contain one negative sign.";
    		errorLabel.setText(textField + errorDescription);
    		return 0.0;
    	}
    	return Double.parseDouble(text);
    }
    
    boolean validateTotalInputs() {
    	int counter = 0;
    	if (hTf.getText().isEmpty()) counter++;
    	if (oTf.getText().isEmpty()) counter++;
    	if (aTf.getText().isEmpty()) counter++;
    	if (tTf.getText().isEmpty()) counter++;
    	if (counter != 2) {
    		errorLabel.setText("Only enter values for two components.");
    		return false;
    	}
    	return true;
    }
    
    boolean validateTriangle() {
    	if (Double.isNaN(triangle.getH()) && errorLabel.getText().isEmpty()) {
    		errorLabel.setText("Opp. and Adj. can't be more than or equal to Hyp.");
    		return false;
    	}
    	return true;
    }
    
	static void moveOverlappingPoints(Point h, Point o, Point a, Point t) {
		Point[] p = {h,o,a,t};
		int marginY = 15;
		int marginX = 50;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(j==i) continue;
				if(Math.abs(p[i].getY() - p[j].getY()) < marginY && Math.abs(p[i].getX() - p[j].getX()) < marginX) {
					if(p[i].getY() <= p[j].getY()) p[j].setY(p[j].getY() + marginY + 2 - (p[j].getY()-p[i].getY()));
					else p[i].setY(p[i].getY() + marginY + 2 - (p[i].getY()-p[j].getY()));
				}
			}
		}
	}
}


