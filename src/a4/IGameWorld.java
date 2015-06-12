package a4;

import java.util.Vector;

/*  custom IGameWorld interface
 *  Used to pass an unchangeable copy
 *  of a GameWorld to an observer
 *  
 *  currently only implements the things
 *  that would be used by ScoreView and MapView
 *  
 */

public interface IGameWorld {

	// OVERRIDDEN IN PROXY GW
	// creates an initial game world
	public void initLayout();
	
	// return array of strings formatted
	public String getGameState();
	
	// print a textual map of the game world to console
	public void printTextMap();
	
	// OVERRIDDEN IN PROXY GW
	// go through and create a random color for each object
	// objects that should not be able to change colors should
	// have the functions overridden
	public void changeObjColors();

	// OVERRIDDEN IN PROXY GW
	// go through and change strategies of all NPCs
	public void changeNPCStrategies();
	
	// OVERRIDDEN IN PROXY GW
	// tell the player car to turn the wheels left
	public void turnPlayerLeft();
	
	// OVERRIDDEN IN PROXY GW
	// tell the player car to turn the wheels left
	public void turnPlayerRight();

	// OVERRIDDEN IN PROXY GW
	// tell the player car to turn accelerate
	public void acceleratePlayer();

	// OVERRIDDEN IN PROXY GW
	// tell the player car to brake
	public void brakePlayer();

	// OVERRIDDEN IN PROXY GW
	// add damage to the player car for colliding with another car
	public boolean carCarCollision(Car car1, Car car2);
	
	// OVERRIDDEN IN PROXY GW
	// add damage to the player car for colliding with a bird
	public boolean carBirdCollision(Car car, Bird bird);

	// OVERRIDDEN IN PROXY GW
	// create new oil slick and add to object collection
	public void addOilSlick();
	
	// OVERRIDDEN IN PROXY GW
	// tell the player car that it just entered an oil slick
	public void playerEntersOil();

	// OVERRIDDEN IN PROXY GW
	// tell the player car that it just exited an oil slick
	public void playerExitsOil();
	
	
	// OVERRIDDEN IN PROXY GW
	// player got some fuel
	public boolean carGetsFuelCan(Car car, FuelCan fuelCan);

	// OVERRIDDEN IN PROXY GW
	// simulate a player running over pylon # p 
//	public boolean carHitPylon(Car car, Pylon pylon);
	
	// OVERRIDDEN IN PROXY GW
	// advance the clock and make appropriate changes to world
	public void advanceGameClock(int ms);
	
	// see what time is on the clock
	public float getClockTime();
	
	// how many lives does the player still have? 
	public int getLivesLeft();
	
	// which pylon has the player reached?
	public int getHighestPylon();
	
	// how much fuel does the player have?
	public float getPlayerFuel();
	
	// how much damage does the player have?
	public int getPlayerDamage();
	
	// is the sound on?
	public boolean soundIsOn();
	
	// OVERRIDDEN IN PROXY GW
	// turn the sound on
	public void turnSoundOn();
	
	// OVERRIDDEN IN PROXY GW
	// turn the sound off
	public void turnSoundOff();

	// OVERRIDDEN IN PROXY GW
	// toggle the sound
	public void toggleSound();
	
	// get an iterator for the object collection
	public IIterator getIterator();
	
	// get a proxy for this game world
	public IGameWorld getProxy();
	
	// get the collection of game objects
	public GameObjectCollection getObjects();
	
	// get the items currently observing me
	@SuppressWarnings("rawtypes")
	public Vector getObservers();
	
	// return playerCar for printing in tests
	public PlayerCar getPlayerCar();
	
	// OVERRIDDEN IN PROXY GW
	// remove an object from the collections
	public boolean remove(GameObject obj);
	
	// get the GameObjectFactory for accessing things like player car
	public GameObjectFactory getGOF();

	// OVERRIDDEN IN PROXY GW
	// raise add pylon flag
	public void setNewPylonFlag(boolean yesNo);
	
	// OVERRIDDEN IN PROXY GW
	// check the add pylon flag
	public boolean doAddNewPylon();
	
	// OVERRIDDEN IN PROXY GW
	// raise add fuel can flag
	public void setNewFuelCanFlag(boolean yesNo);
	
	// OVERRIDDEN IN PROXY GW
	// check the add fuel can flag 
	public boolean doAddNewFuelCan();

}
