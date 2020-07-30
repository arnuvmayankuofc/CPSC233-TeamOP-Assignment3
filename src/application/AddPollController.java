/** 
 * AddPollController - the controller for the Add Poll View. 
 * The user can provide a name and the placement for each of the polls that the application will track.  
 * 
 * @author Michaela Kasongo, 30041372
 * @version 3.0, July 27, 2020.
 */

package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Poll;
import model.PollList;



public class AddPollController extends PollTrackerController{
    @FXML
    private ChoiceBox<String>PollPlacementChoice; 

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
    /**
	 * ClearInfo - this is the clear button, and only clears the current
	 *  info(text field and choiceBox options) upon click.
	 * @param event - the click
	 *
	 */
    void ClearInfo(ActionEvent event) { 
    	clear();
    }
    
    @FXML
    /**
	 * ClearInfo - this is the clear button, and only clears the current
	 *  info(text field and choiceBox options) upon click.
	 * @param event - the click
	 *
	 */
    void clear() {
	    /*
		 * The first 2 objects cleared are those in which the user has a choice
		 * The last one, UserInput, validates their input into the text field. 
		 */
	    PollPlacementChoice.getSelectionModel().clearSelection();
		UserInput.clear();
		UserInput.setText(""); 
	
    }
    
    /**
	 * AddPoll  - this is the add poll button. It updates the poll/party info
	 * based on the user's specifications upon click.
	 * @param event - the click
	 */
    @FXML
    void AddPoll(ActionEvent event) {
    	//get polls and create random poll with the user input name 
    	Poll[] poll =getPollList().getPolls(); 
    	Poll RandomPoll=getFactory().createRandomPoll(UserInput.getText()); 
    	
    	//get index of the option the user chose from the choiceBox and match to
    	//the random poll that will be generated.
    	int indox=PollPlacementChoice.getSelectionModel().getSelectedIndex(); 
    	poll[indox]= RandomPoll;
    	
    	//generate default factory information and index user input into pollList
    	PollList finallist= new PollList(poll.length,getPollList().getNumOfSeats());
    	for (int index = 0; index < poll.length; index++) {
    		finallist.addPoll(poll[index]);
    	}
    	setPollList(finallist); //set list
     	 }
    
    
	
    /**
	 * refresh - a definition of the abstract method in PollTrackerController
	 * This is called any time the view is changed so that the information can be updated,
	 * but this will serve as the "runner" of the class since initialize is unusable
	 */
	@Override
	public void refresh() {
		//clears user input and choice box with every refresh.
		clear();
		//collects poll names and sets them as the drop down in the ChoiceBox.
		PollPlacementChoice.getItems().removeAll(PollPlacementChoice.getItems());
		for (Poll poll: getPollList().getPolls()) {
			PollPlacementChoice.getItems().add(poll.getPollName());
		}
  
	}
}