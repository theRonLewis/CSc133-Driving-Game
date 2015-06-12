package a4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.awt.geom.AffineTransform;

// this class represents a pylon (or checkpoint) in the game world
// it is used to define the race track, and also to track player progress
public class Pylon extends FixedObject {

	// radius of this pylon
	private int radius;
	
	// which pylon is this in the sequence of pylons
	private int sequenceNumber;
	
	// shared static variable for counting total number of pylons
	// needed to determine if/when the player wins
	private static int count = 0;

	// make pylon at world (x, y), with color c
	public Pylon(float x, float y, Color c) {
		super(x, y, c);
		radius = 10;
		increment();
		setSequenceNumber();
	}
	
	// make pylon at world (x, y), with color c
	// and sequence number sNum
	public Pylon(float x, float y, Color c, int sNum) {
		super(x, y, c);
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
		pylonDetails += "loc=" + df.format( this.getX() ) + "," + df.format( this.getY() ) + " ";
		
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
	public void draw(Graphics2D g2d) {
		
		// save the transform for future restoration
		AffineTransform saveAT = g2d.getTransform();
		
		// add current objects transformations
		g2d.transform(getTranslate());
		g2d.transform(getRotation());
		g2d.transform(getScale());
		
		// draw filled oval (circle)
		//g2d.setColor(getColor());
		//g2d.fillOval(-10, -10, 20, 20);
		
		// draw a banner (above the circle)
		g2d.setColor(Color.white);
		g2d.fillRect(-12, 10, 24, 14); 		// white banner
		g2d.setColor(Color.black);
		g2d.drawRect(-12, 10, 24, 14);		// black - banner outline
		g2d.drawLine(-12, 10, -12, -10);	// left pole
		g2d.drawLine( 12, 10,  12, -10);	// right pole
		
		// draw the number inside the circle
		g2d.scale(1,  -1);
		g2d.setColor(Color.black);
		g2d.drawString(Integer.toString(sequenceNumber), -3, -13);
		g2d.scale(1,  -1);
		
		
		if( isSelected() )
			drawSelected(g2d);
		
		g2d.setTransform(saveAT);
	}

	@Override
	public boolean contains(float x, float y) {
		float distSquared, radSquared;
		distSquared = (float)(Math.pow(((getX() - x)), 2) + Math.pow(((getY() - y)), 2));
		radSquared = (float)( Math.pow( ( (float)radius ) , 2) );
		return distSquared <= radSquared;
	}

	// draw a box around the selected pylon
	@Override
	public void drawSelected(Graphics2D g) {
		// draw small cursor at center (testing)
		//g.drawLine((int)getLocation().getX()-3, (int)getLocation().getY(), (int)getLocation().getX()+3, (int)getLocation().getY());
		//g.drawLine((int)getLocation().getX(), (int)getLocation().getY()+3, (int)getLocation().getX(), (int)getLocation().getY()-3);
		
		// make a box around the pylon
		g.setColor(Color.white);
		g.drawRect(-14, -12, 28, 38);
		g.setColor(Color.black);
		g.drawRect(-15, -13, 30, 40);
	}

	@Override
	public int getLeft() {
		return (int)getX()-radius;
	}

	@Override
	public int getRight() {
		return (int)getX()+radius;
	}

	@Override
	public int getTop() {
		return (int)getY()+radius;
	}

	@Override
	public int getBottom() {
		return (int)getY()-radius;
	}
}