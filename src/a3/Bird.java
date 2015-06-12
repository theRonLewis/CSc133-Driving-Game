package a3;

import java.awt.Color;
import java.awt.Graphics;
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
	
	public boolean contains(FloatPoint p){
	/*	UNCOMMENT THIS TO ENABLE SELECTION OF BIRDS
		float distSquared, radSquared;
		distSquared = (float)(Math.pow(((getLocation().getX() - p.getX())), 2) + Math.pow(((getLocation().getY() - p.getY())), 2));
		radSquared = (float)( Math.pow( ( getSize()/2 ) , 2) );
		return distSquared <= radSquared;
	*/
		return false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawOval((int)getLocation().getX()-(getSize()/2), (int)getLocation().getY()-(getSize()/2), getSize(), getSize());
		
		if( isSelected() )
			drawSelected(g);
	}
	
	public void drawSelected(Graphics g){
		// draw small cursor at center
		g.drawLine((int)getLocation().getX()-3, (int)getLocation().getY(), (int)getLocation().getX()+3, (int)getLocation().getY());
		g.drawLine((int)getLocation().getX(), (int)getLocation().getY()+3, (int)getLocation().getX(), (int)getLocation().getY()-3);
		
		// make a box around the bird
		g.drawRect((int)getLocation().getX()-(getSize()/2), (int)getLocation().getY()-(getSize()/2), getSize(), getSize());
	}

	@Override
	public String getType() {
		return "BIRD";
	}

	@Override
	public int getLeft() {
		return (int)(getLocation().getX()-(getSize()/2));
	}

	@Override
	public int getRight() {
		return (int)(getLocation().getX()+(getSize()/2));
	}

	@Override
	public int getTop() {
		return (int)(getLocation().getY()+(getSize()/2));
	}

	@Override
	public int getBottom() {
		return (int)(getLocation().getY()-(getSize()/2));
	}
}
