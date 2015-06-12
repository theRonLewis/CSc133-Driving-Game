package a4;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Brake command
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
public class BrakeCommand extends AbstractAction {
	
	private static BrakeCommand theBrakeCommand;
	
	private static GameWorld target;
	
	private BrakeCommand(){
		super("Brake");
	}
	
	// request the instance of the command
	public static BrakeCommand getInstance(){
		if(theBrakeCommand == null)
			theBrakeCommand = new BrakeCommand();
		return theBrakeCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target sent
		if(target == null)
			target = tgw;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display source of request in console
	//	System.out.println("Brake requested from " + e.getActionCommand()
	//						+ " " + e.getSource().getClass() );
		
		if(target != null){
			// actually brake the player
			target.brakePlayer();
		} else{
			System.out.println("Command recieved to brake player, but a GameWorld target has been set...");
		}
	}
}