package a4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;

// this class represents a movable Bird Object
// it inherits the hierarchy GameObject -> MovableObject

public class Bird extends MovableObject {
	
	// treated as diameter for drawing bird on screen
	private int size;
	
	// create bird with random size
	// will invoke parent constructors to also create
	// random heading, speed, location, and color
	Bird(){
		super();
		//size = randInt(15,30);
		size = 30;
	}

/*	
	// create bird with given point, color, heading, and speed
	// note: still has random size
	Bird(FloatPoint p, Color c, int h, float s){
		super(p, c, h, s);
		size = randInt(12, 22);
	}
*/
	
	Bird(float x, float y, Color c, int h, float s){
		super(x, y, c, h, s);
		size = randInt(12,22);
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
		birdDetails += "loc=" + df.format( this.getX() ) + "," + df.format( this.getY() ) + " ";
		
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
	
	public boolean contains(float x, float y){
	/*	UNCOMMENT THIS TO ENABLE SELECTION OF BIRDS
		float distSquared, radSquared;
		distSquared = (float)(Math.pow(((getLocation().getX() - p.getX())), 2) + Math.pow(((getLocation().getY() - p.getY())), 2));
		radSquared = (float)( Math.pow( ( getSize()/2 ) , 2) );
		return distSquared <= radSquared;
	*/
		
		// return false, birds cannot be selected
		return false;
	}

	@Override
	public void draw(Graphics2D g2d) {
		
		// save the AT for restoration later
		AffineTransform saveAT = g2d.getTransform();
		
		// add current objects transformations
		g2d.transform(getTranslate());
		g2d.transform(getRotation());
		g2d.transform(getScale());

		// draw filled in circle/oval to represent the bird body
		g2d.setColor(getColor());
		g2d.fillOval(-(getSize()/2), -(getSize()/2), getSize(), getSize());
		
		// prep for drawing beak
		final int [] xBeakPoints = {0, -(getSize()/4), (getSize()/4) };
		final int [] yBeakPoints = { getSize(), (int)(getSize()*0.3), (int)(getSize()*0.3) };
		
		// draw a beak - shows heading
		g2d.setColor(Color.yellow);
		g2d.fillPolygon(xBeakPoints, yBeakPoints, 3);
		
		// TODO working on drawing of bird
		
		// draw whites of eyes
		g2d.setColor(Color.white);
		g2d.fillOval(  2, (int)(getSize()/4)-3, 8, 8);
		g2d.fillOval( -10, (int)(getSize()/4)-3, 8, 8);
		
		// draw pupils
		g2d.setColor(Color.black);
		g2d.fillOval( 5, (int)(getSize()/4)+1, 2, 2);
		g2d.fillOval(-7, (int)(getSize()/4)+1, 2, 2);
		
		
		if( isSelected() )
			drawSelected(g2d);
		
		// restore the g2d object for the next object
		g2d.setTransform(saveAT);
	}
	
	public void drawSelected(Graphics2D g2d){
		// draw small cursor at center
		g2d.drawLine(-3, 0, 3, 0);
		g2d.drawLine(0, 3, 0, -3);
		
		// make a box around the bird
		g2d.drawRect(-(getSize()/2), -(getSize()/2), getSize(), getSize());
	}

	@Override
	public int getLeft() {
		return (int)(getX()-(getSize()/2));
	}

	@Override
	public int getRight() {
		return (int)(getX()+(getSize()/2));
	}

	@Override
	public int getTop() {
		return (int)(getY()+(getSize()/2));
	}

	@Override
	public int getBottom() {
		return (int)(getY()-(getSize()/2));
	}
}
