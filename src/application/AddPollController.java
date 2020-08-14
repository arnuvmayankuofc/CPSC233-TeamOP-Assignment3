
package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import model.Poll;
import model.PollList;
import model.PollListFullException;

/**
 * Controls elements of the {@code AddPollView.fxml}. Allows the user to provide
 * a name and the placement for each of the polls that the application will
 * track.
 * 
 * @author Michaela Kasongo
 * @author Jamie MacDonald
 * 
 * @version 4.0, July 27, 2020.
 */

public class AddPollController extends PollTrackerController {
	@FXML
	private ChoiceBox<String> PollPlacementChoice;

	@FXML
	private Button AddPollButton;

	@FXML
	private Button clearButton;

	@FXML
	private TextField UserInput;

	@FXML
	private Label PollNameText;

	@FXML
	private Label PollPlacement;

	@FXML
	private Label errorMessage;

	@FXML
	private Label selectionError;

	/**
	 * Controls whether the elements controlling the poll are shown to the user.
	 * 
	 * @param show truth value of "should be shown?"
	 */
	private void shouldDisplayElements(boolean show) {
		PollPlacementChoice.setVisible(show);
		AddPollButton.setVisible(show);
		clearButton.setVisible(show);
		UserInput.setVisible(show);
		PollNameText.setVisible(show);
		PollPlacement.setVisible(show);
	}

	/**
	 * Controls whether the {@code errorMessage} should be shown.
	 * 
	 * @param show truth value of "should be shown?"
	 */
	private void shouldDisplayError(boolean show) {
		errorMessage.setVisible(show);
	}

	@FXML
	/**
	 * Clears the current info (text field and choiceBox options). Called when user
	 * clicks the {@code clearButton}.
	 * 
	 * @param event - the click
	 *
	 */
	void ClearInfo(ActionEvent event) {
		clear();
	}

	@FXML
	/**
	 * Clears all pertinent objects on the Scene of their contents
	 */
	void clear() {
		/*
		 * The first 2 objects cleared are those in which the user has a choice The last
		 * one, UserInput, validates their input into the text field.
		 */
		PollPlacementChoice.getSelectionModel().clearSelection();
		UserInput.clear();
		UserInput.setText("");
		selectionError.setText("");

	}

	/**
	 * Activated when the AddPollButton is clicked. Results in the creation of a new
	 * poll in the applications list of polls with some default information for all
	 * the parties in the election. If the user makes a duplicate entry, or does not
	 * make a selection error messages are shown. Handles
	 * {@code ArrayIndexOutOfBoundsException} and {@code PollListFullException}.
	 * 
	 * @param event - the click
	 *
	 */
	@FXML
	void AddPoll(ActionEvent event) {
		/*
		 * create a random poll with the user input name provided. get index of the drop
		 * down options and set as index of random Poll
		 */
		try {
			Poll[] polls = getPollList().getPolls();
			boolean valid = true;
			if (UserInput.getText().isEmpty()) {
				valid = false;
				selectionError.setTextFill(Paint.valueOf("blue"));
				selectionError.setText("No name was entered. Please enter a name.");
			}
			for (Poll poll : polls) {
				if (poll.getPollName().equals(UserInput.getText())) {
					valid = false;
					selectionError.setTextFill(Paint.valueOf("red"));
					selectionError.setText("This name already exists. Please choose a different name.");
				}
			}
			Poll RandomPoll = getFactory().createRandomPoll(UserInput.getText());
			int index = PollPlacementChoice.getSelectionModel().getSelectedIndex();
			polls[index] = RandomPoll;

			// add new random poll to the poll List along with default party information
			if (valid) {
				PollList finallist = new PollList(polls.length, getPollList().getNumOfSeats());
				for (int i = 0; i < polls.length; i++) {
					finallist.addPoll(polls[i]);
				}
				setPollList(finallist);
				refresh();
			}

			/*
			 * if this was ever called it would display and error message and reset, but the
			 * user is just replacing polls in the set pollList length.
			 */
		} catch (PollListFullException plfe) {
			plfe.printStackTrace();
			selectionError.setTextFill(Paint.valueOf("red"));
			selectionError.setText("Error: the PollList is Full. Please try again.");

			/*
			 * This exception is thrown when the user does not select a poll to replace.
			 * Then index of the poll array is null and nothing is replaced.
			 */
		} catch (ArrayIndexOutOfBoundsException aioobe) {
			aioobe.printStackTrace();
			selectionError.setTextFill(Paint.valueOf("orange"));
			selectionError.setText("Error: No selection was made. Please make a selection and try again.");
		}
	}

	/**
	 * Overrides method in {@code PollTrackerController}. Is called any time the
	 * view is changed so that the information can be updated, but will serve as the
	 * "runner" of the class since initialize is unusable.
	 */
	@Override
	public void refresh() {
		// if there's no poll list, they can't add a poll
		if (getPollList() != null) {
			shouldDisplayElements(true);
			shouldDisplayError(false);
			// clears user input and choice box with every refresh.
			clear();
			// collects poll names and sets them as the drop down in the ChoiceBox.
			PollPlacementChoice.getItems().removeAll(PollPlacementChoice.getItems());
			for (Poll poll : getPollList().getPolls()) {
				PollPlacementChoice.getItems().add("replace" + " " + poll.getPollName());
			}
		} else if (getPollList() == null) {
			shouldDisplayElements(false);
			shouldDisplayError(true);
		}

	}
}
