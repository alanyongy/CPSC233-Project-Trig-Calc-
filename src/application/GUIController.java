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
    
    void displayFormula() {
    	String formula = Calc.calculateFormula(Double.parseDouble(hTf.getText()), Double.parseDouble(aTf.getText()), 
    											Double.parseDouble(oTf.getText()), Double.parseDouble(tTf.getText())); 
    	System.out.println(formula);
    }
}
