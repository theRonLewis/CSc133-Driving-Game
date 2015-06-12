package a4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
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
	
	public FuelCan(float x, float y, int num) {
		super(x, y, Color.white); // dummy color for now
		size = num;
		setRandomColor();
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
		fuelCanDetails += df.format( this.getX() ) + "," + df.format( this.getY() ) + " ";
		
		// color
		fuelCanDetails += "color=[" + this.getColor().getRed() + "," + this.getColor().getGreen() + "," + this.getColor().getBlue() + "] ";
		
		// size
		fuelCanDetails += "size=" + this.getSize();
		
		// return the fuel can details
		return fuelCanDetails;
	}

	@Override
	public void draw(Graphics2D g2d) {
		
		// save the transform for future restoration
		AffineTransform saveAT = g2d.getTransform();
		
		// add current objects transformations
		g2d.transform(getTranslate());
		g2d.transform(getRotation());
		g2d.transform(getScale());
	
	/*	
		// draw fuel can as filled square with size printed in center in contrast color
		g2d.setColor(getColor());
		g2d.fillRect(-10, -10, 20, 20);
		g2d.scale(1,  -1);
		g2d.setColor( getContrastColor() );
		g2d.drawString(Integer.toString(getSize()), -3, 3);
		g2d.scale(1,  -1);
	*/
		
		// for drawing the fuel can body
		final int [] xCanPoints = { -10, -10,  3, 10,  10 };
		final int [] yCanPoints = { -16,  16, 16,  9, -16 };
		
		// for drawing the spout
		final int [] xSpoutPoints = { -6,  8, 14, 14, 10, 0 };
		final int [] ySpoutPoints = {  0, 18, 18, 14, 14, 0 };
		
		// draw the spout first
		g2d.setColor(Color.YELLOW);
		g2d.fillPolygon(xSpoutPoints, ySpoutPoints, 6);
		
		// draw the red part of the can on top of spout
		g2d.setColor(Color.RED);
		g2d.fillPolygon(xCanPoints, yCanPoints, 5);
		
		// draw the white and gray stripes
		g2d.setColor(Color.WHITE);
		g2d.fillRect(-10, -8, 20, 16);
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(-10, -6, 20, 12);
		
		// draw the fuel amount on the can
		g2d.scale(1,  -1);
		g2d.setColor(Color.BLACK);
		g2d.drawString(Integer.toString(getSize()), -3, 3);
		g2d.scale(1,  -1);
		
		if( isSelected() )
			drawSelected(g2d);
		
		g2d.setTransform(saveAT);
	}

	@Override
	public boolean contains(float x, float y) {
		// get my x and y bounds
		float x1, x2, y1, y2;
		x1 = getX()-(10);
		x2 = getX()+(10);
		y1 = getY()-(16);
		y2 = getY()+(16);
		
		if( x >= x1 && x <= x2 )
			if( y >= y1 && y <= y2 ) 
				return true;
		return false;
	}

	@Override
	public void drawSelected(Graphics2D g2d) {
		
		g2d.setColor(Color.WHITE);
		// draw few boxes around fuel can
		g2d.drawRect(-12, -18, 28, 38);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(-13 , -19, 30, 40);
	}

	@Override
	public int getLeft() {
		return (int)(getX()-10);
	}

	@Override
	public int getRight() {
		return (int)(getX()+10);
	}

	@Override
	public int getTop() {
		return (int)(getY()+16);
	}

	@Override
	public int getBottom() {
		return (int)(getY()-16);
	}
}