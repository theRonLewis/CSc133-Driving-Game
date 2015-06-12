package a3;

import java.awt.event.ActionEvent;
import java.util.Vector;

@SuppressWarnings("rawtypes")
public class CollisionHandler {

	Vector pastCollisions = new Vector();
	Vector newCollisions = new Vector();
	
	GameObjectCollection theCollection;
	
	// for finding the current newCollision in the pastCollisions
	private boolean foundIt;
	
	// commands...
	private PlayerEntersOilSlickCommand myPlayerEntersOilSlickCommand = PlayerEntersOilSlickCommand.getInstance();
	private PlayerExitsOilSlickCommand myPlayerExitsOilSlickCommand = PlayerExitsOilSlickCommand.getInstance();
	private CarHitsBirdCommand myCarHitsBirdCommand = CarHitsBirdCommand.getInstance();
	private CarHitsCarCommand myCarHitsCarCommand = CarHitsCarCommand.getInstance();
	private CarHitsPylonCommand myCarHitsPylonCommand = CarHitsPylonCommand.getInstance();
	private CarGetsFuelCommand myCarGetsFuelCommand = CarGetsFuelCommand.getInstance();
	
	CollisionHandler(GameObjectCollection goc){
		theCollection = goc;
		foundIt = false;
	}
	
	@SuppressWarnings("unchecked")
	public void addCollision(GameObject o1, GameObject o2){
		//System.out.println("Adding a collision... " + o1.getType() + " with " + o2.getType() );
		newCollisions.add(new CollisionEvent(o1, o2));
	}
	
	public void handleCollisions(){
		
		int i, j; // for some loops
		
		if( pastCollisions.size() == 0 ){
			// no past collisions
			// handle ALL current collisions
			for( i=0; i<newCollisions.size(); i++){
				// check for player-oil, set to entering oil
				if( ((CollisionEvent)newCollisions.elementAt(i)).getType().equals("PLAYER-OIL"))
					((CollisionEvent)newCollisions.elementAt(i)).setEnteringOil(true);
				
		//		System.out.println("handling all collisions #" + i + "... Obj1: " + ((CollisionEvent)newCollisions.elementAt(i)).getFirst().getType() + " ---- obj2: " + ((CollisionEvent)newCollisions.elementAt(i)).getSecond().getType() );
				doTheCollision(((CollisionEvent)newCollisions.elementAt(i)));
			}
		}else{ // there were some past collisions
			// for each new collision
			for( i=0; i<newCollisions.size(); i++){
				// check each past collision
				for( j=0; j<pastCollisions.size();  j++)
				if( (((CollisionEvent)(pastCollisions.elementAt(j))).isEqual(((CollisionEvent)newCollisions.elementAt(i))))){
					// raise a flag that we found it in past collisions
					foundIt = true;
				}
				
				if(!foundIt){
					// check for player-oil, set to entering oil
					if( ((CollisionEvent)newCollisions.elementAt(i)).getType().equals("PLAYER-OIL"))
						((CollisionEvent)newCollisions.elementAt(i)).setEnteringOil(true);
				//	System.out.println("handling some collisions #" + i + "... Obj1: " + ((CollisionEvent)newCollisions.elementAt(i)).getFirst().getType() + " ---- obj2: " + ((CollisionEvent)newCollisions.elementAt(i)).getSecond().getType() );
					doTheCollision(((CollisionEvent)newCollisions.elementAt(i)));
				}
				// reset flag
				foundIt = false;
			}
		}
		
		// done handling collisions, now reset with
		// newCollisions replacing pastCollsions
		// also handles player exiting, could be done here instead
		reset();
	}
	
	private void doTheCollision(CollisionEvent e){
		
		ActionEvent ae = new ActionEvent(e, 1, "The detected collision event...");
		
		switch(e.getType()){
			case "PLAYER-OIL":
				if( e.isPlayerEnteringOil() )
					myPlayerEntersOilSlickCommand.actionPerformed(ae);
				else if( !e.isPlayerEnteringOil() )
					myPlayerExitsOilSlickCommand.actionPerformed(ae);
				break;
			case "CAR-BIRD":
				myCarHitsBirdCommand.actionPerformed(ae);
				break;
			case "CAR-CAR":
				myCarHitsCarCommand.actionPerformed(ae);
				break;
			case "CAR-PYLON":
				myCarHitsPylonCommand.actionPerformed(ae);
				break;
			case "CAR-FUEL":
				myCarGetsFuelCommand.actionPerformed(ae);
				break;
			case "NULL":
				// nothing to do
				break;
			default:
				System.out.println("doTheCollision() in CollisionHandler Received some undefined collision event...");
				break;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void reset(){
		// finished handling all the collisions, time to reset the vectors
		// first, must see if car exited oil before discarding this new set

		boolean triggerOilExit = false;
		CollisionEvent theCollision = null;
		
		int i, j;
		
		for( i=0; i<pastCollisions.size(); i++){
			// see if there is a previously occurring PLAYER and OIL SLICK collision
			if( ((CollisionEvent)(pastCollisions.elementAt(i))).getType().equals("PLAYER-OIL") ){
				
				// save this CollisionEvent for command
				theCollision = ((CollisionEvent)(pastCollisions.elementAt(i)));
				theCollision.setEnteringOil(false);
				
				// check to see if that player oil event exists in the new array
				// set the trigger oil exit flag to true unless found
				triggerOilExit = true;
				
				getOut:
				// unless you can find it in the new collisions
				for( j=0; j<newCollisions.size(); j++ ){
					if(  ((CollisionEvent)(newCollisions.elementAt(j))).isEqual(theCollision) ){
						// found the event in the new collisions,
						// its still happening, do NOT trigger the command
						triggerOilExit = false;
						break getOut;
					}
				}
			}
		}
		
		if( triggerOilExit ){
			theCollision.setEnteringOil(false);
			doTheCollision(theCollision);
		}
		
		// put all currently detected collisions as past collisions
		pastCollisions = new Vector(newCollisions);
		
		// clear the new collisions list to cleanup for next go around
		newCollisions.clear();
	}
}
