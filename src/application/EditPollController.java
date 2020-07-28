package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Party;
import model.Poll;

public class EditPollController extends PollTrackerController {
	
	private Poll  pollToUpdate;
	private Party partyToUpdate;
	
	@FXML
    private Button updatePartyButton;

    @FXML
    private Label pollEditText;

    @FXML
    private Label projectedNumSeatsText;

    @FXML
    private ChoiceBox<String> partyToUpdateChoice;

    @FXML
    private Label partyToUpdateText;

    @FXML
    private Label percent;

    @FXML
    private TextField projectedNumSeats;

    @FXML
    private Label totalSeats;

    @FXML
    private Label resultMessageBox;
    
    @FXML
    private Button clearButton;

    @FXML
    private ChoiceBox<String> pollChoices;

    @FXML
    private Label projVotePercentageText;

    @FXML
    private Label slash;

    @FXML
    private TextField projVotePercentage;
    
    void clearCurrentInfo() {
    	partyToUpdateChoice.getSelectionModel().clearSelection();
    	pollChoices.getSelectionModel().clearSelection();
    	projectedNumSeats.clear();
    	projVotePercentage.clear();
    	resultMessageBox.setText("");
    }
    
    void clearEverything() {
    	clearCurrentInfo();
    	partyToUpdateChoice.getItems().removeAll(partyToUpdateChoice.getItems());
    	pollChoices.getItems().removeAll(pollChoices.getItems());
    }
    
    void initializePartyOptions() {
    	Party[] parties = pollToUpdate.getPartiesSortedBySeats();
    	String partyNamesToDisplay[] = new String[pollToUpdate.getNumberOfParties()];
    	
    	partyToUpdateChoice.getSelectionModel().clearSelection();
    	partyToUpdateChoice.getItems().removeAll(partyToUpdateChoice.getItems());
    	partyToUpdate = null;
    	for (int i = 0; i < pollToUpdate.getNumberOfParties(); i++) {
    		Party party = parties[i];
    		partyNamesToDisplay[i] = party.getName() + " (" + party.getProjectedPercentageOfVotes() * 100 + "% of votes, " + party.getProjectedNumberOfSeats() + " seats)";
    	}
    	partyToUpdateChoice.setItems(FXCollections.observableArrayList(partyNamesToDisplay));
    }
    
    @FXML
    void initialize() {
    	
    }
    
    @FXML
    void clearInfo(ActionEvent event) {
    	clearCurrentInfo();
    }

    @FXML
    void updatePartyInfo(ActionEvent event) {
	    try {
    		int projectedNumSeatsInt = Integer.parseInt(projectedNumSeats.getText());
	    	float projectedVote = Float.parseFloat(projVotePercentage.getText()) / 100f;
	    	
	    	partyToUpdate.setProjectedNumberOfSeats(projectedNumSeatsInt);
	    	partyToUpdate.setProjectedPercentageOfVotes(projectedVote);
	    	resultMessageBox.setText("Updated successfully!");
	    } catch (Exception e) {
	    	resultMessageBox.setText("Incorrectly inputted. Please try again.");
	    }
    }    	
    
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		clearEverything();
		Poll[] polls = getPollList().getPolls();
		String[] pollNames = new String[polls.length];
		
		totalSeats.setText("" + getPollList().getNumOfSeats());
		for (int i = 0; i < polls.length; i++) {
			pollNames[i] = polls[i].getPollName();
		}
		/*for (Poll poll: getPollList().getPolls()) {
    		pollChoices.getItems().add(poll.getPollName());
    	}*/
		pollChoices.setItems(FXCollections.observableArrayList(pollNames));
		pollChoices.getSelectionModel().selectedIndexProperty().addListener(
			new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue observable, Number oldValue, Number newValue) {
					if (newValue.intValue() >= 0) {
						pollToUpdate = polls[newValue.intValue()];
						initializePartyOptions();
					}
				}
			}
		);
		partyToUpdateChoice.getSelectionModel().selectedIndexProperty().addListener(
			new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue observable, Number oldValue, Number newValue) {
					if (newValue.intValue() >= 0) {
						partyToUpdate = pollToUpdate.getPartiesSortedBySeats()[newValue.intValue()];
					}
				}
			}
		);
	}

}
