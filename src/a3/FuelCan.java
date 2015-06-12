package a3;

import java.awt.Color;
import java.awt.Graphics;
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
	
	public FuelCan(FloatPoint p, int num) {
		super(p, Color.white); // dummy color for now
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
		fuelCanDetails += df.format( this.getLocation().getX() ) + "," + df.format( this.getLocation().getY() ) + " ";
		
		// color
		fuelCanDetails += "color=[" + this.getColor().getRed() + "," + this.getColor().getGreen() + "," + this.getColor().getBlue() + "] ";
		
		// size
		fuelCanDetails += "size=" + this.getSize();
		
		// return the fuel can details
		return fuelCanDetails;
	}

	@Override
	public void draw(Graphics g) {
		// draw fuel can as filled square with size printed in center in contrast color
		g.setColor(getColor());
		g.fillRect((int)getLocation().getX()-10, (int)getLocation().getY()-10, 20, 20);
		g.setColor( getContrastColor() );
		g.drawString(Integer.toString(getSize()), (int)getLocation().getX()-3, (int)getLocation().getY()+3);
		
		if( isSelected() )
			drawSelected(g);
	}

	@Override
	public boolean contains(FloatPoint p) {
		// get my x and y bounds
		float x1, x2, y1, y2;
		x1 = getLocation().getX()-(10);
		x2 = getLocation().getX()+(10);
		y1 = getLocation().getY()-(10);
		y2 = getLocation().getY()+(10);
		
		if( p.getX() >= x1 && p.getX() <= x2 )
			if( p.getY() >= y1 && p.getY() <= y2 ) 
				return true;
		return false;
	}

	@Override
	public void drawSelected(Graphics g) {
		// draw small cursor at center
		g.setColor(getContrastColor());
		//g.drawLine((int)getLocation().getX()-3, (int)getLocation().getY(), (int)getLocation().getX()+3, (int)getLocation().getY());
		//g.drawLine((int)getLocation().getX(), (int)getLocation().getY()+3, (int)getLocation().getX(), (int)getLocation().getY()-3);
		
		// draw few boxes around fuel can
		g.drawRect( (int)(getLocation().getX()-10) , (int)(getLocation().getY()-10), 20, 20);
		g.setColor(Color.white);
		g.drawRect( (int)(getLocation().getX()-14) , (int)(getLocation().getY()-14), 28, 28);
	}

	@Override
	public String getType() {
		return "FUEL";
	}

	@Override
	public int getLeft() {
		return (int)(getLocation().getX()-10);
	}

	@Override
	public int getRight() {
		return (int)(getLocation().getX()+10);
	}

	@Override
	public int getTop() {
		return (int)(getLocation().getY()+10);
	}

	@Override
	public int getBottom() {
		return (int)(getLocation().getY()-10);
	}
}