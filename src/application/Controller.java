package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class Controller implements Initializable {

	@FXML private TextField loopCounter;
	@FXML private ToggleGroup toggleGroup1;
	@FXML private RadioButton auto1;
	@FXML private RadioButton auto2;

	RobotComms robotComms = null;

	public Controller() {
		robotComms = new RobotComms(this);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		toggleGroup1.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
		        Toggle old_toggle, Toggle new_toggle) {
		            if (toggleGroup1.getSelectedToggle() != null) {
		            	System.out.println("Selected toggle " + ((RadioButton) toggleGroup1.getSelectedToggle()).getText());
		            	robotComms.setAutoSelection(((RadioButton) toggleGroup1.getSelectedToggle()).getText());
		            }                
		        }
		});
	}
	
	public void setLoopCounter(double value) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loopCounter.setText("" + value);
			}});
		System.out.println(value);
	}

}
