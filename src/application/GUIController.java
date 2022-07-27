package application;

import java.util.ArrayList;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
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
    void calculate() {
    	displayFormula();
    	GraphicsContext gc = canvas.getGraphicsContext2D();
    	drawTriangle(gc);
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
	}
   
    
    void displayFormula() {
    	Double validatedH = validate(hTf.getText());
    	Double validatedO = validate(oTf.getText());
    	Double validatedA = validate(aTf.getText());
    	Double validatedT = validate(tTf.getText());
    	boolean degrees = degreesToggleButton.isSelected() ? true : false;
    	triangle = Calc.solveValues(validatedH,validatedO,validatedA,validatedT, degrees); 
    	infoText.setText(triangle.getInfo());
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
    
    Double validate(String text) {
    	int dotCount = 0;
    	int dashCount = 0;
    	if(text.isEmpty()) return 0.0;
    	for(int i = 0; i < text.length(); i++) {
    		if(!Character.isDigit(text.charAt(i))){
        		if(text.charAt(i) == '.') dotCount++;
        		else if(text.charAt(i) == '-') dashCount++;
        		else return 0.0;
    		}
    	}
    	if(dotCount > 1 || dashCount > 1) return 0.0;
    	return Double.parseDouble(text);
    }
}
