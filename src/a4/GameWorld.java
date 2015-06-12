package a4;

import java.util.Random;
import java.util.Vector;
import javax.swing.JOptionPane;

// this class represents a GameWorld, with a collection of GameObjects
// other classes can initiate commands on GameWorld to have it operate
// on the objects in it's world

public class GameWorld implements IObservable, IGameWorld {

	// must have a collection of GameObjects
	private GameObjectCollection myObjs;
	
	// make a factory to make game objects
	private GameObjectFactory myGOFactory;
	
	// keep a list of things observing the game world
	@SuppressWarnings("rawtypes")
	private Vector myObservers;
	
	// amount of time passed since game started
	private float clockTime;
	
	// number of lives the player has left
	private int livesLeft;
	
	// flags for adding pylon or fuel can
	private boolean addNewPylon = false;
	private boolean addNewFuelCan = false;
	
	// sound on or off
	private boolean sound;
	
	// sound library
	private SoundLibrary mySoundLib;
	
	// create a collision handler to use...
	private CollisionHandler myCollisionHandler;
	
	// constructor to initialize observers
	@SuppressWarnings("rawtypes")
	GameWorld(){
		// initialize the observers
		myObservers = new Vector();
		sound = true;
		myObjs = new GameObjectCollection();
		myCollisionHandler = new CollisionHandler(myObjs);
		mySoundLib = SoundLibrary.getInstance();
	} 
	
	// creates an initial game world
	public void initLayout(){
		
		int i; // for counting
		
		// Initialize game state variables
		clockTime = 0;
		livesLeft = 3;
		
		// make a game object factory to setup the world
		myGOFactory = new GameObjectFactory(this.getProxy());
		
		// create the player car and add to myObjs
		myObjs.add(myGOFactory.getGameObject("PLAYER"));
		
		// create three NPCs near the start, add to myObjs
		for(i=1; i<=3; i++)
			myObjs.add(myGOFactory.getGameObject("NPC"));
			
		
		// create four pylons to create a demo track
		for(i=1; i<=4; i++)
			myObjs.add(myGOFactory.getGameObject("PYLON"));
		
		// create 3 randomized birds
		for(i=1; i<=3; i++)
			myObjs.add(myGOFactory.getGameObject("BIRD"));
		
		// create 3 random oil slicks
		for(i=1; i<=3; i++)
			myObjs.add(myGOFactory.getGameObject("OIL"));
		
		// create 3 random fuel cans
		for(i=1; i<=3; i++)
			myObjs.add(myGOFactory.getGameObject("FUEL"));
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
		varsAndVals += ( myGOFactory.getPlayer().getHighestPylon() + "\n");
		
		// add current player fuel
		varsAndVals += "Current player fuel: ";
		varsAndVals += ( String.valueOf( myGOFactory.getPlayer().getFuelLevel() ) + "\n");
		
		// add current player damage
		varsAndVals += "Current player damage level: ";
		varsAndVals += ( String.valueOf( myGOFactory.getPlayer().getDamageLevel() ) + "\n");
		
		// add the damage cap
		varsAndVals += "Player Max Damage: ";
		varsAndVals += ( String.valueOf( myGOFactory.getPlayer().getMaxDamage() ) + "\n");
		
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
		System.out.println("\n Object count: " + myObjs.getSize() + "\n");
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
			if( currentObj instanceof NonPlayerCar){ // found a car
				
				// increase its highest pylon count
				((NonPlayerCar)currentObj).hitPylon( ((NonPlayerCar)currentObj).getHighestPylon() + 1 );
				
				// TODO npc strat optimize
				// just make a NPC method changeStrat() and changeStrat(ICarStrat) 
				if( ((NonPlayerCar)currentObj).getStrategy() instanceof RaceStrategy ){
					((NonPlayerCar)currentObj).setStrategy(new DestructionDerbyStrategy(((NonPlayerCar)currentObj), myGOFactory.getPlayer() ));
				} else if ( ((NonPlayerCar)currentObj).getStrategy() instanceof DestructionDerbyStrategy ){
					((NonPlayerCar)currentObj).setStrategy(new RaceStrategy(((NonPlayerCar)currentObj), this.getProxy()));
				}
			}
		}
	}
	
	// tell the player car to turn the wheels left
	public void turnPlayerLeft(){
		myGOFactory.getPlayer().turnLeft();
	}
	
	// tell the player car to turn the wheels left
	public void turnPlayerRight(){
		myGOFactory.getPlayer().turnRight();
	}
	
	// tell the player car to turn accelerate
	public void acceleratePlayer(){
		myGOFactory.getPlayer().accelerate();
	}
	
	// tell the player car to brake
	public void brakePlayer(){
		myGOFactory.getPlayer().brake();
	}
	
	// add damage to the player car for colliding with another car
	public boolean carCarCollision(Car car1, Car car2){		
		// add damage to the cars
		car1.addDamage(2);
		car2.addDamage(2);
		
		// add a shock wave to the word at location of car1
		myObjs.add(myGOFactory.getGameObject("SHOCKWAVE", car1.getX(), car1.getY() ));

		// 10% chance at creating an oil slick at car1
		// 10% chance at creating an oil slick at car2
		Random numGen = new Random();
		int r = numGen.nextInt();
		r = r%10;
		if(r<=4)
			myObjs.add( myGOFactory.getGameObject("OIL", car1.getX(), car1.getY() ) );
		else if(r>4)
			myObjs.add( myGOFactory.getGameObject("OIL", car2.getX(), car2.getY() ) );
		
		// check if we got a player car and it is still ok
		if(car1 instanceof PlayerCar )
			if( !car1.isOkay() ){
				playerDied();
				return false;
			}
		else if(car2 instanceof PlayerCar)
			if( !car2.isOkay() ){
				playerDied();
				return false;
			}
		return true;
	}
	
	// add damage to the player car for colliding with a bird
	public boolean carBirdCollision(Car car, Bird bird){
		car.addDamage(1);
		
		// add a new shock wave at location of the car
		myObjs.add(myGOFactory.getGameObject("SHOCKWAVE", car.getX(), car.getY() ));
		
		// TODO handle NPC (car) dying
		// currently just stops in spot on map
		
		// check if the car is still ok
		if( !myGOFactory.getPlayer().isOkay() )
			playerDied();
		
		return true;
	}
	
	// create new oil slick and add to object collection
	public void addOilSlick(){
		OilSlick os = new OilSlick();
		myObjs.add(os);
		notifyObservers();
	}
	
	// tell the player car that it just entered an oil slick
	public void playerEntersOil(){
		myGOFactory.getPlayer().enterOil();
	}
	
	// tell the player car that it just exited an oil slick
	public void playerExitsOil(){
		myGOFactory.getPlayer().exitOil();
	}
	
	// simulate the player colliding with and picking up a fuel can
	// then add a new one to the world
	public boolean carGetsFuelCan(Car car, FuelCan fuelCan){
		
		car.addFuel(fuelCan.getSize());
		myObjs.remove(fuelCan);
		
		// create new fuel can and add to GameWorld
		FuelCan newCan = new FuelCan();
		myObjs.add(newCan);
		
		return true;
	}
	
	// simulate a player running over pylon, find the pylon and try to advance player 
	public boolean carHitPylon(Car car, Pylon pylon){
		
		boolean success = false;
		success = car.hitPylon(pylon.getSequenceNumber());
		
		// if successful, was it a player, and did they win?
		if(car instanceof PlayerCar){
			if(success)
				if( car.getHighestPylon() == myGOFactory.getPylonCount() ){
					playerWins();
					// return false so sound does not play
					return false;
				}
			return success;
		}
		
		// was an npc, got updated appropriately
		// return false for no sound
		return false;
	}
	
	// the player won the game by reaching the last pylon
	private void playerWins(){
		// for now, print to console and close
	//	System.out.println("The player won the game b reaching the final pylon");
		
		// stop bgm for dramatic effect
		stopBGM();
		
		if(soundIsOn())
			mySoundLib.playPlayerWinsSound();
		
		// display a pane with information about the application
		JOptionPane.showMessageDialog(null, "Awesome, you won! Good job!", "Player completed race...",
								JOptionPane.PLAIN_MESSAGE);
				
		System.exit(0);
	}
	
	// the player has died, from 0 fuel, or reaching max damage
	private void playerDied(){
		
		// play the sound, if sound is on
		if( soundIsOn() )
			mySoundLib.playCarExplosionSound();
		
		// stop bgm for dramatic affect
		stopBGM();
		
		// take a life away
		livesLeft -= 1;
		
		// check for game over
		if(livesLeft == 0){
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
			loop:
			while( anIterator.hasNext() ){
				currentObj = anIterator.next();
				if(currentObj instanceof Pylon)
					if( ((Pylon)currentObj).getSequenceNumber() == myGOFactory.getPlayer().getHighestPylon() ){
						// found the pylon they were at, reset the player there
						myGOFactory.getPlayer().reset( ((Pylon)currentObj).getX(), ((Pylon)currentObj).getY() );
						// reset the clock
						clockTime = 0;
						break loop;
					}
			}	
		}
		
		// display a pane with information about dying
		JOptionPane.showMessageDialog(null, "Sorry, you died! You probably ran out of fuel, or took too much damage. Better luck next time!", "Player died...",
								JOptionPane.PLAIN_MESSAGE);
		
		// play bgm again
		if(soundIsOn())
			playBGM();
	}
	
	// the player ran out of lives, and the game is over
	private void gameOver(){
		
		if(soundIsOn())
			mySoundLib.playGameOverSound();
		
		// display a pane with information game over
		JOptionPane.showMessageDialog(null, "Sorry, you died and ran out of lives! Better luck next time!", "Game Over...",
								JOptionPane.PLAIN_MESSAGE);
		
		System.exit(0);
	}
	
	
	// advance the clock and make appropriate changes to world
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void advanceGameClock(int ms){
		
		Vector toBeRemoved = new Vector();
		
		// actually advance the clock
		clockTime += ( (float)ms / 1000 );
		
		// create iterator to go through and move objects
		IIterator anIterator = myObjs.getIterator();
		
		Object currentObj = new Object();
		
		while( anIterator.hasNext() ){
			currentObj = anIterator.next();
			// if this object is movable...
			if( currentObj instanceof MovableObject){
				// move it!
				((MovableObject) currentObj).move(ms);
				
				// if its a shock wave, prep to remove if it's expired
				if( currentObj instanceof ShockWave ){
					if( ((ShockWave)currentObj).isExpired() )
						toBeRemoved.add(currentObj);
				}
			}
		}
		// check if the car is still OK (fuel)
		if( !myGOFactory.getPlayer().isOkay() )
			playerDied();
		
		// remove all objects in toBeRemoved
		while(!toBeRemoved.isEmpty()){
			myObjs.remove(toBeRemoved.firstElement());
			toBeRemoved.remove(toBeRemoved.firstElement());
		}
		
		// done moving objects, time to detect collisions
		detectCollisions();
		
	}
	
	public boolean remove(GameObject obj){
		return myObjs.remove(obj);
	}
	
	// get how long the game has been going
	public float getClockTime(){
		return clockTime;
	}
	
	// get how many lives the player has left
	public int getLivesLeft(){
		return livesLeft;
	}
	
	// get the highest player reached pylon
	public int getHighestPylon(){
		return myGOFactory.getPlayer().getHighestPylon();
	}
	
	// get how much fuel the player has left
	public float getPlayerFuel(){
		return myGOFactory.getPlayer().getFuelLevel();
	}

	// get the player damage level
	public int getPlayerDamage(){
		return myGOFactory.getPlayer().getDamageLevel();
	}
	
	// turn the sound on
	public void turnSoundOn(){
		sound = true;
		playBGM();
	}
	
	// turn the sound off
	public void turnSoundOff(){
		sound = false;
		stopBGM();
	}
	
	// toggle the sound
	public void toggleSound(){
		if(sound){
			sound = false;
		}
		else{
			sound = true;
		}
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
		
		GameWorldProxy myProxy = new GameWorldProxy(this);
		
		// go through all observers and pass them a proxy of myself
		if(!myObservers.isEmpty()){
			for(i = 0; i < myObservers.size(); i++)
				((IObserver)myObservers.elementAt(i)).update(myProxy);
		}
	}
	
	// return playerCar for printing in tests
	public PlayerCar getPlayerCar(){
		return myGOFactory.getPlayer();
	}
	
	// raise add pylon flag
	public void setNewPylonFlag(boolean yesNo){
		addNewPylon = yesNo;
	}
	
	// check the add pylon flag
	public boolean doAddNewPylon(){
		return addNewPylon;
	}
	
	// raise add fuel can flag
	public void setNewFuelCanFlag(boolean yesNo){
		addNewFuelCan = yesNo;
	}
	
	// check the add fuel can flag 
	public boolean doAddNewFuelCan(){
		return addNewFuelCan;
	}

	@Override
	// give out an iterator for my GameObjects
	public IIterator getIterator() {
		return myObjs.getIterator();
	}
	
	// get a proxy for this game world
	public GameWorldProxy getProxy(){
		GameWorldProxy gwp = new GameWorldProxy(this);
		return gwp;
	}

	@Override
	// give out a set of my objects
	public GameObjectCollection getObjects() {
		return myObjs;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Vector getObservers() {
		return myObservers;
	}
	
	public GameObjectFactory getGOF(){
		return myGOFactory;
	}

	// do whatever is needed to detect collisions
	private void detectCollisions() {
		
		int i, j; // for counting and looping 
		
		// detect and give collision events to handler
		for( i = 0; i < (myObjs.getSize()-1); i++ ){
			for( j = i+1; j < myObjs.getSize(); j++ ){
				// detect if obj[i] is colliding with obj[j] 
				if( ((ICollider)myObjs.elementAt(i)).collidesWith( (ICollider)myObjs.elementAt(j) ))
					myCollisionHandler.addCollision((GameObject)myObjs.elementAt(i), (GameObject)myObjs.elementAt(j));
			}
		}
		
		// done checking all objs, have handler do cleanup and finish
		myCollisionHandler.handleCollisions();
	}
	
	// turn BGM on
	public void playBGM(){
		if( soundIsOn() )
			mySoundLib.playBGM();
	}
	
	// turn BGM off
	public void stopBGM(){
		mySoundLib.stopBGM();
	}
}