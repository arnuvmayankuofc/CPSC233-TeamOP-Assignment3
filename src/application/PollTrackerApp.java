/**
 * PollTrackerApp - this contains the program-wide copies of a poll list and
 * a factory. It puts together all of the views into a single scene.
 * @author Nathaly Verwaal, Arnuv Mayank
 *
 */
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


public class PollTrackerApp extends Application {
	public static final int DEFAULT_NUMBER_OF_SEATS = 345;
	public static final String FXML_FILES_LOCATION = "src/view/";
	public static final int DEFAULT_NUMBER_OF_POLLS = 5;
	
	private PollList polls;
	private Factory factory = new Factory(DEFAULT_NUMBER_OF_SEATS);
	
	/**
	 * getPolls - getter for polls
	 * @return - polls
	 */
	public PollList getPolls() {
		return polls;
	}
	
	/**
	 * setPolls - setter for polls
	 * @param aList - the poll list it will set polls to
	 */
	public void setPolls(PollList aList) {
		polls = aList;
	}
	
	/**
	 * getFactory - getter for the factory
	 * @return - factory
	 */
	public Factory getFactory() {
		return factory;
	}
	
	/**
	 * setFactory - setter for the factory
	 * @param aFactory - the factory it will set factory to
	 */
	public void setFactory(Factory aFactory) {
		factory = aFactory;
	}
	
	/**
	 * createTab - creates a tab for the scene based on the FXML specifications
	 * @param tabName
	 * @param FXMLFilename
	 * @return - the tab to be included
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
	 * updateVisualization - 
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
	 * getDefaultVisualization - uses visualization provided to construct the tab
	 * @return - the visualization tab
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
	 * start - creates the scene
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
	
	public static void main(String[] args) {
		launch(args);
	}
}
