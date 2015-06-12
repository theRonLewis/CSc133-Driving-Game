package a2;

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
	private GameWorld tgw;

	public RaceStrategy(Car thisCar, GameWorld gw){
		myCar = thisCar;
		tgw = gw;
	}
	
	public String toString(){
		return "RaceStrategy";
	}
	
	public void applyStrategy(){
		// code here to find next pylon and set car to move towards that
		
		// the next pylon we want to go to
		int nextPylon = myCar.getHighestPylon() + 1;
		float pX = 0, 
			  pY = 0;
		
		// get an iterator for the collection
		IIterator anIterator = tgw.myObjs.getIterator();
		
		// setup a placeholder object
		Object currentObj = new Object();
		// iterate through, and check each one
		while( anIterator.hasNext() ){
			currentObj = anIterator.next();
			if(currentObj instanceof Pylon)
				if( ((Pylon)currentObj).getSequenceNumber() == nextPylon ){
					// found the next pylon they need to get to, save coordinates
					pX = ((Pylon)currentObj).getLocation().getX();
					pY = ((Pylon)currentObj).getLocation().getY();
				}
		}
		
		// change in x and y
		float dx, dy;
		
		// calculate the change
		dx = pX - myCar.getLocation().getX();
		dy = pY - myCar.getLocation().getY();
		
		// check for needing to move on axis, this also accounts
		// for checking of division by 0
		
		// is the y val the same?
		if( dy == 0 )
			if( dx < 0 ){
				// target pylon is directly to the left
				myCar.setHeading(270);
				return;
			}
			else if( dx > 0 ){
				// target pylon is directly to the right
				myCar.setHeading(90);
				return;
			}
		
		// is x val the same?
		if( dx == 0 )
			if( dy > 0 ){
				// target pylon is directly above
				myCar.setHeading(0);
				return;
			}else if( dy < 0 ){
				// target pylon is directly below
				myCar.setHeading(180);
				return;
			}
		
		// target wasn't at a 90 degree interval,
		// need to determine the angle
		
		int angle = (int)Math.toDegrees( Math.atan( (dx/dy) ) );
		
		// got the angle, now figure out which quadrant to put it in
		if( dx > 0 ){ 
			if( dy > 0 ){
				// top right
				myCar.setHeading( Math.abs(angle) );
				return;
			}else if( dy < 0){
				// bottom right
				myCar.setHeading( 180 - angle );
				return;
			}
		}else if( dx < 0 ){
			if( dy > 0){
				// top left
				myCar.setHeading( 360 - angle );
				return;
			}else if( dy < 0){
				// bottom left
				myCar.setHeading( 180 + Math.abs(angle) );
				return;
			}
		}
		
	}
}
