package model;

/** @author Michaela kasongo
 * @since July 15 2020
 * This is the Poll Class. This class represents a single poll
 * 
 */

import java.util.Arrays;
import java.util.Comparator;

public class Poll {
	private String name = "Unnamed Poll";
	private Party[] parties;
	private int numberOfParties = 0;
	
	//set max number of parties. Max is 1, any other entries set max number to 10.
	public Poll(String name, int maxNumberOfParties) { 
		this.name = name;
		if (maxNumberOfParties <1) {
			parties= new Party[10];
		}
		else {
			parties = new Party[maxNumberOfParties];
		}	
    }
	
	public String getPollName() {
		return name;
	}
	
	public int getNumberOfParties() {
		return numberOfParties;
	}
	
	/* Add entries into the Array. Entries must be case insensitive
	*if duplicates are present, replace with existing entry in the array
	*if the name is not present in the array, append to the list 
	*this method is repeated until the list is full.
	*/
	public void addParty(Party aParty) {
		for (int index=0; index < parties.length && parties[index] != null; index++) {
			if(aParty.getName().equalsIgnoreCase(parties[index].getName())) {
				parties[index]=aParty;
				return;
			}
			else {
				if(parties[index]==null) {
					System.out.println("Arguement is null!");
					}
				}
			}
		if (numberOfParties<parties.length) {
			parties[numberOfParties++] = aParty;
		}
		else {
			if(numberOfParties>=parties.length) {
				System.out.println("Error! The List is full");
				}
		
			}
	}
	
    //takes the name of the party to find and returns the party in the poll with that name. 
	public Party getParty(String name) {
		for (int index=0;index<parties.length;index++) {
			if(name.toUpperCase().equals(parties[index].getName().toUpperCase())) {
				return parties[index];
			}
		}
		
		return null;
	}
	
    // return all the parties in the poll in sorted form from most seats to least
	public Party[] getPartiesSortedBySeats() {
		Party[] arg = parties;
		Arrays.sort(arg,  new Comparator<Party>() {
	        
	        public int compare(Party p1, Party p2) {
	        	int a = (int) p1.getProjectedNumberOfSeats();
	        	int b = (int) p2.getProjectedNumberOfSeats();
	        	if(a > b) {
	        		return -1;
	        	}else if( a < b) {
	        	return 1; 	
	        	} else {
	        		return 0;
	        	}
	        }
	    });	
		return arg;
		}
	
	//same method as before but sorted by the most votes to the least	
	public Party[] getPartiesSortedByVotes() {
		Party[] arg = parties;
		Arrays.sort(arg,  new Comparator<Party>() {        
	        public int compare(Party p1, Party p2) {
	        	float a = p1.getProjectedPercentageOfVotes();
	        	float b = p2.getProjectedPercentageOfVotes();
	        	if(a > b) {
	        		return -1;
	        	}else if( a < b) {
	        	return 1; 	
	        	} else {
	        		return 0;
	        	}
	        }
	    });	
		return arg;	
		}	
	
	//match names and string representation of the party
	@Override
	public String toString() {
		String out = this.getPollName()+"\n";
		for(int index = 0; index < numberOfParties; index++) {
			out = out + parties[index].toString()+"\n";			
		}	
		return out;
	   }
  }
	

