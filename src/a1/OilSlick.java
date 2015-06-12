package a1;

import java.awt.Color;
import java.text.DecimalFormat;


// this class represents a fixed oil slick
// it inherits the hierarchy GameObject -> FixedObject
public class OilSlick extends FixedObject {
	
	// oil slicks must have a width and length
	// to be represented on the screen
	private int width;
	private int length;
	
	// new randomly placed OilSlick
	// parent constructors create random location and color
	OilSlick(){
		super();
		width = (randInt(10,30));
		length = (randInt(10, 30));
	}
	
	// new OilSlick with given point and color 
	OilSlick(FloatPoint p, Color c){
		super(p, c);
		width = (randInt(10,30));
		length = (randInt(10, 30));
	}
	
	// get the width of the oil slick
	public int getWidth(){
		return width;
	}
	
	// get the length of the oil slick
	public int getLength(){
		return length;
	}

	// override the toString method
	public String toString(){
		
		// initialize the string
		String oilDetails = "OilSlick: ";
		
		// prep decimal formatting
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("##.#");
		
		// add details to the string for...
		
		// location
		oilDetails += "loc=" + df.format( this.getLocation().getX() ) + "," + df.format( this.getLocation().getY() ) + " ";
		
		// color
		oilDetails += "color=[" + this.getColor().getRed() + "," + this.getColor().getGreen() + "," + this.getColor().getBlue() + "] ";
		
		// width
		oilDetails += "width=" + this.getWidth() + " ";
		
		// length
		oilDetails += "length=" + this.getLength();
		
		// return the oil slick details
		return oilDetails;
	}
}
