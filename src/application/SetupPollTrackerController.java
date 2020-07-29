/**
 * 
 * 
 * 
 * 
 */




package application;


import model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SetupPollTrackerController extends PollTrackerController {

    @FXML
    private Button submitButton;

    @FXML
    private Label numberOfPartiesLable;

    @FXML
    private Button clearButton;

    @FXML
    private TextField numberOfPartiesField;

    @FXML
    private TextField numberOfPollsField;

    @FXML
    private Label pollsToTrackLabel;

    @FXML
    private Label seatsAvailableLabel;

    @FXML
    private TextField numberOfSeatsField;

    @FXML
    private void clearOptions(ActionEvent event) {
    	clearAllOptions();
    }

    private void clearAllOptions() {
    	numberOfSeatsField.clear();
    	numberOfPartiesField.clear();
    	numberOfPollsField.clear();
    }
    
    
    @FXML
    private void updatePolls(ActionEvent event) {
    	
    	int numberOfPolls = Integer.parseInt(numberOfPollsField.getText());
    	int numberOfSeats = Integer.parseInt(numberOfSeatsField.getText());
    	int numberOfParties  = Integer.parseInt(numberOfPartiesField.getText());
    	
    	Factory factory  = new Factory(numberOfSeats);
    	
    	String[] str = new String[numberOfParties];
    	for (int i = 0; i < numberOfParties; i++) {
    		str[i] = "" + (int)(i + 1);
    	}
    	
    	factory.setPartyNames(str);
    	setFactory(factory);
    	setPollList(factory.createRandomPollList(numberOfPolls));
    }

	@Override
	public void refresh() {
		clearAllOptions();
	}

}