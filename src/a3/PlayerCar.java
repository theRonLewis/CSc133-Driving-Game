package a3;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;

public class PlayerCar extends Car implements ISteerable {

	// car needs to be able to turn
	// can turn max of 40 degrees
	private int steeringDirection;
	private final int MAX_STEER = 40;
	
	public PlayerCar(FloatPoint p, Color c, int h, float s) {
		super(p, c, h, s);
		
		// also setup the player car vars appropriately
		steeringDirection = 0;
		setMaxDamage(15);
		setFuelLevel(20);
	}
	
	// get the current steering direction of the car
	public int getSteeringDirection(){
		return steeringDirection;
	}
	
	public void reset(FloatPoint p){
		super.reset(p); // reset the car to this point
		
		// also reset player car values
		steeringDirection = 0;
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
		carDetails += "loc=" + df.format( this.getLocation().getX() ) + "," + df.format( this.getLocation().getY() ) + " ";
		
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
	}

	// turn the car's wheels right
	// don't go beyond max turning limit
	public void turnRight(){
		if( steeringDirection < (MAX_STEER - 4) )
			steeringDirection += 5;
	}

	@Override
	public void draw(Graphics g) {
		// print string at object location for testing
		//g.drawString(toString(), (int)getLocation().getX(), (int)getLocation().getY());
		
		g.setColor(getColor());
		g.fillRect( (int)(getLocation().getX()- (getWidth()/2)) , (int)(getLocation().getY()- (getLength()/2)), (int)getWidth(), (int)getLength() );
		
		if( isSelected() )
			drawSelected(g);
	}

	@Override
	public void drawSelected(Graphics g) {
		// draw small cursor at center
		g.setColor(Color.WHITE);
		g.drawLine((int)getLocation().getX()-3, (int)getLocation().getY(), (int)getLocation().getX()+3, (int)getLocation().getY());
		g.drawLine((int)getLocation().getX(), (int)getLocation().getY()+3, (int)getLocation().getX(), (int)getLocation().getY()-3);
		
		// draw black box around player
		g.drawRect( (int)(getLocation().getX()- (getWidth()/2)) , (int)(getLocation().getY()- (getLength()/2)), (int)getWidth(), (int)getLength() );
	}

	@Override
	public String getType() {
		return "PLAYER";
	}
}
