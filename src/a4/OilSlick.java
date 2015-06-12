package a4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
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
	OilSlick(float x, float y){
		super(x, y);
		width = (randInt(10,30));
		length = (randInt(10, 30));
	}
	
	// new OilSlick with given point and color 
	OilSlick(float x, float y, Color c){
		super(x, y, c);
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
		oilDetails += "loc=" + df.format( this.getX() ) + "," + df.format( this.getY() ) + " ";
		
		// color
		oilDetails += "color=[" + this.getColor().getRed() + "," + this.getColor().getGreen() + "," + this.getColor().getBlue() + "] ";
		
		// width
		oilDetails += "width=" + this.getWidth() + " ";
		
		// length
		oilDetails += "length=" + this.getLength();
		
		// return the oil slick details
		return oilDetails;
	}

	@Override
	public void draw(Graphics2D g2d) {
		
		// save the transform for future restoration
		AffineTransform saveAT = g2d.getTransform();
		
		// add current objects transformations
		g2d.transform(getTranslate());
		g2d.transform(getRotation());
		g2d.transform(getScale());

		
		g2d.setColor(getColor());
		g2d.fillOval( -(getWidth()/2) , -(getLength()/2), getWidth(), getLength() );
		g2d.setColor(Color.black);
		g2d.drawOval( -(getWidth()/2) , -(getLength()/2), getWidth(), getLength() );
		
		if( isSelected() )
			drawSelected(g2d);
		
		g2d.setTransform(saveAT);
	}

	@Override
	public boolean contains(float x, float y) {
	/*	UNCOMMENT THIS TO ENABLE SELECTION OF OIL SLICKS	
		// get my x and y bounds
		float x1, x2, y1, y2;
		x1 = getLocation().getX()-(width/2);
		x2 = getLocation().getX()+(width/2);
		y1 = getLocation().getY()-(length/2);
		y2 = getLocation().getY()+(length/2);
		
		if( p.getX() >= x1 && p.getX() <= x2 )
			if( p.getY() >= y1 && p.getY() <= y2 ) 
				return true;
	*/	
		return false;
	}

	@Override
	public void drawSelected(Graphics2D g2d) {
		// draw small cursor at center
		g2d.setColor(getContrastColor());
		g2d.drawLine(-3, 0, 3, 0);
		g2d.drawLine(0, +3, 0, -3);
		
		// draw box around fuel can
		g2d.drawRect( -(width/2), -(length/2), width, length);
	}

	@Override
	public int getLeft() {
		return ((int)getX()-(getWidth()/2));
	}

	@Override
	public int getRight() {
		return ((int)getX()+(getWidth()/2));
	}

	@Override
	public int getTop() {
		return ((int)getY()+(getLength()/2));
	}

	@Override
	public int getBottom() {
		return ((int)getY()-(getLength()/2));
	}
}