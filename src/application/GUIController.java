package application;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIController {
	Stage applicationStage;
	
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
    void calculate() {
    	displayFormula();
    //	drawTriangle();
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
    	if(text.isEmpty()) return 0.0;
    	for(int i = 0; i < text.length(); i++) {
    		if(!Character.isDigit(text.charAt(i))){
        		if(text.charAt(i) == '.') dotCount++;
        		else return 0.0;
    		}
    	}
    	if(dotCount > 1) return 0.0;
    	return Double.parseDouble(text);
    }
    
    void displayFormula() {
    	Double validatedH = validate(hTf.getText());
    	Double validatedO = validate(oTf.getText());
    	Double validatedA = validate(aTf.getText());
    	Double validatedT = validate(tTf.getText());
    	
    	String formula = Calc.calculateFormula(validatedH,validatedO,validatedA,validatedT, true); 
    	System.out.println(formula);
    }
}
