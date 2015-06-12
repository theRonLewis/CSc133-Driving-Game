package a4;

public class CollisionEvent {
	
	// 2 game objects that are colliding
	private GameObject obj1, obj2;
	
	// for handling collision
	private String myType;
	
	private boolean enterOil;
	
	// constructor
	public CollisionEvent(GameObject o1, GameObject o2){
		
		// save the objs
		obj1 = o1;
		obj2 = o2;
		
		// determine the type...
		
		// player and oil
		if( (obj1 instanceof PlayerCar && obj2 instanceof OilSlick) || (obj2 instanceof PlayerCar && obj1 instanceof OilSlick) )
			myType = "PLAYER-OIL";
		
		// car and bird
		else if( (obj1 instanceof Car && obj2 instanceof Bird) || (obj2 instanceof Car && obj1 instanceof Bird) )
			myType = "CAR-BIRD";
		
		// car and car
		else if( obj1 instanceof Car && obj2 instanceof Car )
			myType = "CAR-CAR";
		
		// car and pylon

		else if( (obj1 instanceof Car && obj2 instanceof Pylon)	|| (obj2 instanceof Car && obj1 instanceof Pylon) )
			myType = "CAR-PYLON";

		// car and fuel
		else if( (obj1 instanceof Car && obj2 instanceof FuelCan) || ( obj2 instanceof Car && obj1 instanceof FuelCan ) )
			myType = "CAR-FUEL";
		
		// null type, nothing to do
		else
			myType = "NULL";
	}
	
	public GameObject getFirst(){
		return obj1;
	}
	
	public GameObject getSecond(){
		return obj2;
	}
	
	public boolean isEqual(CollisionEvent other){
		if( (getFirst() == other.getFirst() && getSecond() == other.getSecond()) || ( getFirst() == other.getSecond() && getSecond() == other.getFirst() ) )
			return true;
		return false;
	}
	
	public String getType(){
		return myType;
	}
	
	public boolean isPlayerEnteringOil(){
		return enterOil;
	}
	
	public void setEnteringOil(boolean yesNo){
		enterOil = yesNo;
	}
}

