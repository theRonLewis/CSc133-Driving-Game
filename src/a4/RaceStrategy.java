package a4;

/*
 * NPC Car Strategy class: Race Strategy
 * 
 * This implements ICarStrategy as a way of 
 * managing NPC AI in a GameWorld.
 * 
 * This strategy takes a car to handle, and 
 * the game world that car resides in as constructor parameters.
 * 
 * The strategy makes the NPC go towards the next highest
 * pylon, to try and beat the player.
 * 
 */

public class RaceStrategy implements ICarStrategy {

	private Car myCar;
	private GameWorldProxy tgw;

	public RaceStrategy(NonPlayerCar thisCar, GameWorldProxy gw){
		myCar = thisCar;
		tgw = gw;
	}
	
	public String toString(){
		return "RaceStrategy";
	}
	
	public void applyStrategy(){
		
		// the next pylon we want to go to
		int nextPylon = myCar.getHighestPylon() + 1;
		
		// check for trying to go to non existent pylon
		if( nextPylon > Pylon.getCount() )
			nextPylon = Pylon.getCount();
		
		float pX = 0, 
			  pY = 0;
		
		// get an iterator for the collection
		IIterator anIterator = tgw.getObjects().getIterator();
		
		// setup a placeholder object
		Object currentObj = new Object();
		// iterate through, and check each one
		while( anIterator.hasNext() ){
			currentObj = anIterator.next();
			if(currentObj instanceof Pylon)
				if( ((Pylon)currentObj).getSequenceNumber() == nextPylon ){
					// found the next pylon they need to get to, save coordinates
					pX = ((Pylon)currentObj).getX();
					pY = ((Pylon)currentObj).getY();
				}
		}
		
		// change in x and y
		float dx, dy;
		
		// calculate the change
		dx = pX - myCar.getX();
		dy = pY - myCar.getY();
		
		// awesome, atan2 handles all the hard parts of arctan calculations
		int angle = (int)Math.toDegrees( Math.atan2( dx, dy) );
		
		myCar.setHeading( angle );
	}

	/*
	// get the location of the target, for drawing
	public FloatPoint getTargetLocation(){
		
		// the next pylon we want to go to
		int nextPylon = myCar.getHighestPylon() + 1;
		
		if( nextPylon > Pylon.getCount() )
			nextPylon = Pylon.getCount();
		
		// get an iterator for the collection
		IIterator anIterator = tgw.getObjects().getIterator();
		
		// setup a placeholder object
		Object currentObj = new Object();
		// iterate through, and check each one
		while( anIterator.hasNext() ){
			currentObj = anIterator.next();
			if(currentObj instanceof Pylon)
				if( ((Pylon)currentObj).getSequenceNumber() == nextPylon ){
					// found the next pylon they need to get to, return location
					return ((Pylon)currentObj).getLocation();
				}
		}
		return null;
	}
*/
	
	@SuppressWarnings("null")
	public float getTargetX(){
		
		// the next pylon we want to go to
		int nextPylon = myCar.getHighestPylon() + 1;
		
		if( nextPylon > Pylon.getCount() )
			nextPylon = Pylon.getCount();
		
		// get an iterator for the collection
		IIterator anIterator = tgw.getObjects().getIterator();
		
		// setup a placeholder object
		Object currentObj = new Object();
		// iterate through, and check each one
		while( anIterator.hasNext() ){
			currentObj = anIterator.next();
			if(currentObj instanceof Pylon)
				if( ((Pylon)currentObj).getSequenceNumber() == nextPylon ){
					// found the next pylon they need to get to, return location
					return ((Pylon)currentObj).getX();
				}
		}
		System.out.println("There is some sort of issue with a car with a Race Strategy... no next pylon??");
		return (Float) null;
	}
	
	@SuppressWarnings("null")
	public float getTargetY(){
		
		// the next pylon we want to go to
		int nextPylon = myCar.getHighestPylon() + 1;
		
		if( nextPylon > Pylon.getCount() )
			nextPylon = Pylon.getCount();
		
		// get an iterator for the collection
		IIterator anIterator = tgw.getObjects().getIterator();
		
		// setup a placeholder object
		Object currentObj = new Object();
		// iterate through, and check each one
		while( anIterator.hasNext() ){
			currentObj = anIterator.next();
			if(currentObj instanceof Pylon)
				if( ((Pylon)currentObj).getSequenceNumber() == nextPylon ){
					// found the next pylon they need to get to, return location
					return ((Pylon)currentObj).getY();
				}
		}
		System.out.println("There is some sort of issue with a car with a Race Strategy... no next pylon??");
		return (Float) null;
	}
}
