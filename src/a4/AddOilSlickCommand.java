package a4;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Add Oil Slick command
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
public class AddOilSlickCommand extends AbstractAction {
	
	private static AddOilSlickCommand theAddOilSlickCommand;
	
	private static GameWorld target;

	private AddOilSlickCommand(){
		super("Add Oil Slick");
	}
	
	// request the instance of the command
	public static AddOilSlickCommand getInstance(){
		if(theAddOilSlickCommand == null)
			theAddOilSlickCommand = new AddOilSlickCommand();
		return theAddOilSlickCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target given
		if(target == null)
			target = tgw;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display the source of request in console
	//	System.out.println("Add Oil Slick requested from " + e.getActionCommand()
	//					+ " " + e.getSource().getClass() );
		
		if(target != null){
			// actually add the oil slick
			target.addOilSlick();
		} else{
			System.out.println("Command recieved to add oil slick, but a GameWorld target has been set...");
		}
	}
}