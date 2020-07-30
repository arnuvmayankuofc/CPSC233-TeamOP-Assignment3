package application;

import model.Factory;
import model.PollList;

/**
 * The PollTrackerController is an abstract class with the methods to manipulate the PollTrackerApp and Factory.
 * 
 * 
 * @author Jamie MacDonald
 * @author Michaela Kasongo
 * @author Arnuv Mayank
 * @author Desmond O'Brien
 *
 * @version 1.0
 */
public abstract class PollTrackerController {
	private PollTrackerApp app;
	
	/**
	 * An abstract method. Updates the information on the tabs, as described in each child class.
	 */
	public abstract void refresh();
	
	/**
	 * Sets the app Program
	 * @param app An updated version of the poll tracker app to be set.
	 */
	public void setPollTrackerApp(PollTrackerApp app) {
		this.app = app;
		refresh();
	}
	
	/**
	 * Gets the app program
	 * @return The current app program.
	 */
	protected PollList getPollList() {
		return app.getPolls();
	}
	
	/**
	 * Retrieves the current Factory of poll data
	 * @return Factory with current data.
	 */
	protected Factory getFactory() {
		return app.getFactory();
	}
	
	/**
	 * Sets the list of polls currently being tracked
	 * @param polls A latest list of polls
	 */
	protected void setPollList(PollList polls) {
		app.setPolls(polls);
	}
	
	/**
	 * Sets the Factory used by the Poll Tracker app.
	 * @param aFactory A Factory object to be set.
	 */
	protected void setFactory(Factory aFactory) {
		app.setFactory(aFactory);
	}
}
