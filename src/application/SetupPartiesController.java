package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Party;
import model.Poll;
import model.PollList;

public class SetupPartiesController extends PollTrackerController {

	private String[] partyNames;
	private String[] currentParties;

	@FXML
	private Button clearButton;

	@FXML
	private Button submitAllPartyInfoButton;

	@FXML
	private Label selectPartyLabel;

	@FXML
	private Button setPartyInfoButton;

	@FXML
	private Label partyNameLabel;

	@FXML
	private TextField partyNameInput;

	@FXML
	private ChoiceBox<String> selectPartyChoiceBox;

	@FXML
	private Label errorMessage;

	private void clearEverything() {
		partyNameInput.clear();
		selectPartyChoiceBox.getSelectionModel().clearSelection();
	}

	/**
	 * shouldDisplay - hides all the elements but the error message that the user
	 * hasn't created a poll list if that is the case, otherwise it shows everything
	 * but said error message
	 */
	private void shouldDisplayElements(boolean hide) {
		clearButton.setVisible(hide);
		submitAllPartyInfoButton.setVisible(hide);
		selectPartyLabel.setVisible(hide);
		setPartyInfoButton.setVisible(hide);
		partyNameLabel.setVisible(hide);
		partyNameInput.setVisible(hide);
		selectPartyChoiceBox.setVisible(hide);

		errorMessage.setVisible(!hide);
	}

	private void initializeDropdown() {
		partyNames = getFactory().getPartyNames();
		selectPartyChoiceBox.setItems(FXCollections.observableArrayList(partyNames));
		currentParties = new String[partyNames.length];
		for (int i = 0; i < partyNames.length; i++) {
			currentParties[i] = partyNames[i];
		}
	}

	@FXML
	void clearClick(ActionEvent event) {
		clearEverything();
	}

	@FXML
	void setPartyInfoClick(ActionEvent event) {
		for (int i = 0; i < currentParties.length; i++) {
			if (currentParties[i].equals(selectPartyChoiceBox.getValue())) {
				currentParties[i] = partyNameInput.getText();
			}
		}
		selectPartyChoiceBox.setItems(FXCollections.observableArrayList(currentParties));
		clearEverything();
	}

	@FXML
	void submitAllPartyInfoClick(ActionEvent event) {
		PollList currentPollList = getPollList();
		for (Poll a : currentPollList.getPolls()) {
			for (int i = 0; i < currentParties.length; i++) {
				a.getParty(partyNames[i]).setName(currentParties[i]);
			}
		}

		getFactory().setPartyNames(currentParties);
		initializeDropdown();
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		if (getPollList() != null) {
			shouldDisplayElements(true);
			clearEverything();
			initializeDropdown();
		} else {
			shouldDisplayElements(false);
		}

	}

}
