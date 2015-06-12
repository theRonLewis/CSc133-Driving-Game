package a3;

import java.util.Vector;

// this class represents a GameWorld, with a collection of GameObjects
// other classes can initiate commands on GameWorld to have it operate
// on the objects in it's world

public class GameWorldProxy implements IObservable, IGameWorld {

	// must have a collection of GameObjects
	private GameObjectCollection myObjs = new GameObjectCollection();
	
	// keep a list of things observing the game world
	@SuppressWarnings({ "rawtypes" })
	private Vector myObservers;
	
	// amount of time passed since game started
	private float clockTime;
	
	// number of lives the player has left
	private int livesLeft;
	
	// must have a player car
	//private PlayerCar playerCar;
	
	// copy of the GO Factory so we can access internal objects
	private GameObjectFactory myGOFactory;
	
	// sound on or off
	private boolean sound = true;
	
	// constructor to initialize observers
	@SuppressWarnings("rawtypes")
	GameWorldProxy(){
		// initialize the observers
		myObservers = new Vector();
	}
	
	GameWorldProxy(IGameWorld gwp){
		myObjs = gwp.getObjects();
		myObservers = gwp.getObservers();
		clockTime = gwp.getClockTime();
		livesLeft = gwp.getLivesLeft();
		sound = gwp.soundIsOn();
		myGOFactory = gwp.getGOF();
	}
	
	// creates an initial game world
	public void initLayout(){
		System.out.println("Cannot initialize, proxy world.");
	}
	
	// return single string, formatted
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
	
	// OVERRIDDEN, PROXY CLASS
	// go through and create a random color for each object
	// objects that should not be able to change colors should
	// have the functions overridden
	public void changeObjColors(){
		System.out.println("Cannot change object colors, proxy world.");
	}
	
	// OVERRIDDEN, PROXY CLASS
	// go through and change strategies of all NPCs
	public void changeNPCStrategies(){
		System.out.println("Cannot change NPC strategies, proxy world.");
	}
	
	// OVERRIDDEN, PROXY CLASS
	// tell the player car to turn the wheels left
	public void turnPlayerLeft(){
		System.out.println("Cannot turn player left, proxy world.");
	}
	
	// OVERRIDDEN, PROXY CLASS
	// tell the player car to turn the wheels left
	public void turnPlayerRight(){
		System.out.println("Cannot turn player right, proxy world.");
	}
	
	// OVERRIDDEN, PROXY CLASS
	// tell the player car to turn accelerate
	public void acceleratePlayer(){
		System.out.println("Cannot accelerate player, proxy world.");
	}
	
	// OVERRIDDEN, PROXY CLASS
	// tell the player car to brake
	public void brakePlayer(){
		System.out.println("Cannot brake player, proxy world.");
	}
	
	// OVERRIDDEN, PROXY CLASS
	// add damage to the player car for colliding with another car
	public boolean carCarCollision(Car car1, Car car2){
		System.out.println("Cannot collide player with car, proxy world.");
		return false;
	}
	
	// OVERRIDDEN, PROXY CLASS
	// add damage to the player car for colliding with a bird
	public boolean carBirdCollision(Car car, Bird bird){
		System.out.println("Cannot collide player with bird, proxy world.");
		return false;
	}
	
	// OVERRIDDEN, PROXY CLASS
	// create new oil slick and add to object collection
	public void addOilSlick(){
		System.out.println("Cannot add oil slick, proxy world.");
	}
	
	// OVERRIDDEN, PROXY CLASS
	// tell the player car that it just entered an oil slick
	public void playerEntersOil(){
		System.out.println("Cannot put  player in oil, proxy world.");
	}
	
	// OVERRIDDEN, PROXY CLASS
	// tell the player car that it just exited an oil slick
	public void playerExitsOil(){
		System.out.println("Cannot remove player from oil, proxy world.");
	}
	
	// OVERRIDDEN, PROXY CLASS
	// simulate the player colliding with and picking up a fuel can
	// currently there is no collision detection, so we just pick up the 
	// first instance of a fuel can in the game world, and add a new one
	public boolean carGetsFuelCan(Car car, FuelCan fuelCan){
		System.out.println("Cannot have player get fuel, proxy world.");
		return false;
	}
	
	// OVERRIDDEN, PROXY CLASS
	// simulate a player running over pylon # p 
	public boolean carHitPylon(Car car, Pylon pylon){
		System.out.println("Cannot have player hit pylon, proxy world.");	
		return false;
	}
	
	// OVERRIDDEN, PROXY CLASS
	// advance the clock and make appropriate changes to world
	public void advanceGameClock(int ms){
		System.out.println("Cannot advance game clock, proxy world.");
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
	
	// OVERRIDDEN, PROXY CLASS
	// turn the sound on
	public void turnSoundOn(){
		System.out.println("Cannot turn sound on, proxy world.");
	}
	
	// OVERRIDDEN, PROXY CLASS
	// turn the sound off
	public void turnSoundOff(){
		System.out.println("Cannot turn sound off, proxy world.");
	}
	
	// OVERRIDDEN, PROXY CLASS
	// toggle the sound
	public void toggleSound(){
		System.out.println("Cannot toggle the sound, proxy world.");
	}
	
	public boolean soundIsOn(){
		return sound;
	}
	
	// return playerCar for printing in tests
	public PlayerCar getPlayerCar(){
		return myGOFactory.getPlayer();
	}

	@Override
	// give out an iterator for my GameObjects
	public IIterator getIterator() {
		return myObjs.getIterator();
	}

	@Override
	public IGameWorld getProxy() {
		return this;
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

	// OVERRIDDEN, PROXY CLASS
	@Override
	public boolean remove(GameObject obj) {
		System.out.println("Cannot remove an object, this is a proxy world...");
		return false;
	}

	@Override
	public GameObjectFactory getGOF() {
		return myGOFactory;
	}

	// OVERRIDDEN, PROXY CLASS
	@Override
	public void setNewPylonFlag(boolean yesNo) {
		System.out.println("Cannot set flag to add new pylon, this is a proxy world...");
	}

	// OVERRIDDEN, PROXY CLASS
	@Override
	public boolean doAddNewPylon() {
		System.out.println("Cannot technically check flag or add new pylon, this is a proxy world...");
		return false;
	}

	// OVERRIDDEN, PROXY CLASS
	@Override
	public void setNewFuelCanFlag(boolean yesNo) {
		System.out.println("Cannot set flag to add new fuel can, this is a proxy world...");
		
	}

	// OVERRIDDEN, PROXY CLASS
	@Override
	public boolean doAddNewFuelCan() {
		System.out.println("Cannot technically check flag or add new fuel can, this is a proxy world...");
		return false;
	}

	// OVERRIDDEN, PROXY CLASS
	@Override
	public void detectCollisions() {
		System.out.println("Cannot initiate detect collisions, this is a proxy world...");
	}
}