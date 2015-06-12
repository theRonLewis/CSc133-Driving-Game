package a2;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the New Colors command
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
public class NewColorsCommand extends AbstractAction {
	
	private static NewColorsCommand theNewColorsCommand;

	private static GameWorld target;
	
	private NewColorsCommand(){
		super("New Colors");
	}
	
	// request the instance of the command
	public static NewColorsCommand getInstance(){
		if(theNewColorsCommand == null)
			theNewColorsCommand = new NewColorsCommand();
		return theNewColorsCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target sent
		if(target == null)
			target = tgw;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display the source of request in console
		System.out.println("New Colors requested from " + e.getActionCommand()
						+ " " + e.getSource().getClass() );
		
		if(target != null){
			// actually change colors of the objects
			target.changeObjColors();
		} else{
			System.out.println("Command received to turn the player right, but a GameWorld target has been set...");
		}
	}
}