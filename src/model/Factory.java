package model;

import java.lang.Math;
import java.util.Random;

/**
 * Class Factory, creates random party names and seats
 * 
 * @author Victor Campos
 * @author Jamie MacDonald
 *
 */
public class Factory {
	private int numOfSeats;
	String[] partyNames = { "BQ", "CPC", "Green", "Liberal", "NDP", "PPC", "Rhinoceros" };

	/**
	 * Constructs a new {@code Factory} with specified number of Election Seats.
	 * 
	 * @param numOfSeats the number of election seats.
	 */
	public Factory(int numOfSeats) {
		this.numOfSeats = numOfSeats;

		if (numOfSeats > 0) {
			this.numOfSeats = numOfSeats;
		} else {
			System.out.println("Error number of seats cannot be less than 1");
		}
	}

	/**
	 * Takes a list of party names and stores in the Factory.
	 * 
	 * @param names the array of names to be saved.
	 */
	public void setPartyNames(String[] names) {
		partyNames = names;
	}

	/**
	 * Fetches the list of party names stored in the Factory.
	 * 
	 * @return array of party names.
	 */
	public String[] getPartyNames() {
		return partyNames;
	}

	/**
	 * Creates a new party object with a random number of seats and random
	 * percentage of votes, confined to specified maximums.
	 * 
	 * @param partyName      a party's name.
	 * @param maximumSeats   the maximum number of seats.
	 * @param maximumPercent the maximum percent of votes.
	 * @return The {@code Party} with random properties.
	 */
	public Party createRandomParty(String partyName, int maximumSeats, int maximumPercent) {
		Random rand = new Random();
		int numSeatsInParty = rand.nextInt(maximumSeats + 1);
		if (maximumSeats != 0) {
			float percentageVotesOfParty = (float) (rand.nextInt((int) (maximumPercent * 100) + 1) / 100.0);
			while (Math.abs(((float) (numSeatsInParty) / numOfSeats) - percentageVotesOfParty) > 0.05) {
				percentageVotesOfParty = (float) (rand.nextInt((int) (maximumPercent * 100) + 1) / 100.0);
			}
			return new Party(partyName, numSeatsInParty, percentageVotesOfParty);
		}
		return new Party(partyName, 0, 0);
	}

	/**
	 * Generates a Poll filled with elements with {@code createRandomParty}
	 * 
	 * @param name The desired poll name.
	 * @return The Poll with random Party elements
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
	 * Generates a PollList filled with elements with {@code createRandomPoll}.
	 * 
	 * @param numOfPolls The number of polls in the PollList.
	 * @return The PollList filled with random Poll elements.
	 */
	public PollList createRandomPollList(int numOfPolls) {
		PollList polls = new PollList(numOfPolls, numOfSeats);

		for (int i = 0; i < numOfPolls; i++) {
			Poll newPoll = createRandomPoll("Poll " + (i + 1));
			try {
				polls.addPoll(newPoll);
			} catch (PollListFullException plfe) {
				plfe.printStackTrace(); // "i" will not exceed numOfPolls so don't need to handle.
			}
		}
		return polls;

	}

	public PollList promptForPollList(int numOfPolls) {
		return createRandomPollList(numOfPolls);
	}

}