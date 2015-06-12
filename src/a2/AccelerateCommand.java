package a2;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Accelerate command
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
public class AccelerateCommand extends AbstractAction{

	private static AccelerateCommand theAccelerateCommand;
	
	private static GameWorld target;
	
	private AccelerateCommand(){
		super("Accelerate");
	}
	
	// request the instance of the command
	public static AccelerateCommand getInstance(){
		if(theAccelerateCommand == null)
			theAccelerateCommand = new AccelerateCommand();
		return theAccelerateCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target sent
		if(target == null)
			target = tgw;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display source of request in console
		System.out.println("Accelerate player requested from " + e.getActionCommand()
							+ " " + e.getSource().getClass());
		
		if(target != null){
			// actually accelerate the player
			target.acceleratePlayer();
		} else{
			System.out.println("Command received to accelerate player, but a GameWorld target has been set...");
		}
	}	
}