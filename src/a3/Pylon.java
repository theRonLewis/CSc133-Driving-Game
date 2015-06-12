package a3;

import java.awt.Color;
import java.awt.Graphics;
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
	
	public Pylon(FloatPoint p, Color c, int sNum) {
		super(p, c);
		radius = 10;
		increment();
		sequenceNumber = sNum;
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
	
	// decrement the count for all pylons
	public static void decrement(){
		count--;
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

	@Override
	public void draw(Graphics g) {
		
		// draw oval / circke
		g.setColor(getColor());
		g.fillOval((int)getLocation().getX()-10, (int)getLocation().getY()-10, 20, 20);
		
		// draw the number
		g.setColor(Color.white);
		g.drawString(Integer.toString(sequenceNumber), (int)getLocation().getX()-3, (int)getLocation().getY()+3);
		
		if( isSelected() )
			drawSelected(g);
	}

	@Override
	public boolean contains(FloatPoint p) {
		float distSquared, radSquared;
		distSquared = (float)(Math.pow(((getLocation().getX() - p.getX())), 2) + Math.pow(((getLocation().getY() - p.getY())), 2));
		radSquared = (float)( Math.pow( ( (float)radius ) , 2) );
		return distSquared <= radSquared;
	}

	@Override
	public void drawSelected(Graphics g) {
		// draw small cursor at center
		//g.drawLine((int)getLocation().getX()-3, (int)getLocation().getY(), (int)getLocation().getX()+3, (int)getLocation().getY());
		//g.drawLine((int)getLocation().getX(), (int)getLocation().getY()+3, (int)getLocation().getX(), (int)getLocation().getY()-3);
		
		// make a box around the pylon
		g.drawRect((int)getLocation().getX()-radius, (int)getLocation().getY()-radius, radius*2, radius*2);
	}

	@Override
	public String getType() {
		return "PYLON";
	}

	@Override
	public int getLeft() {
		return (int)getLocation().getX()-radius;
	}

	@Override
	public int getRight() {
		return (int)getLocation().getX()+radius;
	}

	@Override
	public int getTop() {
		return (int)getLocation().getY()+radius;
	}

	@Override
	public int getBottom() {
		return (int)getLocation().getY()-radius;
	}
}