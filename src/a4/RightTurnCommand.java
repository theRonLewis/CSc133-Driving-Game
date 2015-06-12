package a4;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Right Turn command
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
public class RightTurnCommand extends AbstractAction {
	
	private static RightTurnCommand theRightTurnCommand;
	
	private static GameWorld target;
	
	private RightTurnCommand(){
		super("RightTurn");
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target sent
		if(target == null)
			target = tgw;
	}
	
	// request the instance of the command
	public static RightTurnCommand getInstance(){
		if(theRightTurnCommand == null)
			theRightTurnCommand = new RightTurnCommand();
		return theRightTurnCommand;
	}
	
	public void actionPerformed(ActionEvent e){

		// display source of request in console
	//	System.out.println("Right turn requested from " + e.getActionCommand() 
	//						+ " " + e.getSource().getClass() );
		
		if(target != null){
			// actually turn the player right
			target.turnPlayerRight();
		} else{
			System.out.println("Command received to turn the player right, but a GameWorld target has been set...");
		}
	}
}