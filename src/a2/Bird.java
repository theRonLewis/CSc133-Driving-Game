package a2;

import java.awt.Color;
import java.text.DecimalFormat;

// this class represents a movable Bird Object
// it inherits the hierarchy GameObject -> MovableObject

public class Bird extends MovableObject {
	
	private int size;
	
	// create bird with random size
	// will invoke parent constructors to also create
	// random heading, speed, location, and color
	Bird(){
		super();
		size = randInt(10,25);
	}
	
	// create bird with given point, color, heading, and speed
	// note: still has random size
	Bird(FloatPoint p, Color c, int h, float s){
		super(p, c, h, s);
		size = randInt(12, 22);
	}
	
	// get the size of the bird
	public int getSize(){
		return size;
	}
	
	// override, birds cannot change color
	public void setColor(Color c){
		// empty code body, birds cannot change color
	}
	
	// override, birds cannot change color
	public void setRandomColor(){
		// empty code body, birds cannot change color
	}
	
	// set heading override, birds cannot change heading
	public void setHeading(int h){
		// empty code body, birds cannot change heading
	}
	
	// set speed override, birds cannot change speed
	public void setSpeed(float s){
		// empty code body, birds cannot change speed
	}
	
	// override the toString method
	public String toString() {
		
		// initialize the string
		String birdDetails = "Bird: ";
		
		// prep decimal formatting
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("##.#");
		
		// add details to the string for...
		
		// location
		birdDetails += "loc=" + df.format( this.getLocation().getX() ) + "," + df.format( this.getLocation().getX() ) + " ";
		
		// color
		birdDetails += "color=[" + this.getColor().getRed() + "," + this.getColor().getGreen() + "," + this.getColor().getBlue() + "] ";
		
		// heading
		birdDetails += "heading=" + this.getHeading() + " ";
		
		// speed
		birdDetails += "speed=" + df.format( getSpeed() ) + " ";
		
		// size
		birdDetails += "size=" + this.getSize();
		
		//return all the details
		return birdDetails;
	}
	
}
