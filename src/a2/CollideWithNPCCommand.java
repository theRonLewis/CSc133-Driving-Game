package a2;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Collide With NPC command
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
public class CollideWithNPCCommand extends AbstractAction {
	
	private static CollideWithNPCCommand theCollideWithNPCCommand;
	
	private static GameWorld target;

	private CollideWithNPCCommand(){
		super("Collide with NPC");
	}
	
	// request the instance of the command
	public static CollideWithNPCCommand getInstance(){
		if(theCollideWithNPCCommand == null)
			theCollideWithNPCCommand = new CollideWithNPCCommand();
		return theCollideWithNPCCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target sent
		if(target == null)
			target = tgw;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display the source of request in console
		System.out.println("Collide with NPC requested from " + e.getActionCommand()
						+ " " + e.getSource().getClass() );
		
		if(target != null){
			// actually collide player with (the first) npc
			target.playerCarCollision();
		} else{
			System.out.println("Command received to collide player with NPC, but a GameWorld target has been set...");
		}
	}
}
