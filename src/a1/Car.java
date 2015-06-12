package a1;

import java.awt.Color;
import java.text.DecimalFormat;

// this class represents a movable Car object
// it inherits the hierarchy GameObject -> MovableObject
// it also implements the programmer defined interface ISteerable

public class Car extends MovableObject implements ISteerable {

	// car needs width and length for area location representation
	private int width;
	private int length;
	
	// car needs to be able to turn
	// can turn max of 40 degrees
	private int steeringDirection;
	private final int MAX_STEER = 40;
	
	// car needs to sometimes not have traction
	// this is used for accelerating, braking, and
	// turning, and preventing that from happening
	// in OilSlick objects
	private boolean hasTraction;
	
	// car needs to have a max speed, defined at creation
	// for now its always 50
	private float maxSpeed;
	
	// car has some amount of fuel
	private int fuelLevel;
	
	// car has a damage meter
	private int damageLevel;

	// car has a max damage, when reached the player loses a life
	// final for now, but cars may have different max damage in
	// future versions so this may change
	private final int MAX_DMG = 10;
	
	// construct a car with given starting location, color,
	// as well as given heading, and speed
	Car(FloatPoint p, Color c, int h, float s){
		super(p, c, h, s);
		width=10;
		length=10;
		steeringDirection = 0;
		maxSpeed = 50;
		fuelLevel = 10;
		damageLevel = 0;
		hasTraction = true;
	}
	
	// get the width of the car
	public int getWidth(){
		return width;
	}
	
	// get the length of the car
	public int getLength(){
		return length;
	}
	
	// get the current steering direction of the car
	public int getSteeringDirection(){
		return steeringDirection;
	}
	
	// get the max speed for this car
	// right now is always 50, but may differ from
	// car to car in later versions
	public float getMaxSpeed(){
		return maxSpeed;
	}
	
	// get max damage of this car, used for checking
	// if player is still alive
	public int getMaxDamage(){
		return MAX_DMG;
	}
	
	// return the car's current fuel level
	public int getFuelLevel(){
		return fuelLevel;
	}
	
	// add more fuel to the car
	public void addFuel(int moreFuel){
		fuelLevel += moreFuel;
	}
	
	// get the current damage level of the car, used
	// for checking if player is still alive
	public int getDamageLevel(){
		return damageLevel;
	}
	
	// add damage to the car from a collision
	public void addDamage(int damage){
		damageLevel += damage;
		
		// adjust speed for dmg taken if needed
		checkSpeed();
	}
	
	// car took damage, or lost fuel,
	// need to check and maybe adjust the speed
	private void checkSpeed(){
		// if car is already near max speed...
		// need to adjust speed because
		// damage was taken and will reduce max speed
		if( getSpeed() > ( maxSpeed * ( (MAX_DMG - damageLevel) / MAX_DMG))){
			// speed was over new max, adjust
			setSpeed( (maxSpeed * ( (MAX_DMG - damageLevel) / MAX_DMG) ) );
		}
		
		// if the car has no fuel, set the speed to 0
		if( getFuelLevel() == 0)
			setSpeed(0);
		
	}
	
	// car entered an oil slick and no longer has
	// traction for turning and accelerating or braking
	public void enterOil(){
		hasTraction = false;
	}
	
	// car exited an oil slick and now has traction
	// again and can turn, accelerate, and brake properly
	public void exitOil(){
		hasTraction = true;
	}
	
	// override move function to check for traction
	// and adjust heading if needed
	public void move(){
		// if car has no traction, then you CANNOT 
		// adjust steeringDirection
		if(hasTraction){
			// car has traction, apply steeringDirection to heading
			setHeading( getHeading() + steeringDirection );
		}
		// time has passed, car uses fuel
		// even if not actually moving
		useFuel(1);
		
		// call the parent move() to perform
		// remaining operations and actually
		// move the car
		super.move();
		
		// check the fuel level in case speed needs adjusting
		checkSpeed();
	}
	
	// car uses some fuel
	private void useFuel(int i){
		// if the car has some fuel, use some
		if (getFuelLevel() > 0)
			fuelLevel -= i;
		
		// if the car is out of fuel, make sure level isn't below 0
		if ( getFuelLevel() < 0)
			fuelLevel = 0;
	}
	
	// attempt to accelerate the car if
	// it currently has traction and also
	// check speed vs max speed
	public void accelerate(){
		// car can only accelerate if
		// it currently has traction
		if( hasTraction ){
			// if car is already at max speed...
			// it also cannot accelerate
			// check for damage factor as well
			if( getSpeed() < ( maxSpeed * ( (MAX_DMG - damageLevel) / MAX_DMG))){
				setSpeed( getSpeed() + 3 );
				//check if you went over max and adjust
				checkSpeed();
			}
		}
	}
	
	// attempt to apply brakes to the car if
	// it currently has traction and also
	// prevent speed from dropping below 0
	public void brake(){
		// car can only brake if
		// it currently has tractoin
		if( hasTraction ){
			// cannot go in reverse... (yet?)
			if( getSpeed() > 0 ){
				setSpeed( getSpeed() - 3);
				// check for going under
				if( getSpeed() < 0)
					setSpeed(0);
			}
		}
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
		carDetails += "width=" + this.getWidth() + " ";		
		
		// length
		carDetails += ( "length=" + this.getLength() + "\n     ");
		
		// maxSpeed
		carDetails += ("maxSpeed=" + df.format( this.getMaxSpeed() ) + " ");
		
		// steeringDirection
		carDetails += ("steeringDirection=" + this.getSteeringDirection() + " ");
		
		// fuelLevel
		carDetails += "fuelLevel=" + this.getFuelLevel() + " ";
		
		// damageLevel
		carDetails += "damage=" + this.getDamageLevel();
		
		// return the car details
		return carDetails;
	}
	
	
	// INTERFACE FUNCTIONS
	
	// these functions will need adjusting if the steering
	// adjustment changes from a value that divides perfectly
	// into the max stering amount
	
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
}
