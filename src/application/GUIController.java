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
    void calculate() {
    	displayFormula();
    //	drawTriangle();
    }
    
    Double validate(Double n) {
    	return n;
    }
    
    void displayFormula() {
    	Double validatedH = validate(Double.parseDouble(hTf.getText()));
    	Double validatedO = validate(Double.parseDouble(hTf.getText()));
    	Double validatedA = validate(Double.parseDouble(hTf.getText()));
    	Double validatedT = validate(Double.parseDouble(hTf.getText()));
    	
    	String formula = Calc.calculateFormula(validatedH,validatedO,validatedA,validatedT); 
    	System.out.println(formula);
    }
}
