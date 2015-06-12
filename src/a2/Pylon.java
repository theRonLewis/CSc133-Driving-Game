package a2;

import java.awt.Color;
import java.text.DecimalFormat;

// this class represents a pylon (or checkpoint) in the game world
// it is used to define the race track, and also to track player progress
public class Pylon extends FixedObject {

	// radius of this pylon
	private int radius;
	
	// which pylon is this in the sequence of pylons
	private int sequenceNumber;
	
	// shared static variable for counting total number of pylons
	// will most likely be needed to determine when the player wins
	private static int count = 0;
	
	// create a pylon with the given location and color
	// and set pylon variables
	Pylon(FloatPoint p, Color c){
		super(p, c);
		radius = 10;
		
		// increment the total number of pylons
		// (first pylon will increment count from 0 to 1)
		increment();
		
		// set the sequence number of this pylon to the new count
		setSequenceNumber();
	}
	
	// get the radius of this pylon
	public int getRadius(){
		return radius;
	}
	
	// get the sequence number of this pylon
	public int getSequenceNumber(){
		return sequenceNumber;
	}
	
	// internal method to set the sequence number
	// of a newly created pylon
	private void setSequenceNumber(){
		sequenceNumber = count;
	}
	
	// increment the count for all pylons
	private void increment(){
		count++;
	}
	
	// get the count of all the pylons
	public static int getCount(){
		return count;
	}
	
	// override, pylons cannot change color
	public void setColor(Color c){
		// empty code body, pylons cannot change color
	}
	
	// override, pylons cannot change color
	public void setRandomColor(){
		// empty code body, pylons cannot change color
	}

	// override the toString method
	public String toString() {
		
		// initialize the string
		String pylonDetails = "Pylon: ";
		
		// prep decimal formatting
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("##.#");
		
		// add details to string for...
		
		// location
		pylonDetails += "loc=" + df.format( this.getLocation().getX() ) + "," + df.format( this.getLocation().getY() ) + " ";
		
		// color
		pylonDetails += "color=[" + this.getColor().getRed() + "," + this.getColor().getGreen() + "," + this.getColor().getBlue() + "] ";
		
		// radius
		pylonDetails += "radius=" + this.getRadius() + " ";
		
		// seqNum
		pylonDetails += "seqNum=" + this.getSequenceNumber();
		
		// return the pylon details
		return pylonDetails;
	}
}