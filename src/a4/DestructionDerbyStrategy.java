package a4;

/*
 * NPC Car Strategy class: Destruction Derby Strategy
 * 
 * This implements ICarStrategy as a way of 
 * managing NPC AI in a GameWorld.
 * 
 * This strategy takes a car to handle, and 
 * the car it is hunting down as constructor parameters.
 * 
 * The strategy makes the NPC go towards the target car in
 * an attempt to hit them.
 * 
 */

public class DestructionDerbyStrategy implements ICarStrategy {
	
	private Car myCar;
	private Car targetCar;
	
	public DestructionDerbyStrategy(NonPlayerCar thisCar, Car target){
		myCar = thisCar;
		targetCar = target;
	}
	
	public String toString(){
		return "DestructionDerby";
	}
	
	public void applyStrategy(){
		// code here to find the given player car and set heading towards it
		
		// change in x and y
		float dx, dy;
		
		dx = targetCar.getX() - myCar.getX();
		dy = targetCar.getY() - myCar.getY();
		
		// awesome, atan2 handles all the hard parts of arctan calculations
		int angle = (int)Math.toDegrees( Math.atan2(dx, dy) );
		myCar.setHeading( angle );
	}
	
	// get the X and Y of the target, for drawing sometimes
	public float getTargetX(){
		return targetCar.getX();
	}
	
	public float getTargetY(){
		return targetCar.getY();
	}
}
