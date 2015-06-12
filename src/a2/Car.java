package a2;

import java.awt.Color;
import java.text.DecimalFormat;

// this class represents a movable Car object
// it inherits the hierarchy GameObject -> MovableObject
// it also implements the programmer defined interface ISteerable

/*
 * A SIDE NOTE: 
 * 
 * This Car class currently represents both Player Cars and
 * NPC cars. I am aware that this is not-ideal, but I could not decide on 
 * how I wanted to implement this in a class hierarchy yet. For assignment 3 
 * I will have this sorted out. I'm thinking I will most likely make the 
 * Car class abstract, and then that inherit  down to a PlayerCar class
 * as well as a NPCCar class.
 * 
 * An alternative idea...
 *  I guess you could leave this Car class as is, and just inherit down
 *  to a NPCCar class to handle NPCCars, but I think I would like to 
 *  separate the two. I'm toying with the idea in my head of how you could 
 *  make this game 2 players, and that is why I was considering how to 
 *  do this for so long.
 * 
 */


public class Car extends MovableObject implements ISteerable {

	// is this player or NPC?
	private boolean isPlayer;
	
	// progress, highest pylon reached
	private int highestPylonReached;
	
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
	
	// the car is OK
	private boolean carIsOkay;

	// car has a max damage, when reached the player loses a life
	private int maxDamage;
	
	// current car strategy, only applies to NPC cars
	private ICarStrategy curStrategy;
	
	// construct a car with given starting location, color,
	// as well as given heading, and speed
	public Car(FloatPoint p, Color c, int h, float s, boolean carIsPlayer){
		super(p, c, h, s);
		highestPylonReached=1;
		width=10;
		length=10;
		steeringDirection = 0;
		maxSpeed = 50;
		damageLevel = 0;
		hasTraction = true;
		carIsOkay = true;
		isPlayer = carIsPlayer;
		
		// set maxDamage and fuel for player or NPC accordingly
		if(carIsPlayer){
			maxDamage = 15;
			fuelLevel = 10;
		}
		else {
			maxDamage = 30;
			fuelLevel = 30;
		}
	}
	
	public boolean isPlayer(){
		return isPlayer;
	}
	
	public boolean isNPC(){
		return !isPlayer;
	}
	
	public ICarStrategy getStrategy(){
		return curStrategy;
	}
	
	public void setStrategy(ICarStrategy s){
		curStrategy = s;
	}
	
	public void invokeStrategy(){
		curStrategy.applyStrategy();
	}
	
	public boolean hitPylon(int p){	
		// car can only activate this pylon if it is
		// the one immediately after the last one they hit
		if( p == (highestPylonReached + 1) ){
			// update the player's pylon progress
			highestPylonReached = p;

		/*	this should be handled by the game world? where?	
			// did the player reach the last pylon?
			if( highestPylonReached == Pylon.getCount() )
				//playerWins();
		*/	
			return true;
			}
		
		// player didn't hit the next valid pylon
		return false;
	}
	
	public int getHighestPylon(){
		return highestPylonReached;
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
		return maxDamage;
	}
	
	// return the car's current fuel level
	public int getFuelLevel(){
		return fuelLevel;
	}
	
	// add more fuel to the car
	public void addFuel(int moreFuel){
		fuelLevel += moreFuel;
	}
	
	public boolean isOkay(){
		return carIsOkay;
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
		
		// check to see if car is still ok
		if(damageLevel >= maxDamage)
			carIsOkay = false;
	}
	
	// car took damage, or lost fuel,
	// need to check and maybe adjust the speed
	private void checkSpeed(){
		// if car is already near max speed...
		// need to adjust speed because
		// damage was taken and will reduce max speed
		if( getSpeed() > ( maxSpeed * ( (maxDamage - damageLevel) / maxDamage))){
			// speed was over new max, adjust
			setSpeed( (maxSpeed * ( (maxDamage - damageLevel) / maxDamage) ) );
		}
		
		// if the car has no fuel, set the speed to 0
		if( getFuelLevel() == 0)
			setSpeed(0);
		
	}
	
	public void reset(FloatPoint p){
		setLocation(p); 		// put car at this point (probably a pylon)
		setSpeed(0);			// put car speed to 0
		setHeading(0);			// face car north
		steeringDirection = 0;	// zero the cars wheels
		fuelLevel = 10;			// reset the fuel
		damageLevel = 0;		// reset the damage
		hasTraction = true;		// car should have traction to start
		carIsOkay = true;		// car is OK again (was not ok to trigger death and reset)
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
	// also accounts for NPCs currently
	public void move(){
		
		// car is a player car, handle that
		if(isPlayer()){
			// if car has no traction, then you CANNOT 
			// adjust steeringDirection
			if(hasTraction){
				// car has traction, apply steeringDirection to heading
				setHeading( getHeading() + steeringDirection );
			}
		}else if(isNPC()){
			// now dealing with an NPC car
			// invoke the strategy to set heading
			invokeStrategy();
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
		
		// car is out of fuel, no longer OK to drive
		if( getFuelLevel() == 0)
			carIsOkay = false;
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
			if( getSpeed() < ( maxSpeed * ( (maxDamage - damageLevel) / maxDamage) )){
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
		carDetails += ("fuelLevel=" + this.getFuelLevel() + " ");
		
		// damageLevel
		carDetails += ("damage=" + this.getDamageLevel() + " ");
		
		// car max damage
		carDetails += ("maxDamage=" + maxDamage + " ");
		
		// car has traction?
		carDetails += ( "\n     hasTraction=" + hasTraction + " ");
		
		// car pylon progress?
		carDetails += ("lastPylon=" + getHighestPylon() + " ");
		
		// car type
		carDetails += "type=";
		
		if(isPlayer())
			carDetails += "player";
		else{ 
			carDetails += "NPC" + " ";
			carDetails += "strategy=" + curStrategy.toString();
		}
		
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
}
