package a3;

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
		if( (obj1.getType().equals("PLAYER") && obj2.getType().equals("OIL")) || ( obj2.getType().equals("PLAYER") && obj1.getType().equals("OIL")) )
			myType = "PLAYER-OIL";
		
		// car and bird
		else if( (obj1.getType().equals("NPC") && obj2.getType().equals("BIRD")) || ( obj2.getType().equals("NPC") && obj1.getType().equals("BIRD") ) 
			|| (obj1.getType().equals("PLAYER") && obj2.getType().equals("BIRD")) || ( obj2.getType().equals("PLAYER") && obj1.getType().equals("BIRD") )	)
			myType = "CAR-BIRD";
		
		// car and car
		// (already handles player-player for future!)
		else if( (obj1.getType().equals("NPC") && obj2.getType().equals("NPC"))
			|| (obj1.getType().equals("NPC") && obj2.getType().equals("PLAYER")) || ( obj2.getType().equals("NPC") && obj1.getType().equals("PLAYER") ) 
			|| (obj1.getType().equals("PLAYER") && obj2.getType().equals("PLAYER")) )
			myType = "CAR-CAR";
		
		// car and pylon
		else if( (obj1.getType().equals("NPC") && obj2.getType().equals("PYLON")) || ( obj2.getType().equals("NPC") && obj1.getType().equals("PYLON") ) 
			|| (obj1.getType().equals("PLAYER") && obj2.getType().equals("PYLON")) || ( obj2.getType().equals("PLAYER") && obj1.getType().equals("PYLON"))	)
			myType = "CAR-PYLON";

		// car and fuel
		else if( (obj1.getType().equals("NPC") && obj2.getType().equals("FUEL")) || ( obj2.getType().equals("NPC") && obj1.getType().equals("FUEL") ) 
			|| (obj1.getType().equals("PLAYER") && obj2.getType().equals("FUEL")) || ( obj2.getType().equals("PLAYER") && obj1.getType().equals("FUEL"))	)
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
		if( (getFirst() == other.getFirst() & getSecond() == other.getSecond()) || ( getFirst() == other.getSecond() && getSecond() == other.getFirst() ) )
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

