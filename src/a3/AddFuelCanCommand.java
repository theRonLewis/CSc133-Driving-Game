package a3;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the add Fuel Can command
 * 
 * It can be instantiated, and but may also
 * be used via the static method getInstance()
 * if assigning to a GUI button or keybinding.
 * 
 * In order to be used properly, you must also use
 * the setTarget(GameWorld) method to assign the 
 * command to a game world.
 * 
 */

@SuppressWarnings("serial")
public class AddFuelCanCommand extends AbstractAction {
	
	private static AddFuelCanCommand theAddFuelCanCommand;
	
	private static GameWorld target;

	private AddFuelCanCommand(){
		super("Add Fuel Can");
	}
	
	// request the instance of the command
	public static AddFuelCanCommand getInstance(){
		if(theAddFuelCanCommand == null)
			theAddFuelCanCommand = new AddFuelCanCommand();
		return theAddFuelCanCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target given
		if(target == null)
			target = tgw;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display the source of request in console
	//	System.out.println("Add Fuel Canrequested from " + e.getActionCommand()
	//					+ " " + e.getSource().getClass() );
		
		if(target != null){
			// set flag in world to add a fuel can when map is clicked
			target.setNewFuelCanFlag(true);
			target.setNewPylonFlag(false);
		} else{
			System.out.println("Command recieved to add fuel can, but a GameWorld target has been set...");
		}
	}
}