package a2;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Pick Up Fuel command
 * 
 * It cannot be instantiated, and must always 
 * be used via the static method getInstance().
 * 
 * This implements the singleton design pattern.
 * 
 * In order to be used properly, you must also use
 * the setTarget(GameWorld) method to assign the 
 * command to a game world.
 * 
 */

@SuppressWarnings("serial")
public class PickUpFuelCommand extends AbstractAction {
	
	private static PickUpFuelCommand thePickUpFuelCommand;
	
	private static GameWorld target;

	private PickUpFuelCommand(){
		super("Pick Up Fuel");
	}
	
	// request the instance of the command
	public static PickUpFuelCommand getInstance(){
		if(thePickUpFuelCommand == null)
			thePickUpFuelCommand = new PickUpFuelCommand();
		return thePickUpFuelCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target sent
		if(target == null)
			target = tgw;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display the source of request in console
		System.out.println("Pick Up Fuel requested from " + e.getActionCommand()
						+ " " + e.getSource().getClass() );
		
		if(target != null){
			// have the player pick up fuel
			target.playerGetsFuelCan();
		} else{
			System.out.println("Command received to have player get fuel can, but a GameWorld target has been set...");
		}
	}
}