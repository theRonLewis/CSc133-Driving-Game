package a3;

import java.awt.Color;
import java.awt.Graphics;
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
	OilSlick(FloatPoint p){
		super(p);
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

	@Override
	public void draw(Graphics g) {
		// print string where object is, for initial setup and testing
		//g.drawString(toString(), (int)getLocation().getX(), (int)getLocation().getY());
		
		g.setColor(getColor());
		g.fillOval( ((int)getLocation().getX()- (getWidth()/2)) , ((int)getLocation().getY()- (getLength()/2)), getWidth(), getLength() );
		
		if( isSelected() )
			drawSelected(g);
	}

	@Override
	public boolean contains(FloatPoint p) {
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
	public void drawSelected(Graphics g) {
		// draw small cursor at center
		g.setColor(getContrastColor());
		g.drawLine((int)getLocation().getX()-3, (int)getLocation().getY(), (int)getLocation().getX()+3, (int)getLocation().getY());
		g.drawLine((int)getLocation().getX(), (int)getLocation().getY()+3, (int)getLocation().getX(), (int)getLocation().getY()-3);
		
		// draw box around fuel can
		g.drawRect( (int)(getLocation().getX()-(width/2)) , (int)(getLocation().getY()-(length/2)), width, length);
	}

	@Override
	public String getType() {
		return "OIL";
	}

	@Override
	public int getLeft() {
		return ((int)getLocation().getX()-(getWidth()/2));
	}

	@Override
	public int getRight() {
		return ((int)getLocation().getX()+(getWidth()/2));
	}

	@Override
	public int getTop() {
		return ((int)getLocation().getY()+(getLength()/2));
	}

	@Override
	public int getBottom() {
		return ((int)getLocation().getY()-(getLength()/2));
	}
}