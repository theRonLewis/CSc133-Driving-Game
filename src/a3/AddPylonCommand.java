package a3;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Add Pylon command
 * 
 * This implements the singleton design pattern, and
 * if you want to use it without the need to enable or
 * disable the command does not need to be instantiated.
 * 
 * In order to be used properly, you must also use
 * the setTarget(GameWorld) method to assign the 
 * command to a game world.
 * 
 */

@SuppressWarnings("serial")
public class AddPylonCommand extends AbstractAction {
	
	private static AddPylonCommand theAddPylonCommand;
	
	private static GameWorld target;

	private AddPylonCommand(){
		super("Add Pylon");
	}
	
	// request the instance of the command
	public static AddPylonCommand getInstance(){
		if(theAddPylonCommand == null)
			theAddPylonCommand = new AddPylonCommand();
		return theAddPylonCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target given
		if(target == null)
			target = tgw;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display the source of request in console
	//	System.out.println("Add Pylon requested from " + e.getActionCommand()
	//					+ " " + e.getSource().getClass() );
		
		if(target != null){
			// set flag in world to add a pylon when map is clicked
			target.setNewPylonFlag(true);
			target.setNewFuelCanFlag(false);
			
		} else{
			System.out.println("Command recieved to add pylon, but a GameWorld target has been set...");
		}
	}
}