package application;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Poll;
import model.PollList;

/**
 * Setup Parties Controller is a subsection of the Poll Tracker App. It allows
 * the user to view and manipulate the current list of Parties that the App is
 * tracking.
 * 
 * @author Jamie MacDonald
 * @author Michaela Kasongo
 * 
 * @version 1.0
 */
public class SetupPartiesController extends PollTrackerController {

	private String[] partyNames; // The final list of Parties imported and exported to factory and other tabs.
	private String[] currentParties; // The local list of Parties used by the tab

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

	/**
	 * Clears the information previously inputed to the tab.
	 */
	private void clearEverything() {
		partyNameInput.clear();
		selectPartyChoiceBox.getSelectionModel().clearSelection();
	}

	/**
	 * shouldDisplay - hides all the elements but the error message: "User hasn't
	 * created a poll list", otherwise shows all other elements of this tab.
	 * 
	 * @param hide True if the current Poll List is empty, False if this check
	 *             passes.
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

	/**
	 * Sets the current list for the drop-down box. Stores this list in partyNames.
	 */
	private void initializeDropdown() {
		partyNames = getFactory().getPartyNames();
		selectPartyChoiceBox.setItems(FXCollections.observableArrayList(partyNames));
		currentParties = new String[partyNames.length];
		for (int i = 0; i < partyNames.length; i++) {
			currentParties[i] = partyNames[i];
		}
	}

	/**
	 * Acts after the user clicks on the "Clear" Button. Calls the clearEverything
	 * method to erase user input from the tab.
	 * 
	 * @param event Clear Button is clicked.
	 */
	@FXML
	void clearClick(ActionEvent event) {
		clearEverything();
	}

	/**
	 * Is called when the user clicks on the "Set party info" Button. Calls the list
	 * of current parties on the tab and replaces the name selected by the user in
	 * the Choicebox with the name typed in the text field.
	 * 
	 * @param event Set party info Button is clicked.
	 */
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

	/**
	 * Is called when the user click on the "Submit all party info" Button. Calls
	 * the current list of parties as named by the user and updates the list of
	 * parties in the {@code Factory} and all other tabs.
	 * 
	 * @param event Submit all party info Button is clicked.
	 */
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

	/**
	 * Refreshes the data on this tab. The latest {@code PollList} is called by the
	 * initializeDropdown method, and checked by the shouldDisplayElements method.
	 * The fields altered by the user are reset to blank.
	 */
	@Override
	public void refresh() {
		if (getPollList() != null) {
			shouldDisplayElements(true);
			clearEverything();
			initializeDropdown();
		} else {
			shouldDisplayElements(false);
		}

	}

}
