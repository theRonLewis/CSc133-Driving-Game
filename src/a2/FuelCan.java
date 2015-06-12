package a2;

import java.awt.Color;
import java.text.DecimalFormat;

// this class represents a fixed Fuel Can object
// it inherits the hierarchy GameObject -> FixedObject
public class FuelCan extends FixedObject {

	// fuel can must have some amount of fuel
	private int size;
	
	// create randomly sized fuel can
	// has random location and color as well
	FuelCan(){
		super();
		setSize(randInt(3,6));
	}
	
	// create randomly sized fuel can
	// with given point and color
	FuelCan(FloatPoint p, Color c){
		super(p, c);
		size = randInt(3,6);
	}
	
	// get the size of the fuel can
	public int getSize(){
		return size;
	}
	
	// set the size of the fuel can
	private void setSize(int s){
		size = s;
	}

	// override the toString method
	public String toString() {
		// initialize the string
		String fuelCanDetails = "FuelCan: ";
		
		// prep decimal formatting
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("##.#");
		
		// add details to the string for...
		
		// location
		fuelCanDetails += "loc=";
		fuelCanDetails += df.format( this.getLocation().getX() ) + "," + df.format( this.getLocation().getY() ) + " ";
		
		// color
		fuelCanDetails += "color=[" + this.getColor().getRed() + "," + this.getColor().getGreen() + "," + this.getColor().getBlue() + "] ";
		
		// size
		fuelCanDetails += "size=" + this.getSize();
		
		// return the fuel can details
		return fuelCanDetails;
	}
}
