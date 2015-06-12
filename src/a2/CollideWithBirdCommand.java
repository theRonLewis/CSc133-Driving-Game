package a2;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Collide With Bird command
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
public class CollideWithBirdCommand extends AbstractAction {
	
	private static CollideWithBirdCommand theCollideWithBirdCommand;
	
	private static GameWorld target;

	private CollideWithBirdCommand(){
		super("Collide with Bird");
	}
	
	// request the instance of the command
	public static CollideWithBirdCommand getInstance(){
		if(theCollideWithBirdCommand == null)
			theCollideWithBirdCommand = new CollideWithBirdCommand();
		return theCollideWithBirdCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target sent
		if(target == null)
			target = tgw;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display the source of request in console
		System.out.println("Collide with Bird requested from " + e.getActionCommand()
						+ " " + e.getSource().getClass() );
		
		if(target != null){
			// actually collide the player with a bird
			target.playerBirdCollision();
		} else{
			System.out.println("Command recieved to collide with bird, but a GameWorld target has been set...");
		}	
	}
}