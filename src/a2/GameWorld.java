package a2;

import java.awt.Color;
import java.util.Vector;


// this class represents a GameWorld, with a collection of GameObjects
// other classes can initiate commands on GameWorld to have it operate
// on the objects in it's world
public class GameWorld implements IObservable {

	// must have a collection of GameObjects
	GameObjectCollection myObjs = new GameObjectCollection();
	
	// keep a list of things observing the game world
	@SuppressWarnings("rawtypes")
	private Vector myObservers;
	
	// amount of time passed since game started
	private int clockTime;
	
	// number of lives the player has left
	private int livesLeft;
	
	// the furtherest pylon that the player car reached
	// used for restarting the player when they die
	// THIS IS NO LONGER USED, AS NPC REQUIRES CAR TO HOLD THIS VARIABLE
	//private int lastPylonReached;
	
	// must have a player car
	private Car playerCar;
	
	// sound on or off
	private boolean sound = true;
	
	// constructor to initialize observers
	@SuppressWarnings("rawtypes")
	GameWorld(){
		// initialize the observers
		myObservers = new Vector();
	}
	
	// creates an initial game world
	public void initLayout(){
		
		// Initialize game state variables
		clockTime = 0;
		livesLeft = 3;
		//lastPylonReached = 1; // no longer used
		
		// create points for car and pylons
		FloatPoint startingPoint = new FloatPoint(200, 200);
		FloatPoint pylonPoint1   = new FloatPoint(200, 200);
		FloatPoint pylonPoint2   = new FloatPoint(200, 250);
		FloatPoint pylonPoint3   = new FloatPoint(700, 800);
		FloatPoint pylonPoint4   = new FloatPoint(900, 400);
		
		// create single unchangeable shared pylon color
		final Color PYLON_COLOR = new Color(64,64,64);
		
		// create the player car and add to myObjs
		playerCar = new Car(startingPoint, Color.blue, 0, 0, true);
		myObjs.add(playerCar);
		
		// create three NPCs near the start, add to myObjs
		// start the NPCs with speed of 10 for now, otherwise they will never move
		Car npc1, npc2, npc3;
		
		npc1 = new Car(new FloatPoint(100, 200), Color.green, 0, 10, false);
		npc1.setStrategy(new DestructionDerbyStrategy(npc1, playerCar));
		myObjs.add(npc1);
		
		npc2 = new Car(new FloatPoint(150, 200), Color.green, 0, 10, false);
		npc2.setStrategy(new RaceStrategy(npc2, this));
		myObjs.add(npc2);
		
		npc3 = new Car(new FloatPoint(250, 200), Color.green, 0, 10, false);
		npc3.setStrategy(new DestructionDerbyStrategy(npc3, playerCar));
		myObjs.add(npc3);
		
		// create the pylons, the game 'track'
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
		
		notifyObservers();
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
		varsAndVals += ( playerCar.getHighestPylon() + "\n");
		
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
	
	// print a textual map of the game world to console
	public void printTextMap(){
		
		// get an iterator for the collection
		IIterator anIterator = myObjs.getIterator();
		
		// setup a placeholder object
		Object currentObj = new Object();
		
		System.out.println("\n THE CURRENT \"MAP\"...\n");
		// iterate through, and print each one
		while( anIterator.hasNext() ){
			currentObj = anIterator.next();
			System.out.println( currentObj.toString() );
		}
		System.out.println();
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
		notifyObservers();
	}
	
	// go through and change strategies of all NPCs
	public void changeNPCStrategies(){
		IIterator anIterator = myObjs.getIterator();
		
		Object currentObj = new Object();
		
		while( anIterator.hasNext() ){
			currentObj = anIterator.next();
			if( currentObj instanceof Car){ // found a car
				if( ((Car)currentObj).isNPC() ){ // is the car a NPC
					if( ((Car)currentObj).getStrategy() instanceof RaceStrategy ){
						((Car)currentObj).setStrategy(new DestructionDerbyStrategy(((Car)currentObj), playerCar ));
					} else if ( ((Car)currentObj).getStrategy() instanceof DestructionDerbyStrategy ){
						((Car)currentObj).setStrategy(new RaceStrategy(((Car)currentObj), this ));
					}
				}
			}
		}
		notifyObservers();
	}
	
	// tell the player car to turn the wheels left
	public void turnPlayerLeft(){
		playerCar.turnLeft();
		notifyObservers();
	}
	
	// tell the player car to turn the wheels left
	public void turnPlayerRight(){
		playerCar.turnRight();
		notifyObservers();
	}
	
	// tell the player car to turn accelerate
	public void acceleratePlayer(){
		playerCar.accelerate();
		notifyObservers();
	}
	
	// tell the player car to brake
	public void brakePlayer(){
		playerCar.brake();
		notifyObservers();
	}
	
	// add damage to the player car for colliding with another car
	public void playerCarCollision(){
		// add damage to the player
		playerCar.addDamage(2);

		// check if the car is still ok
		if( !playerCar.isOkay() )
			playerDied();
		
		// find the first NPC and add damage to it
		IIterator anIterator = myObjs.getIterator();
		
		Object currentObj = new Object();
		
		while( anIterator.hasNext() ){
			currentObj = anIterator.next();
			if(currentObj instanceof Car){
				if( ((Car)currentObj).isNPC() ){
					((Car)currentObj).addDamage(2);
					notifyObservers();
					return;
				}
			}
		}
	}
	
	// add damage to the player car for colliding with a bird
	public void playerBirdCollision(){
		playerCar.addDamage(1);
		notifyObservers();
		
		// check if the car is still ok
		if( !playerCar.isOkay() )
			playerDied();
	}
	
	// create new oil slick and add to object collection
	public void addOilSlick(){
		OilSlick os = new OilSlick();
		myObjs.add(os);
		notifyObservers();
	}
	
	// tell the player car that it just entered an oil slick
	public void playerEntersOil(){
		playerCar.enterOil();
		notifyObservers();
	}
	
	// tell the player car that it just exited an oil slick
	public void playerExitsOil(){
		playerCar.exitOil();
		notifyObservers();
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
				notifyObservers();
				return true;
			}
		}
		// didn't find any fuel cans, return false
		return false;
	}
	
	// simulate a player running over pylon # p 
	public boolean playerHitPylon(int p){
		// simulate player hitting the given pylon
		// record if was succesful 
		boolean success;
		success = playerCar.hitPylon(p);
		
		// if successful, did the player win?
		if(success)
			if( playerCar.getHighestPylon() == Pylon.getCount() )
				playerWins();
		
		notifyObservers();
		
		return success;
	}
	
	// the player won the game by reaching the last pylon
	private void playerWins(){
		// for now, print to console and close
		System.out.println("The player won the game by reaching the final pylon");
		System.exit(0);
	}
	
	// the player has died, from 0 fuel, or reaching max damage
	private void playerDied(){
		
		// print to console for now
		System.out.println("The player died, probably from lack of fuel, or reaching max damage -- check last map print");
		
		// take a life away
		livesLeft -= 1;
		
		// check for game over
		if(livesLeft == 0){
			notifyObservers();
			gameOver();
			return;
		} else if( livesLeft > 0){
			// player still had lives left, restart them at the last pylon the reached

			// go through the game world objects, and find the last pylon they hit

			// get an iterator for the collection
			IIterator anIterator = myObjs.getIterator();
			
			// setup a placeholder object
			Object currentObj = new Object();
			// iterate through, and check each one
			while( anIterator.hasNext() ){
				currentObj = anIterator.next();
				if(currentObj instanceof Pylon)
					if( ((Pylon)currentObj).getSequenceNumber() == playerCar.getHighestPylon() ){
						// found the pylon they were at, reset the player there
						playerCar.reset( ((Pylon)currentObj).getLocation() );
						
						// reset the clock
						clockTime = 0;
						
						notifyObservers();
					}
			}
		}
	}
	
	// the player ran out of lives, and the game is over
	private void gameOver(){
		
		// for now, print to console, and exit the app
		System.out.println("Game over. The player ran out of lives. Closing app.");
		System.exit(0);
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
			// if this object is movable...
			if( currentObj instanceof MovableObject){
				// move it!
				((MovableObject) currentObj).move();
			}
		}
		notifyObservers();
		
		// check if the car is still OK (fuel)
		if( !playerCar.isOkay() )
			playerDied();
		
	}
	
	// get how long the game has been going
	public int getClockTime(){
		return clockTime;
	}
	
	// get how many lives the player has left
	public int getLivesLeft(){
		return livesLeft;
	}
	
	// get the highest player reached pylon
	public int getHighestPylon(){
		return playerCar.getHighestPylon();
	}
	
	// get how much fuel the player has left
	public int getPlayerFuel(){
		return playerCar.getFuelLevel();
	}

	// get the player damage level
	public int getPlayerDamage(){
		return playerCar.getDamageLevel();
	}
	
	// turn the sound on
	public void turnSoundOn(){
		sound = true;
		notifyObservers();
	}
	
	// turn the sound off
	public void turnSoundOff(){
		sound = false;
		notifyObservers();
	}
	
	// toggle the sound
	public void toggleSound(){
		if(sound)
			sound = false;
		else sound = true;
		notifyObservers();
	}
	
	public boolean soundIsOn(){
		return sound;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addObserver(IObserver obs) {
		// add the given observer
		myObservers.add(obs);	
	}

	@Override
	public void notifyObservers() {
		
		int i; // counter for going through observers
		Object theObservers[]; // array of observables watching me
		
		theObservers = myObservers.toArray();
		
		// go through all observers and pass them a copy of myself
		if(!myObservers.isEmpty()){
			for(i = 0; i < myObservers.size(); i++)
				((IObserver)theObservers[i]).update(this);
		}
		
	}
	
	// FOLLOWING METHODS ARE FOR TESTING PURPOSES
	
	// return playerCar for printing in tests
	public String getPlayerCar(){
		return playerCar.toString();
	}
}