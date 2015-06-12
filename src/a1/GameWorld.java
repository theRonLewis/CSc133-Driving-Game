package a1;

import java.awt.Color;

// this class represents a GameWorld, with a collection of GameObjects
// other classes can initiate commands on GameWorld to have it operate
// on the objects in it's world
public class GameWorld {

	// must have a collection of GameObjects
	GameObjectCollection myObjs = new GameObjectCollection();
	
	// amount of time passed since game started
	private int clockTime;
	
	// number of lives the player has left
	private int livesLeft;
	
	// the furtherest pylon that the player car reached
	// used for restarting the player when they die
	private int lastPylonReached;
	
	// must have a player car
	private Car playerCar;
	
	// creates an initial game world
	public void initLayout(){
		
		// Initialize game state variables
		clockTime = 0;
		livesLeft = 3;
		lastPylonReached = 1;
		
		// create points for car and pylons
		FloatPoint startingPoint = new FloatPoint(200, 200);
		FloatPoint pylonPoint1   = new FloatPoint(200, 200);
		FloatPoint pylonPoint2   = new FloatPoint(200, 800);
		FloatPoint pylonPoint3   = new FloatPoint(700, 800);
		FloatPoint pylonPoint4   = new FloatPoint(900, 400);
		
		// create single unchangeable shared pylon color
		final Color PYLON_COLOR = new Color(64,64,64);
		
		// create the car and pylons, and add them to myObjs
		playerCar = new Car(startingPoint, Color.blue, 0, 0);
		myObjs.add(playerCar);
		
		Pylon p1 = new Pylon(pylonPoint1, PYLON_COLOR);
		myObjs.add(p1);
		
		Pylon p2 = new Pylon(pylonPoint2, PYLON_COLOR);
		myObjs.add(p2);
		
		Pylon p3 = new Pylon(pylonPoint3, PYLON_COLOR);
		myObjs.add(p3);
		
		Pylon p4 = new Pylon(pylonPoint4, PYLON_COLOR);
		myObjs.add(p4);
		
		// create 3 randomized birds
		Bird b1 = new Bird();
		Bird b2 = new Bird();
		Bird b3 = new Bird();
		
		// add those birds to the game
		myObjs.add(b1);
		myObjs.add(b2);
		myObjs.add(b3);
		
		// create 3 random oil slicks
		OilSlick os1 = new OilSlick();
		OilSlick os2 = new OilSlick();
		OilSlick os3 = new OilSlick();
		
		// add those oil slicks to the game
		myObjs.add(os1);
		myObjs.add(os2);
		myObjs.add(os3);
		
		// create 3 random fuel cans
		FuelCan fc1 = new FuelCan();
		FuelCan fc2 = new FuelCan();
		FuelCan fc3 = new FuelCan();
		
		// add those fuel cans to the game
		myObjs.add(fc1);
		myObjs.add(fc2);
		myObjs.add(fc3);
		
	}
	
	// return array of strings formatted
	public String getGameState(){
		
		// create a string to pass back
		String varsAndVals;
		
		// add current lives left
		varsAndVals = "Lives left: ";
		varsAndVals += ( String.valueOf(livesLeft) + "\n");
		
		// add current clock time
		varsAndVals += "Current clock time: ";
		varsAndVals += ( String.valueOf(clockTime) + "\n");
		
		// add highest pylon reached
		varsAndVals += "Highest pylon reached: ";
		varsAndVals += ( String.valueOf(lastPylonReached) + "\n");
		
		// add current player fuel
		varsAndVals += "Current player fuel: ";
		varsAndVals += ( String.valueOf( playerCar.getFuelLevel() ) + "\n");
		
		// add current player damage
		varsAndVals += "Current player damage level: ";
		varsAndVals += ( String.valueOf( playerCar.getDamageLevel() ) + "\n");
		
		// add the damage cap
		varsAndVals += "Player Max Damage: ";
		varsAndVals += ( String.valueOf( playerCar.getMaxDamage() ) + "\n");
		
		// return the string
		return varsAndVals;
		
	}
	
	// print a textual map of the game world
	public void printTextMap(){
		
		// get an iterator for the collection
		IIterator anIterator = myObjs.getIterator();
		
		// setup a placeholder object
		Object currentObj = new Object();
		
		// iterate through, and print each one
		while( anIterator.hasNext() ){
			currentObj = anIterator.next();
			System.out.println( currentObj.toString() );
		}
	}
	
	// go through and create a random color for each object
	// objects that should not be able to change colors should
	// have the functions overridden
	public void changeObjColors(){
		IIterator anIterator = myObjs.getIterator();
		
		Object currentObj = new Object();
		
		while( anIterator.hasNext() ){
			currentObj = anIterator.next();
			((GameObject) currentObj).setRandomColor();
		}
	}
	
	// tell the player car to turn the wheels left
	public void turnPlayerLeft(){
		playerCar.turnLeft();
	}
	
	// tell the player car to turn the wheels left
	public void turnPlayerRight(){
		playerCar.turnRight();
	}
	
	// tell the player car to turn accelerate
	public void acceleratePlayer(){
		playerCar.accelerate();
	}
	
	// tell the player car to brake
	public void brakePlayer(){
		playerCar.brake();
	}
	
	// add damage to the player car for colliding with another car
	public void playerCarCollision(){
		playerCar.addDamage(2);
	}
	
	// add damage to the player car for colliding with a bird
	public void playerBirdCollision(){
		playerCar.addDamage(1);
	}
	
	// create new oil slick and add to object collection
	public void addOilSlick(){
		OilSlick os = new OilSlick();
		myObjs.add(os);
	}
	
	// tell the player car that it just entered an oil slick
	public void playerEntersOil(){
		playerCar.enterOil();
	}
	
	// tell the player car that it just exited an oil slick
	public void playerExitsOil(){
		playerCar.exitOil();
	}
	
	// simulate the player colliding with and picking up a fuel can
	// currently there is no collision detection, so we just pick up the 
	// first instance of a fuel can in the game world, and add a new one
	public boolean playerGetsFuelCan(){
		
		// get an iterator, and create placeholder object
		IIterator anIterator = myObjs.getIterator();
		Object currentObj = new Object();
		
		// for now...
		// find the first fuel can, process it, and remove it, then return
		// later on this would be triggered by a collision between the 
		// player and a fuel can
		while( anIterator.hasNext() ){
			currentObj = anIterator.next();
			// found the first fuel can!
			if( currentObj instanceof FuelCan){
				// add the fuel in the can to the car
				playerCar.addFuel( ((FuelCan)currentObj).getSize() );
				
				// remove the fuel can
				myObjs.remove(currentObj);
				
				// create new fuel can and add to car
				FuelCan newCan = new FuelCan();
				myObjs.add(newCan);
				
				// remove successful, return true
				return true;
			}
		}
		// didn't find any fuel cans, return false
		return false;
	}
	
	// simulate a player running over pylon # p 
	public boolean playerHitPylon(int p){
		// player can only activate this pylon if it is
		// the one immediately after the last one they hit
		if( p == (lastPylonReached + 1) ){
			// update the player's pylon progress
			lastPylonReached = p;
			return true;
			}
		
		// player didn't hit the next valid pylon
		return false;
	}
	
	// advance the clock and make appropriate changes to world
	public void advanceGameClock(){
		
		// actually advance the clock
		clockTime++;
		
		// create iterator to go through and move objects
		IIterator anIterator = myObjs.getIterator();
		
		Object currentObj = new Object();
		
		while( anIterator.hasNext() ){
			currentObj = anIterator.next();
			// if this object is moveable...
			if( currentObj instanceof MovableObject){
				// move it!
				((MovableObject) currentObj).move();
			}
		}
		
	}

	// FOLLOWING METHODS ARE FOR TESTING PURPOSES
	
	// return playerCar for printing in tests
	public String getPlayerCar(){
		return playerCar.toString();
	}
}
