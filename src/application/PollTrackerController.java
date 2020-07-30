/**
 * PollTrackerController - an abstract class to be used by all of the views
 * @author Nathaly Verwaal and Arnuv Mayank
 *
 */

package application;

import model.Factory;
import model.PollList;


public abstract class PollTrackerController {
	private PollTrackerApp app;
	
	public abstract void refresh();
	
	/**
	 * setupPollTrackerApp - initiates the app from PollTrackerApp
	 * @param app
	 */
	public void setPollTrackerApp(PollTrackerApp app) {
		this.app = app;
		refresh();
	}
	
	/**
	 * getPollList - gets the poll list from the app
	 * @return - the poll list
	 */
	protected PollList getPollList() {
		return app.getPolls();
	}
	
	/**
	 * getFactory - gets the factory from the app
	 * @return - the factory
	 */
	protected Factory getFactory() {
		return app.getFactory();
	}
	
	/**
	 * setPollList - sets the poll list in the app
	 * @param polls - the new poll list
	 */
	protected void setPollList(PollList polls) {
		app.setPolls(polls);
	}
	
	/**
	 * setFactory - sets the factory in the app
	 * @param aFactory - the new factory
	 */
	protected void setFactory(Factory aFactory) {
		app.setFactory(aFactory);
	}
}
