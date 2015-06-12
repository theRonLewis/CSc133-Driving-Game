package a3;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Pick Up Fuel command
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
public class CarGetsFuelCommand extends AbstractAction {
	
	private static CarGetsFuelCommand theCarGetsFuelCommand;
	
	private static GameWorld target;

	// sound library
	private SoundLibrary mySoundLib = SoundLibrary.getInstance();
	
	private CarGetsFuelCommand(){
		super("Car gets Fuel");
	}
	
	// request the instance of the command
	public static CarGetsFuelCommand getInstance(){
		if(theCarGetsFuelCommand == null)
			theCarGetsFuelCommand = new CarGetsFuelCommand();
		return theCarGetsFuelCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target sent
		if(target == null)
			target = tgw;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display the source of request in console
	//	System.out.println("Pick Up Fuel requested from " + e.getActionCommand()
	//					+ " " + e.getSource().getClass() );
		
		if(target != null){
			boolean success = false;
			// have the player pick up fuel
			
			if ( ((CollisionEvent)e.getSource()).getType().equals("CAR-FUEL") ){
				if( ((CollisionEvent)e.getSource()).getFirst().getType().equals("PLAYER") || ((CollisionEvent)e.getSource()).getFirst().getType().equals("NPC") )
					success = target.carGetsFuelCan( (Car)((CollisionEvent)e.getSource()).getFirst(), (FuelCan)((CollisionEvent)e.getSource()).getSecond() );
				else 
					success = target.carGetsFuelCan( (Car)((CollisionEvent)e.getSource()).getSecond(), (FuelCan)((CollisionEvent)e.getSource()).getFirst() );
			}
			
			if(success && target.soundIsOn() ) // play sound if went OK and is on
				mySoundLib.playCarFuelSound();
			
		} else{
			System.out.println("Command received to have player get fuel can, but a GameWorld target has been set...");
		}
	}
}