package a3;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Left Turn command
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
public class LeftTurnCommand extends AbstractAction {
	
	private static LeftTurnCommand theLeftTurnCommand;
	
	private static GameWorld target;
	
	private LeftTurnCommand(){
		super("LeftTurn");
	}
	
	// request the instance of the command
	public static LeftTurnCommand getInstance(){
		if(theLeftTurnCommand == null)
			theLeftTurnCommand = new LeftTurnCommand();
		return theLeftTurnCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target sent
		if(target == null)
			target = tgw;
	}
	
	public void actionPerformed(ActionEvent e){

		// display source of request in console
	//	System.out.println("Left turn requested from " + e.getActionCommand() + " " + e.getSource().getClass() );
	
		if(target != null){
			// actually turn the player left
			target.turnPlayerLeft();
		} else{
			System.out.println("Command received to turn player left, but a GameWorld target has been set...");
		}
	}
}