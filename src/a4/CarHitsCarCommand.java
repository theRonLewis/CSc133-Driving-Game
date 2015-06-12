package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Collide With NPC command
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
public class CarHitsCarCommand extends AbstractAction {
	
	private static CarHitsCarCommand theCarHitsCarCommand;
	
	private static GameWorld target; 
	
	// sound library
	private SoundLibrary mySoundLib = SoundLibrary.getInstance();
	
	private CarHitsCarCommand(){
		super("Car collides with Car");
	}
	
	// request the instance of the command
	public static CarHitsCarCommand getInstance(){
		if(theCarHitsCarCommand == null)
			theCarHitsCarCommand = new CarHitsCarCommand();
		return theCarHitsCarCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target sent
		if(target == null)
			target = tgw;
	}
	
	public void actionPerformed(ActionEvent e){		
		
		// display the source of request in console
	//	System.out.println("Collide with NPC requested from " + e.getActionCommand()
	//					+ " " + e.getSource().getClass() );
		
		if(target != null){
			boolean success = false;
			
			// actually collide player with the npc
			if ( ((CollisionEvent)e.getSource()).getType().equals("CAR-CAR") )
				success = target.carCarCollision((Car)((CollisionEvent)e.getSource()).getFirst(), (Car)((CollisionEvent)e.getSource()).getSecond());
			
			if(success) // play sound if went OK
				if( target.soundIsOn() )
					mySoundLib.playCarCarCollisionSound();
			
		} else{
			System.out.println("Command received to collide player with NPC, but a GameWorld target has been set...");
		}
	}
}
