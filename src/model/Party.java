package model;
// CPSC 233 Assignment 1
// 30068971
// Minsu Kim
// This class will take a party name, number of seats, and percentage of votes as a main argument and then visualize the results through text.

public class Party {
	private String name;
	private float projectedNumberOfSeats;
	private float projectedPercentageOfVotes;
	
// This method changes partyName to name for simplicity.	
	public Party(String partyName) {
		name = partyName;
	}

// This is the main method used for party, which takes a string type party name, float type number of seats and percentage of votes.
	public Party(String partyName, float projectedNumberOfSeats, float projectedPercentageOfVotes) {
		name = partyName;
		setProjectedNumberOfSeats(projectedNumberOfSeats);
		setProjectedPercentageOfVotes(projectedPercentageOfVotes);
	}

// This getter method of the percentage will return a float type 0.0 only when the number inputed is outside of its parameter of between 0 and 1.
	public float getProjectedPercentageOfVotes() {
		if (projectedPercentageOfVotes < 0) {
			return (float) 0.0;
		}
		if (projectedPercentageOfVotes > 1) {
			return (float) 0.0;
		}
		return projectedPercentageOfVotes;
	}
	
// This getter method of name will return the party name.	
	public String getName() {
		return name;
	}

//This setter method of name sets the name to that inputed.
	public void setName(String name) {
		this.name = name;
	}
	
// This getter method will return a float type of 0.0 when the argument of number of seats is less than 0.
	public float getProjectedNumberOfSeats() {
		if (projectedNumberOfSeats > 0) {
			return projectedNumberOfSeats;
		}
		return (float) 0.0;
	}
	
//	This setter method will only work if the argument passed is within the parameter and once it does, it will change up the original parameter passed on by the main method.
	public void setProjectedPercentageOfVotes(float projectedPercentageOfVotes) {
		if (projectedPercentageOfVotes >= 0 && projectedPercentageOfVotes <= 1) {
			this.projectedPercentageOfVotes = projectedPercentageOfVotes;
		}
	}
	
// This setter method will only work if the argument passed is within the parameter and once it does, it will change up the original parameter passed on by the main method.
	public void setProjectedNumberOfSeats(float projectedNumberOfSeats) {
		if (projectedNumberOfSeats >= 0) {
		this.projectedNumberOfSeats = projectedNumberOfSeats;
		}
	}

// This toString method will take the main method's arguments, and print out the proper statement.
	public String toString() {
		int votes = (int) (projectedPercentageOfVotes * 100);
		return name + " (" + votes + "% of votes, " + projectedNumberOfSeats + " seats)";
	}

	public double projectedPercentOfSeats(int totalNumberOfSeats) {
		if (totalNumberOfSeats > 0) {
			double num = projectedNumberOfSeats / totalNumberOfSeats;
			return num;
		}
		else {
			return 0.0;
		}
	}
	
// This textVisualSeats method will take the three arguments, and return a text visual of the inputs.	
	public String textVisualizationBySeats(int maxStars, int starsNeededForMajority, double numOfSeatsPerStar) {
		String list = "";
		// For counting how many stars should be printed out.
		int stars = (int) (projectedNumberOfSeats / numOfSeatsPerStar);
		for (int counter = 0; counter < maxStars +1; counter++) {
			// At the majority point, which is half of the max stars, it will put in "|" to show a visual of where the half way point is.
			if (counter == starsNeededForMajority) {
				list += "|";
			}
			else {
				// Using int stars, it will loop through to add "*", for the amount of stars that should be printed out.
				if(stars > 0) {
					list += "*";
					stars --;
				}
				else {
				// This else statement will only go through once all of the "*" are used up and the rest of the spaces needed will be filled with spaces in order to show a proper visual.
					list += " ";
					stars --;
				}
			}
		}
		// This returns the proper statement of the visual.
		return list + " " + name + " (" + ((int)(projectedPercentageOfVotes * 100)) + "% of votes, " + projectedNumberOfSeats + " seats)";
	}
	
// This method will take three inputs, and show a visual of what is needed to be shown. The only difference between the above textVisual method is that this is shown using votes, not seats.
	public String textVisualizationByVotes(int maxStars, int starsNeededForMajority, double percentOfVotesPerStar) {
		String list = "";
		// Int stars is created by using formulas to count how many stars(votes) should be shown.
		int stars = (int) ((projectedPercentageOfVotes * 100) / percentOfVotesPerStar);
		for (int counter = 0; counter < maxStars + 1; counter++) {
			// At the majority point, which is half of the max stars, it will put in "|" to show a visual of where the half way point is.
			if (counter == starsNeededForMajority) {
				list += "|";
			}
			else {
				// Using int stars, it will loop through to add "*", for the amount of stars that should be printed out.
				if(stars > 0) {
					list += "*";
					stars --;
				}
				else {
				// This else statement will only go through once all of the "*" are used up and the rest of the spaces needed will be filled with spaces in order to show a proper visual.
					list += " ";
					stars --;
				}
			}
		}
		// This returns the proper statement of the visual.
		return list + " " + name + " (" + ((int)(projectedPercentageOfVotes * 100)) + "% of votes, " + projectedNumberOfSeats + " seats)";
	}
}