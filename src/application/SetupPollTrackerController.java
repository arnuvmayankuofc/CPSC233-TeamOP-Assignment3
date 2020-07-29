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
    void clearOptions(ActionEvent event) {
    	numberOfSeatsField.clear();
    	numberOfPartiesField.clear();
    	numberOfPollsField.clear();
    }

    @FXML
    private void updatePolls(ActionEvent event) {
    	PollTrackerApp app = new PollTrackerApp();
    	super.setPollTrackerApp(app);
    	PollList list = new PollList(Integer.parseInt(numberOfPollsField.getText()), Integer.parseInt(numberOfSeatsField.getText()));
    	Factory factory  = new Factory(Integer.parseInt(numberOfSeatsField.getText()));
    	super.setPollList(list);
    	super.setFactory(factory);
    	
    	String[] str;
    	for (int i = 0; i < Integer.parseInt(numberOfPartiesField.getText());) {
    		
    		str[i] = Integer.
    		i++;
    	}
    		factory.setPartyNames(str);
    }

	@Override
	public void refresh() {
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}