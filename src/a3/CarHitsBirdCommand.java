package a3;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Collide With Bird command
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
public class CarHitsBirdCommand extends AbstractAction {
	
	private static CarHitsBirdCommand theCarHitsBirdCommand;
	
	private static GameWorld target;
	
	// sound library
//	private SoundLibrary mySoundLib = SoundLibrary.getInstance();

	private CarHitsBirdCommand(){
		super("Car collides with Bird");
	}
	
	// request the instance of the command
	public static CarHitsBirdCommand getInstance(){
		if(theCarHitsBirdCommand == null)
			theCarHitsBirdCommand = new CarHitsBirdCommand();
		return theCarHitsBirdCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target sent
		if(target == null)
			target = tgw;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display the source of request in console
	//	System.out.println("Collide with Bird requested from " + e.getActionCommand()
	//					+ " " + e.getSource().getClass() );
		
		if(target != null){
	//		boolean success = false;
			
			// have the player hit the bird
			if ( ((CollisionEvent)e.getSource()).getType().equals("CAR-BIRD") ){
				if( ( ((CollisionEvent)e.getSource()).getFirst().getType().equals("PLAYER") || ((CollisionEvent)e.getSource()).getFirst().getType().equals("NPC") ) && ((CollisionEvent)e.getSource()).getSecond().getType().equals("BIRD") )
					target.carBirdCollision( (Car)((CollisionEvent)e.getSource()).getFirst(), (Bird)((CollisionEvent)e.getSource()).getSecond() );
				else
					target.carBirdCollision( (Car)((CollisionEvent)e.getSource()).getSecond(), (Bird)((CollisionEvent)e.getSource()).getFirst() );
			}
			
	//		if(success) // play sound if went OK
	//			if( target.soundIsOn() )
	//				mySoundLib.playCarFuelSound();
			
		} else{
			System.out.println("Command recieved to collide with bird, but a GameWorld target has been set...");
		}	
	}
}