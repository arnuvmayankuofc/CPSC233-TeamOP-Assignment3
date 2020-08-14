package model;

/**
 * This class will take a party name, number of seats, and percentage of votes
 * as a main argument and then visualize the results through text. It will also
 * catch any errors when the number of seats and percentage of votes are invalid
 * 
 * @author Minsu Kim
 * @author Arnuv Mayank
 *
 */
public class Party {
	private String name;
	private float projectedNumberOfSeats;
	private float projectedPercentageOfVotes;

	/**
	 * Creates a new {@code Party} and sets the name to the one provided
	 * 
	 * @param partyName - the name of the party
	 */
	public Party(String partyName) {
		name = partyName;
	}

	/**
	 * Creates a new {@code Party} and sets the name, projected
	 * number of seats and votes to the ones provided
	 * 
	 * @param partyName                  - the name of the party
	 * @param projectedNumberOfSeats     - the projected number of seats (must be >=
	 *                                   0)
	 * @param projectedPercentageOfVotes - the projected number of votes (must be
	 *                                   between 0 and 1)
	 * 
	 * @throws InvalidPartyDataException when projected number of seats is negative
	 *                                   or percentage of votes is not between 0 and
	 *                                   1 for any party
	 */
	public Party(String partyName, float projectedNumberOfSeats, float projectedPercentageOfVotes)
			throws InvalidPartyDataException {
		name = partyName;

		setProjectedNumberOfSeats(projectedNumberOfSeats);
		setProjectedPercentageOfVotes(projectedPercentageOfVotes);

	}

	/**
	 * This getter method of the percentage will return a float type 0.0 only when
	 * the number inputed is outside of its parameter of between 0 and 1.
	 * 
	 * @return the projected percentage of votes
	 */
	public float getProjectedPercentageOfVotes() {
		if (projectedPercentageOfVotes < 0) {
			return (float) 0.0;
		}
		if (projectedPercentageOfVotes > 1) {
			return (float) 0.0;
		}
		return projectedPercentageOfVotes;
	}

	/**
	 * Gets the party's name
	 * 
	 * @return the party's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the party's name
	 * 
	 * @param name - the name to be set to
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the projected number of seats
	 * 
	 * @return the projected number of seats
	 */
	public float getProjectedNumberOfSeats() {
		if (projectedNumberOfSeats > 0) {
			return projectedNumberOfSeats;
		}
		return (float) 0.0;
	}

	/**
	 * Sets the projected percentage of votes
	 * 
	 * @param projectedPercentageOfVotes - the percentage of votes to be set to
	 * 
	 * @throws InvalidPartyDataException when projected percentage of votes is not
	 *                                   between 0 and 1 for any party
	 */
	public void setProjectedPercentageOfVotes(float projectedPercentageOfVotes) throws InvalidPartyDataException {
		if (projectedPercentageOfVotes >= 0 && projectedPercentageOfVotes <= 1) {
			this.projectedPercentageOfVotes = projectedPercentageOfVotes;
		} else {
			throw new InvalidPartyDataException("Percentage of votes must be between 0 and 1 (inclusive). ");
		}
	}

	/**
	 * Sets the projected number of seats
	 * 
	 * @param projectedNumberOfSeats the number of seats to be set to
	 * 
	 * @throws InvalidPartyDataException when projected number of seats is negative
	 */
	public void setProjectedNumberOfSeats(float projectedNumberOfSeats) throws InvalidPartyDataException {
		if (projectedNumberOfSeats >= 0) {
			this.projectedNumberOfSeats = projectedNumberOfSeats;
		} else {
			throw new InvalidPartyDataException("Number of seats must be non-negative. ");
		}
	}

	/**
	 * Prints a parties information in the specified manner
	 */
	public String toString() {
		int votes = (int) (projectedPercentageOfVotes * 100);
		return name + " (" + votes + "% of votes, " + projectedNumberOfSeats + " seats)";
	}

	/**
	 * Gives the projected percentage of seats given the total
	 * 
	 * @param totalNumberOfSeats - the total number of seats
	 * 
	 * @return the ratio of projected seats to total seats
	 */
	public double projectedPercentOfSeats(int totalNumberOfSeats) {
		if (totalNumberOfSeats > 0) {
			double num = projectedNumberOfSeats / totalNumberOfSeats;
			return num;
		} else {
			return 0.0;
		}
	}

	/**
	 * Gives a visual representation of the party by seats in the specified manner.
	 * 
	 * @param maxStars               - the maximum number of stars that should be
	 *                               displayed on a single line
	 * @param starsNeededForMajority - the minimum number of starts that would
	 *                               represent a majority
	 * @param numOfSeatsPerStar      - how many seats are represented by a star
	 * 
	 * @return the text visualization
	 */
	public String textVisualizationBySeats(int maxStars, int starsNeededForMajority, double numOfSeatsPerStar) {
		String list = "";
		// for counting how many stars should be printed out.
		int stars = (int) (projectedNumberOfSeats / numOfSeatsPerStar);
		for (int counter = 0; counter < maxStars + 1; counter++) {
			// at the majority point, which is half of the max stars, it will put in "|" to
			// show a visual of where the half way point is
			if (counter == starsNeededForMajority) {
				list += "|";
			} else {
				// using int stars, it will loop through to add "*", for the amount of stars
				// that should be printed out
				if (stars > 0) {
					list += "*";
					stars--;
				} else {
					// only goes through once all of the "*" are used up and the rest of the spaces
					// needed will be filled with spaces in order to show a proper visual
					list += " ";
					stars--;
				}
			}
		}
		// returns the proper statement of the visual.
		return list + " " + name + " (" + ((int) (projectedPercentageOfVotes * 100)) + "% of votes, "
				+ projectedNumberOfSeats + " seats)";
	}

	/**
	 * Gives a visual representation of the party by votes in the specified manner.
	 * 
	 * @param maxStars               - the maximum number of stars that should be
	 *                               displayed on a single line
	 * @param starsNeededForMajority - the minimum number of starts that would
	 *                               represent a majority
	 * @param numOfSeatsPerStar      - how many seats are represented by a star
	 * 
	 * @return the text visualization
	 */
	public String textVisualizationByVotes(int maxStars, int starsNeededForMajority, double percentOfVotesPerStar) {
		String list = "";
		// int stars is created by using formulas to count how many stars(votes) should
		// be shown
		int stars = (int) ((projectedPercentageOfVotes * 100) / percentOfVotesPerStar);
		for (int counter = 0; counter < maxStars + 1; counter++) {
			// At the majority point, which is half of the max stars, it will put in "|" to
			// show a visual of where the half way point is
			if (counter == starsNeededForMajority) {
				list += "|";
			} else {
				// using int stars, it will loop through to add "*", for the amount of stars
				// that should be printed out
				if (stars > 0) {
					list += "*";
					stars--;
				} else {
					// only goes through once all of the "*" are used up and the rest of the spaces
					// needed will be filled with spaces in order to show a proper visual.
					list += " ";
					stars--;
				}
			}
		}
		// returns the proper statement of the visual.
		return list + " " + name + " (" + ((int) (projectedPercentageOfVotes * 100)) + "% of votes, "
				+ projectedNumberOfSeats + " seats)";
	}
}