package application;
	
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import model.Factory;
import model.PollList;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

/**
 * PollTrackerApp Program is a GUI that allows a user to manipulate and visualize polls.
 * 
 * @author Jamie MacDonald
 * @author Michaela Kasongo
 * @author Arnuv Mayank
 * @author Desmond O'Brien
 *
 * @version 1.0
 */
public class PollTrackerApp extends Application {
	public static final int DEFAULT_NUMBER_OF_SEATS = 345;
	public static final String FXML_FILES_LOCATION = "src/view/";
	public static final int DEFAULT_NUMBER_OF_POLLS = 5;
	
	private PollList polls;
	private Factory factory = new Factory(DEFAULT_NUMBER_OF_SEATS);
	
	/**
	 * Retrieves the List of Polls currently being tracked
	 * @return A list of polls.
	 */
	public PollList getPolls() {
		return polls;
	}
	
	/**
	 * Updates the stored instance variable with all polls currently tracked.
	 * @param aList The list of polls which will be stored.
	 */
	public void setPolls(PollList aList) {
		polls = aList;
	}
	
	/**
	 * Gets the Factory.
	 * @return The current Factory. 
	 */
	public Factory getFactory() {
		return factory;
	}
	
	/**
	 * Sets the Factory.
	 * @param aFactory
	 */
	public void setFactory(Factory aFactory) {
		factory = aFactory;
	}
	
	/**
	 * Creates a new tab in JavaFX.
	 * @param tabName
	 * @param FXMLFilename
	 * @return a tab
	 */
	private Tab createTab(String tabName, String FXMLFilename) {
		Tab aTab = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			aTab = new Tab(tabName, loader.load(new FileInputStream(FXMLFilename)));
			PollTrackerController controller = (PollTrackerController)loader.getController();
			aTab.setOnSelectionChanged (e -> controller.refresh());
			controller.setPollTrackerApp(this);
		} catch (IOException e1) {
			System.out.println("Problem loading FXML file " + FXMLFilename);
		}
		return aTab;
	}

	/**
	 * Gets the current party data stored in factory and calls on text application to make a visual layout.
	 * @param vizualizationTextArea
	 */
	private void updateVisualization(TextArea vizualizationTextArea) {
		//can only update the visualization if polls isn't empty, and the user must initialize it
		if (getPolls() != null) {	
			ByteArrayOutputStream visualization = new ByteArrayOutputStream();
			PrintStream visualizationStream = new PrintStream(visualization);
			PrintStream old = System.out;
			System.setOut(visualizationStream);
			(new TextApplication(polls)).displayPollsBySeat(factory.getPartyNames());
			System.out.flush();
			System.setOut(old);
			visualizationStream.close();
			
			//getPartyNames returns a String[], but this must be converted to a String Builder
			//for the visualization
			StringBuilder partyNames = new StringBuilder();
			partyNames.append("Party names: ");
			for (String name : factory.getPartyNames()) {
				partyNames.append(name);
				partyNames.append(", ");
			}
			partyNames.append("\n");
			
			String numOfSeats = "Number of seats: " + polls.getNumOfSeats() + "\n";
			
			String numOfPolls = "Number of polls: " + polls.getPolls().length + "\n";
			
			vizualizationTextArea.setText(partyNames + numOfSeats + numOfPolls + visualization.toString());
		}
	}
	
	/**
	 * 	Gets the latest data and stores it in a tab.
	 * 
	 * @return vizTab A tab with the organized information of all the parties.
	 */
	private Tab getDefaultVisualization() {
		// Create a stream to hold the output
		TextArea vizTextArea = new TextArea();
		updateVisualization(vizTextArea);
		Tab vizTab = new Tab("Visualize Polls", vizTextArea); 
		vizTab.setOnSelectionChanged (e -> updateVisualization(vizTextArea));
		return vizTab; 
	}
	
	/**
	 * Starts the Poll Tracker Program. 
	 * A new window opens with multiple tabs to control and display poll data.
	 * Calls from child classes to control the Polls.
	 */
	@Override
	public void start(Stage primaryStage) {
		TabPane root = new TabPane(
				createTab("Setup Poll Tracker", FXML_FILES_LOCATION + "SetupPollTrackerView.fxml"),
				createTab("Setup Parties", FXML_FILES_LOCATION + "SetupPartiesView.fxml"),
				createTab("Add Poll", FXML_FILES_LOCATION + "AddPollView.fxml"),
				createTab("Edit Poll", FXML_FILES_LOCATION + "EditPollView.fxml"),
				//createTab("Visualize Poll", FXML_FILES_LOCATION + "VisualizePollView.fxml")
				getDefaultVisualization()
									);
		
		Scene scene = new Scene(root,500,400);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * The main method for the Poll Tracker Program.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
