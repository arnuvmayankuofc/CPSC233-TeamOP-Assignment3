package model;
import java.util.Arrays;
import java.util.Comparator;

/** 
 * This is the Poll Class. This class represents a single poll
 * 
 * @author Michaela Kasongo
 * 
 * @since July 15 2020
 */

public class Poll {
	private String name = "Unnamed Poll";
	private Party[] parties;
	private int numberOfParties = 0;

	/**
	 * Constructor: takes the name of the poll and the maximum number
	 *  of Parties this poll will take as an argument.  The maximum 
	 *  number of parties should be at least 1.  If an invalid number 
	 *  is provided, set the maximum number of parties this poll 
	 *  can contain to 10.
	 * 
	 * @param name
	 * @param maxNumberOfParties
	 */
	public Poll(String name, int maxNumberOfParties) { 
		this.name = name;
		//set the max number of parties to 10 if an invalid number is entered
		if (maxNumberOfParties <1) {
			parties= new Party[10];
		}
		else {
			parties = new Party[maxNumberOfParties];
		}	
	}

	/**
	 * getPollName - gets the name of the poll
	 * 
	 * @return name of the poll
	 */
	public String getPollName() {
		return name;
	}

	/**
	 * getNumberOfParties - the name of the poll
	 * 
	 * @return number of parties in the poll
	 */
	public int getNumberOfParties() {
		return numberOfParties;
	}

	/**
	 * addParty - Add entries into the Array. Entries must be case insensitive
	 *if duplicates are present, replace with existing entry in the array
	 *if the name is not present in the array, append to the list 
	 *this method is repeated until the list is full.
	 *
	 *@param aParty contains all parties
	 *@throws PollFullException if a party is attempted to be added 
	 *beyond the number of parties allowed.
	 *@return list of parties 
	 *
	 */
	public void addParty(Party aParty) throws PollFullException  {
		//check for duplicates of the parties, method is case insensitive
		for (int index=0; index < parties.length && parties[index] != null; index++) {
			if(aParty.getName().equalsIgnoreCase(parties[index].getName())) {
				parties[index]=aParty;
				return;
			}
			else {
				//Error for a null argument
				if(parties[index]==null) {
					System.out.println("Error! Input party is null!");
				}
			}
		}
		//if capacity is not reached, add more parties
		if (numberOfParties<parties.length) {
			parties[numberOfParties++] = aParty;
		}
		else {
			throw new PollFullException("Error! The Poll List is full");
		}
	}





	/**
	 * getParty - takes the name of the party to find and 
	 * returns the party in the poll with that name. 
	 * 
	 * @param name
	 * @return null if party name not found or if found,
	 * it return the party name
	 */
	public Party getParty(String name) {
		//fetches one party that matches the input name.
		for (int index=0;index<parties.length;index++) {
			if(name.toUpperCase().equals(parties[index].getName().toUpperCase())) {
				return parties[index];
			}
		}

		return null;
	}

	/** 
	 * getPartiesSortedBySeats - return all the parties in the poll in sorted 
	 * from most seats to least
	 * 
	 * @return parties sorted by seats from highest to lowest.
	 */
	public Party[] getPartiesSortedBySeats() {
		Party[] sorted = parties;
		Arrays.sort(sorted,  new Comparator<Party>() {
			//sorts the party by seats
			public int compare(Party p1, Party p2) {
				int numSeats1 = (int) p1.getProjectedNumberOfSeats();
				int numSeats2 = (int) p2.getProjectedNumberOfSeats();
				if(numSeats1 > numSeats2) {
					return -1;
				}else if( numSeats1 < numSeats2) {
					return 1; 	
				} else {
					return 0;
				}
			}
		});	
		return sorted;
	}

	/** 
	 * getPartiesSortedByVotes - return all the parties in the poll in sorted 
	 * from most votes to least.
	 * 
	 * @return parties sorted by votes from highest to lowest.
	 */
	public Party[] getPartiesSortedByVotes() {
		Party[] sorted = parties;
		Arrays.sort(sorted,  new Comparator<Party>() { 
			//sorts the party by votes
			public int compare(Party p1, Party p2) {
				float percentVote1 = p1.getProjectedPercentageOfVotes();
				float percentVote2= p2.getProjectedPercentageOfVotes();
				if(percentVote1  > percentVote2) {
					return -1;
				}else if( percentVote1  < percentVote2) {
					return 1; 	
				} else {
					return 0;
				}
			}
		});	
		return sorted;	
	}	

	/**
	 * string representation of parties in current poll.
	 */
	@Override
	public String toString() {
		String out = this.getPollName()+"\n";
		for(int index = 0; index < numberOfParties; index++) {
			out = out + parties[index].toString()+"\n";			
		}	
		return out;
	}
}


