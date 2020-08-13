package model;

import java.util.ArrayList;

/**
 * The PollList Class has two instance variables: 1) a list of polls stored in
 * an array. 2) the number of seats available for the election.
 * 
 * 
 * @author Jamie MacDonald
 * @author Michaela Kasongo
 * @author Arnuv Mayank
 * @since 1.0
 * 
 */
public class PollList {
	private Poll[] polls;
	private int numOfSeats;

	/**
	 * Constructs a new PollList with a specified number of Polls and number of
	 * Seats. It checks that the # of Polls and seats is above 1, otherwise sets
	 * default numbers.
	 * 
	 * @param numOP the number of Polls to be included.
	 * @param numOS the number of Seats in the election
	 * 
	 * @since 1.0
	 */
	public PollList(int numOP, int numOS) {
		if (numOP < 1)
			numOP = 5;
		if (numOS < 1)
			numOS = 10;
		polls = new Poll[numOP];
		this.numOfSeats = numOS;
	}

	/**
	 * Fetches the number of seats in the invoked PollList.
	 * 
	 * @return The number of seats.
	 * @since 1.0
	 */
	public int getNumOfSeats() {
		return numOfSeats;
	}

	/**
	 * Fetches the list of Polls in the invoked PollList.
	 * 
	 * @return the array containing the list of polls.
	 * @since 1.0
	 */
	public Poll[] getPolls() {
		return polls;
	}

	/**
	 * Completes the following checks on a Poll before adding to the invoked
	 * PollList:
	 * <li>1) the Poll submitted is not equivalent to null,
	 * <li>2) the Poll name does not already exist within the poll list (if so it
	 * replaces the name)
	 * <li>3) the poll can be added to the end of the list or the list is full <br>
	 * 
	 * @param aPoll the {@code Poll} to be added to this list.
	 * @since 1.0
	 */
	public void addPoll(Poll aPoll) throws PollListFullException {
		if (aPoll == null)
			System.out.print("Error. Please provide the poll name.");
		else {
			int i = 0;
			Boolean exist = false;
			for (; i < polls.length && !exist; i++) {
				if (polls[i] == null) {
					polls[i] = aPoll;
					exist = true;
				}
				else if ((polls[i].getPollName()).equalsIgnoreCase(aPoll.getPollName())) {
					polls[i] = aPoll;
					exist = true;
				}
			}
			if (!exist)
				throw new PollListFullException("The PollList is Full. Cannot add" + aPoll.getPollName() + ".");

		}
	}

	/**
	 * For each party named in a list, the average party data is retrieved and
	 * stored in an Aggregate Poll. The poll is then normalized.
	 * 
	 * @param partyNames the array list of party names.
	 * @return A {@code Poll} containing the named Parties with averaged data.
	 * 
	 * @since 1.0
	 */
	public Poll getAggregatePoll(String[] partyNames) {
		Poll Aggregate = new Poll("Aggregate", partyNames.length);
		for (String p : partyNames) {
			Party avedParty = getAveragePartyData(p);
			
			/*
			 * if there is an error generating the aggregate polls with party names 
			 * then we chose for this method to catch the exception instead of throwing it because PollList is the
			 * most "outer" of the classes (PollList > Poll > Party), so it is the last in this chain
			 * and has nowhere else to go for this error to be handled.
			 */	
			try {
				Aggregate.addParty(avedParty);
			} catch (PollFullException e) {
				e.printStackTrace();
			}
		}
		
		/*
		 * if there is an error adjusting polls to their maxima, then we chose for
		 * this method to catch the exception instead of throwing it because PollList is the
		 * most "outer" of the classes (PollList > Poll > Party), so it is the last in this chain
		 * and has nowhere else to go for this error to be handled
		 */
		try {
			adjustPollToMaximums(Aggregate);
		} catch (InvalidPartyDataException ipde) {
			ipde.printStackTrace();
		}
		return Aggregate;
	}

	/**
	 * Loops through all polls in the {@code PollList} to calculate average number
	 * of seats and average percentage of votes for a given {@code Party}.f
	 * 
	 * @param partyName a {@code Party} to be retrieved and averaged.
	 * @return {@code Party} with average data.
	 * 
	 * @since 1.0
	 */
	public Party getAveragePartyData(String partyName) {
		Party aParty = new Party(partyName);
		int pollCount = 0;
		float seatCount = 0;
		float percCount = 0;
		for (Poll i : polls) {
			Party p = i.getParty(partyName);
			if (p != null) {
				seatCount += p.getProjectedNumberOfSeats();
				percCount += p.getProjectedPercentageOfVotes();
				pollCount++;
			}
		}
		if (pollCount > 0) {
			/*
			 * if there is an error generating the average projected votes/seats, then we chose for
			 * this method to catch the exception instead of throwing it because PollList is the
			 * most "outer" of the classes (PollList > Poll > Party), so it is the last in this chain
			 * and has nowhere else to go for this error to be handled
			 */
			try {	
				aParty.setProjectedNumberOfSeats(seatCount / pollCount);
				aParty.setProjectedPercentageOfVotes(percCount / pollCount);
			} catch (InvalidPartyDataException ipde) {
				ipde.printStackTrace();
			}
		}
		return aParty;
	}

	/**
	 * Sums the number of seats and percentage of votes from all the parties in a
	 * given {@code Poll}. If either number is above a logical maximum, the data of
	 * each {@code Party} is corrected.
	 * 
	 * @param aPoll the {@code Poll} to be corrected.
	 * @return {@code Poll} with corrected seats and vote percentage.
	 * @throws InvalidPartyDataException 
	 * @since 1.0
	 */
	private Poll adjustPollToMaximums(Poll aPoll) throws InvalidPartyDataException {
		Party[] aParties = aPoll.getPartiesSortedBySeats();
		float seatCheck = 0;
		float percCheck = 0;
		for (Party a : aParties) {
			seatCheck += a.getProjectedNumberOfSeats();
			percCheck += a.getProjectedPercentageOfVotes();
		}
		// since this is simply a helper method, it will throw the error so that the calling method
		// getAggregatePolls can handle it
		if (seatCheck > this.numOfSeats) {
			for (Party p : aParties) {
				float fractionOfSeats = p.getProjectedNumberOfSeats() / seatCheck;
				p.setProjectedNumberOfSeats(fractionOfSeats * this.numOfSeats);
			}
		}
		if (percCheck > 1.0) {
			for (Party j : aParties) {
				float fractionOfVotes = j.getProjectedPercentageOfVotes() / percCheck;
				j.setProjectedPercentageOfVotes(fractionOfVotes);
			}
		}
		return aPoll;
	}

	/**
	 * Cycles through all Polls in the {@code PollList} and collects all novel Party
	 * names to an ArrayList.
	 * 
	 * @return Array of all Party names in all Polls.
	 * @since 2.0
	 */
	public String[] getPartyNames() {
		ArrayList<String> allPartyNames = new ArrayList<String>();
		for (Poll a : polls) {
			for (Party b : a.getPartiesSortedBySeats()) {
				if (!allPartyNames.contains(b.getName())) {
					allPartyNames.add(b.getName());
				}
			}
		}
		return (String[]) allPartyNames.toArray();
	}

	// Overrides the toString to return a string of the number of seats.
	@Override
	public String toString() {
		return "Number of seats: " + this.numOfSeats;
	}
}
