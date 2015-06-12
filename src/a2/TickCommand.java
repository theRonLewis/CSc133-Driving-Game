package a2;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Tick command
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
public class TickCommand extends AbstractAction {
	
	private static TickCommand theTickCommand;

	private static GameWorld target;
	
	private TickCommand(){
		super("Tick");
	}
	
	// request the instance of the command
	public static TickCommand getInstance(){
		if(theTickCommand == null)
			theTickCommand = new TickCommand();
		return theTickCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target sent
		if(target == null)
			target = tgw;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display the source of request in console
		System.out.println("Tick the clock requested from " + e.getActionCommand()
						+ " " + e.getSource().getClass() );
		
		if(target != null){
			// actually tick the game clock
			target.advanceGameClock();
		} else{
			System.out.println("Command received to advance game clock, but a GameWorld target has been set...");
		}
	}
}