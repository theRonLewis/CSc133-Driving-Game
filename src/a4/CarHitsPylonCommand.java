package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Collide With Pylon command
 * 
 * It can be instantiated, and but may also
 * be used via the static method getInstance()
 * if assigning to a GUI button or keybinding.
 * 
 * This implements the singleton design pattern.
 * 
 * In order to be used properly, you must also use
 * the setTarget(GameWorld) method to assign the 
 * command to a game world.
 * 
 */

@SuppressWarnings("serial")
public class CarHitsPylonCommand extends AbstractAction {
	
	private static CarHitsPylonCommand theCarHitsPylonCommand;
	
	private static GameWorld target;
	
	// sound library
	private SoundLibrary mySoundLib = SoundLibrary.getInstance();

	private CarHitsPylonCommand(){
		super("Collide with Pylon");
	}
	
	// request the instance of the command
	public static CarHitsPylonCommand getInstance(){
		if(theCarHitsPylonCommand == null)
			theCarHitsPylonCommand = new CarHitsPylonCommand();
		return theCarHitsPylonCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target sent
		if(target == null)
			target = tgw;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display the source of request in console
	//	System.out.println("Collide with Pylon requested from " + e.getActionCommand()
	//					+ " " + e.getSource().getClass() );
		
		if(target != null){
			boolean success = false;
			
			// actually collide car with the pylon
			if ( ((CollisionEvent)e.getSource()).getType().equals("CAR-PYLON") )
				if( ((CollisionEvent)e.getSource()).getFirst() instanceof Car ) 
					success = target.carHitPylon((Car)((CollisionEvent)e.getSource()).getFirst(), (Pylon)((CollisionEvent)e.getSource()).getSecond());
				else 
					success = target.carHitPylon((Car)((CollisionEvent)e.getSource()).getSecond(), (Pylon)((CollisionEvent)e.getSource()).getFirst());
			
			// if sound is on, play sound (if OK from Car-Pylon return)
			if(success)
				if( target.soundIsOn() )
					mySoundLib.playCarPylonSound();
			
		} else{
			System.out.println("Command received to collide with pylon, but a GameWorld target has not been set...");
		}		
	}
}