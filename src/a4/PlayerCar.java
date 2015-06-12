package a4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;

public class PlayerCar extends Car implements ISteerable {

	// car needs to be able to turn
	// can turn max of 40 degrees
	private int steeringDirection;
	private final int MAX_STEER = 40;
	
	public PlayerCar (float x, float y, Color c, int h, int s){
		super(x, y, c, h, s, "PLAYER");
		
		// also setup the player car vars appropriately
		steeringDirection = 0;
		setMaxDamage(15);
		setFuelLevel(20);
	}
	
	// get the current steering direction of the car
	public int getSteeringDirection(){
		return steeringDirection;
	}
	
	public void reset(float x, float y){
		super.reset(x, y); // reset the car to this point
		
		// also reset player car values
		steeringDirection = 0;
		getFrontAxle().setWheelRotation(steeringDirection);
	}
	
	public void move(int ms){
		// if car has no traction, then you CANNOT 
		// adjust steeringDirection
		if(hasTraction()){
			// car has traction, apply steeringDirection to heading
			setHeading( getHeading() + steeringDirection );
		}
		
		// call the parent move function
		super.move(ms);
	}
	
	// override the toString method
	public String toString(){
		
		// initialize the string
		String carDetails = "Car: ";
		
		// prep decimal formatting
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("##.#");
		
		// add details to string for...
		
		// location
		carDetails += "loc=" + df.format( this.getX() ) + "," + df.format( this.getY() ) + " ";
		
		// color RGB
		carDetails += "color=[" + this.getColor().getRed() + "," +  this.getColor().getGreen() +  "," + this.getColor().getBlue() + "] ";
		
		// heading
		carDetails += "heading=" + getHeading() + " ";
		
		// speed
		carDetails += "speed=" + df.format( getSpeed() ) + " ";
		
		// width
		carDetails += "width=" + (int)this.getWidth() + " ";		
		
		// length
		carDetails += ( "length=" + (int)this.getLength() + "\n     ");
		
		// maxSpeed
		carDetails += ("maxSpeed=" + df.format( this.getMaxSpeed() ) + " ");
		
		// steeringDirection
		carDetails += ("steeringDirection=" + this.getSteeringDirection() + " ");
		
		// fuelLevel
		carDetails += ("fuelLevel=" + df.format( this.getFuelLevel() ) + " ");
		
		// damageLevel
		carDetails += ("damage=" + this.getDamageLevel() + " ");
		
		// car max damage
		carDetails += ("maxDamage=" + getMaxDamage() + " ");
		
		// car has traction?
		carDetails += ( "\n     hasTraction=" + hasTraction() + " ");
		
		// car pylon progress?
		carDetails += ("lastPylon=" + getHighestPylon() + " ");
		
		// car type
		carDetails += "type=player";
		
		// return the car details
		return carDetails;
	}
	
	// INTERFACE FUNCTIONS
	
	// these functions will need adjusting if the steering
	// adjustment changes from a value that divides perfectly
	// into the max steering amount
	
	// turn the car's wheels left
	// don't go beyond max turning limit
	public void turnLeft(){
		if( steeringDirection > (4 - MAX_STEER) )
			steeringDirection -= 5;
		getFrontAxle().setWheelRotation(steeringDirection);
	}

	// turn the car's wheels right
	// don't go beyond max turning limit
	public void turnRight(){
		if( steeringDirection < (MAX_STEER - 4) )
			steeringDirection += 5;
		getFrontAxle().setWheelRotation(steeringDirection);
	}

	@Override
	public void draw(Graphics2D g2d) {

		// save the transform for future restoration
		AffineTransform saveAT = g2d.getTransform();
		
		// add current objects transformations
		g2d.transform(getTranslate());
		g2d.transform(getRotation());
		g2d.transform(getScale());
		
		// draw the filled rectangle
	//	g2d.setColor(getColor());
	//	g2d.fillRect( (int)(-(getWidth()/2)), (int)(-(getLength()/2)), (int)getWidth(), (int)getLength() );
		
		// draw the body (should draw axles first)
		rearAxle.draw(g2d);
		frontAxle.draw(g2d);
		myBody.draw(g2d);
		
		// draw a line to show the front
	//	g2d.setColor(Color.WHITE);
	//	g2d.drawLine(0, 0, 0, (int)(getLength()/2) + 3 );
		
		if( isSelected() )
			drawSelected(g2d);
		
		// draw small cursor at center
		//g2d.setColor(Color.WHITE);
		//g2d.drawLine(-3, 0, 3, 0);
		//g2d.drawLine(0, +3, 0, -3);
		
		g2d.setTransform(saveAT);
	}

	@Override
	public void drawSelected(Graphics2D g2d) {
		// draw small cursor at center
		g2d.setColor(Color.WHITE);
		g2d.drawLine(-3, 0, 3, 0);
		g2d.drawLine(0, +3, 0, -3);
		
		// draw black box around player
		g2d.drawRect( (int)(-(getWidth()/2)) , (int)(-(getLength()/2)), (int)getWidth(), (int)getLength() );
	}
}
