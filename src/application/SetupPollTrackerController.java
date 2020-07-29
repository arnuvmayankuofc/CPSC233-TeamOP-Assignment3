/**
 * SetupPollController - the controller for the SetupPollTracker view. 
 * Let's the user create a factory for the application with a number of seats specified by the user's input
 * into a text-field.
 * The factory creates a random PollList for the application with the factory's number of seats, and the user's desired
 * number of parties.
 * Assigns an integer name for each Party.
 * 
 * @Author Desmond O'Brien, 30064340
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

    /**
     * clearOptions - invokes the clearAllOptions method when the 
     * 				  	Button clearButton is clicked
     * @param event
     */
    @FXML
    private void clearOptions(ActionEvent event) {
    	clearAllOptions();
    }
    
    /**
     * clearAllOptions - clears all data entered by the user in each text-field
     */
    private void clearAllOptions() {
    	numberOfSeatsField.clear();
    	numberOfPartiesField.clear();
    	numberOfPollsField.clear();
    }
    
    /**
     * updatePolls - Creates a factory object with number of seats entered into view's text-field
     * 				 	Factory object then creates a new random PollList
     * @param event
     */
    @FXML
    private void updatePolls(ActionEvent event) {
    	
    	//Instance variables that store the data entered into each text-field as integers
    	int numberOfPolls = Integer.parseInt(numberOfPollsField.getText());
    	int numberOfSeats = Integer.parseInt(numberOfSeatsField.getText());
    	int numberOfParties  = Integer.parseInt(numberOfPartiesField.getText());
    	
    	//Create a new factory for the application with number of seats entered into text-field
    	Factory factory  = new Factory(numberOfSeats);
    	
    	/*
    	 * Initialized string array with length of number of parties entered in text-field
    	 * for-loop sets each index of array to an integer starting at 1 and ending at the 
    	 * length of the array
    	 */
    	String[] str = new String[numberOfParties];
    	for (int i = 0; i < numberOfParties; i++) {
    		str[i] = "" + (int)(i + 1);
    	}
    	
    	/*
    	 * Set names of parties factory to the previous string array indices
    	 * set Application's pollList to a random pollList created by the factory.
    	 */
    	factory.setPartyNames(str);
    	setFactory(factory);
    	setPollList(factory.createRandomPollList(numberOfPolls));
    }
    
    /**
     * refresh - method that overrides the abstract method refresh from PollTrackerController
     * Invokes clearAllOptions method to clear text-fields each time the user changes the application's view
     */
	@Override
	public void refresh() {
		clearAllOptions();
	}

}