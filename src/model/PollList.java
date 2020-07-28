package model;

/** 
 * The PollList Class has two instance variables: 
 * 1) a list of polls stored in an array.
 * 2) the number of seats available for the election.
 * 

 * @author Jamie MacDonald
 * @since July 16 2020
 
 * */

public class PollList {
	private Poll[] polls;
	private int numOfSeats;
	
	/* PollList constructor takes the number of Polls and the number of seats as arguments.
	 * It checks that the # of Polls and seats is above 1, otherwise sets default numbers.
	 * The num of Polls sets the length of the array Poll
	 * The num of Seats sets the current method 
	 * */
	public PollList(int numOP, int numOS) {
		if (numOP < 1) numOP = 5;
		if (numOS < 1) numOS = 10;
		polls = new Poll[numOP];
		this.numOfSeats = numOS;
	}
	
	// getter for instance variable numOfSeats
	public int getNumOfSeats() {
		return numOfSeats;
	}
	
	// getter for instance variable polls
	public Poll[] getPolls() {
		return polls;
	}
	
	/* addPoll method takes a Poll as an argument, and completes the following checks:
	 * 1) the Poll submitted is not equivalent to null, 
	 * 2) the Poll name does not already exist within the poll list (if so it replaces the name)
	 * 3) the poll can be added to the end of the list or the list is full 
	 * */
	public void addPoll(Poll aPoll) {
		if (aPoll == null) {
			System.out.print("Error. Please provide the poll name.");
		}
		
		else {
			int i = 0;
			Boolean exist = false;
			for (; i < polls.length && polls[i] != null; i++) { 
				if ((polls[i].getPollName()).equalsIgnoreCase(aPoll.getPollName())) { 
					polls[i] = aPoll;
					exist = true;
				} 
			}
			if (i < polls.length) {
					polls[i] = aPoll;
					exist = true;
			}
			else {
			System.out.print("The list is full. No further polls can be added.");
			}
		}
	}
	
	/* Takes an array of the party names as an argument. For every party named, the average party data is 
	 * retrieved and stored in an Aggregate Poll. The poll is then normalized and returned.
	 */
	public Poll getAggregatePoll(String[] partyNames) {
		Poll Aggregate = new Poll("Aggregate", partyNames.length);
		for (String p : partyNames) {
			Party avedParty = getAveragePartyData(p);
			Aggregate.addParty(avedParty);			
		}
		adjustPollToMaximums(Aggregate);
		return Aggregate;
	}
	
	/* Takes a Party name as an argument, then loops through all polls in the poll list to calculate average
	 * number of seats and average percentage of votes. Returns Party with averages.
	 */
	public Party getAveragePartyData(String partyName) {
		Party aParty = new Party(partyName);
		int pollCount = 0;
		float seatCount = 0;
		float percCount = 0;
		for (Poll i: polls) {
			Party p = i.getParty(partyName);
			if (p != null) {
				seatCount += p.getProjectedNumberOfSeats();
				percCount += p.getProjectedPercentageOfVotes();
				pollCount++;
			}
		}
		if (pollCount > 0) {
			aParty.setProjectedNumberOfSeats(seatCount/pollCount);
			aParty.setProjectedPercentageOfVotes(percCount/pollCount);
		}
		return aParty;
	}
	
	/* Takes a Poll as an argument. The number of seats and percentage of votes is summed from all the parties
	 * in the Poll. If either number is above a logical maximum, the data of each Party is corrected.
	 * Returns the corrected Poll. 
	 */
	public Poll adjustPollToMaximums(Poll aPoll) {
		Party [] aParties = aPoll.getPartiesSortedBySeats();
		float seatCheck = 0;
		float percCheck = 0;
		for (Party a : aParties) {
			seatCheck += a.getProjectedNumberOfSeats();
			percCheck += a.getProjectedPercentageOfVotes();
		}
		if (seatCheck > this.numOfSeats) {
			for (Party p : aParties) {
				float fractionOfSeats = p.getProjectedNumberOfSeats()/seatCheck;
				p.setProjectedNumberOfSeats(fractionOfSeats*this.numOfSeats);
			}
		}
		if (percCheck > 1.0) {
			for (Party j : aParties) {
				float fractionOfVotes = j.getProjectedPercentageOfVotes()/percCheck;
				j.setProjectedPercentageOfVotes(fractionOfVotes);
			}
		}
		return aPoll;
	}
	
	/* Cycles through all Polls in Poll List and makes a new poll with all the party names. Party names are then 
	 * stored in an array of strings, which is returned.
	 */
	public String[] getPartyNames() {
		Poll allPossible = new Poll("allPossible", 30);
		for (Poll a : polls) {
			for (Party b : a.getPartiesSortedBySeats()) {
				allPossible.addParty(b);
			}
		}
		String[] allPartyNames = new String[30];
		Party[] allParties = allPossible.getPartiesSortedBySeats();
		for (int i = 0; i < allParties.length && allParties[i] != null; i++) {
			allPartyNames[i] = allParties[i].getName();
		}
		return allPartyNames;
	}
	
	// Overrides the toString to return a string of the number of seats.
	@Override
	public String toString() {
		return "Number of seats: "+ this.numOfSeats;
	}
}
