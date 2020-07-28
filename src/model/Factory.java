package model;

import java.lang.Math;
import java.util.Random;

/**
 * Class Factory, creates random party names and seats
 * 
 * @author Victor Campos, 30106934
 *
 */
public class Factory {

	// Instance variables

	private int numOfSeats;
	String[] partyNames = { "BQ", "CPC", "Green", "Liberal", "NDP", "PPC", "Rhinoceros" };

	public Factory(int numOfSeats) {
		this.numOfSeats = numOfSeats;

		if (numOfSeats > 0) {
			this.numOfSeats = numOfSeats;
		} else {
			System.out.println("Error number of seats cannot be less than 1");
		}
	}

	public void setPartyNames(String[] names) {
		partyNames = names;
	}

	public String[] getPartyNames() {
		return partyNames;
	}

	/**
	 * createRandomParty, creates a random party, confined to a maximum number and
	 * percentage vote
	 * 
	 * @param partyName
	 * @param maximumSeats
	 * @param maximumPercent
	 * @return creates a new party object using these properties
	 */
	public Party createRandomParty(String partyName, int maximumSeats, int maximumPercent) {
		Random rand = new Random();
		int numSeatsInParty = rand.nextInt(maximumSeats + 1);
		if (maximumSeats != 0) {	
			float percentageVotesOfParty = (float) (rand.nextInt((int)(maximumPercent * 100) + 1) / 100.0);
			while (Math.abs(((float)(numSeatsInParty) / numOfSeats) - percentageVotesOfParty) > 0.05) {
				percentageVotesOfParty = (float) (rand.nextInt((int)(maximumPercent * 100) + 1) / 100.0);
			}
			return new Party(partyName, numSeatsInParty, percentageVotesOfParty);
		}
		return new Party(partyName, 0, 0);
	}

	/**
	 * createRandomPoll makes a poll based of a series of random parties
	 * 
	 * @param name
	 * @return
	 */
	public Poll createRandomPoll(String name) {
		Poll poll = new Poll(name, partyNames.length);
		int remainingSeatsInPoll = numOfSeats;
		int seatsConsumedInParty = -1;

		for (int i = 0; i < partyNames.length; i++) {
			Party currentParty = createRandomParty(partyNames[i], remainingSeatsInPoll, 1);
			seatsConsumedInParty = (int) currentParty.getProjectedNumberOfSeats();
			poll.addParty(currentParty);
			remainingSeatsInPoll -= seatsConsumedInParty;

		}
		return poll;
	}

	/**
	 * createRandomPollList makes a polllist based of the series of polls
	 * 
	 * @param numOfPolls
	 * @return
	 */
	public PollList createRandomPollList(int numOfPolls) {
		PollList polls = new PollList(numOfPolls, numOfSeats);

		for (int i = 0; i < numOfPolls; i++) {
			Poll newPoll = createRandomPoll("Poll " + (i + 1));
			polls.addPoll(newPoll);
		}
		return polls;
	}
	public PollList promptForPollList(int numOfPolls) {
		return createRandomPollList(numOfPolls);
	}


}