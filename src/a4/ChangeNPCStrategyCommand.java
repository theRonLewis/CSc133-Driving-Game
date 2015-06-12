package a4;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Change NPC Strategy command
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
public class ChangeNPCStrategyCommand extends AbstractAction {
	
	private static ChangeNPCStrategyCommand theChangeNPCStrategyCommand;
	
	private static GameWorld target;
	
	private ChangeNPCStrategyCommand(){
		super("Change NPC Strategy");
	}
	
	// request the instance of the command
	public static ChangeNPCStrategyCommand getInstance(){
		if(theChangeNPCStrategyCommand == null)
			theChangeNPCStrategyCommand = new ChangeNPCStrategyCommand();
		return theChangeNPCStrategyCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target sent
		if(target == null)
			target = tgw;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display source of request in console
	//	System.out.println("Change NPC Strategy requested from " + e.getActionCommand()
	//						+ " " + e.getSource().getClass());
		
		if(target != null){
			// actually change the NPC strats
			target.changeNPCStrategies();
		} else{
			System.out.println("Command received to change npc strategies, but a GameWorld target has been set...");
		}
	}
}