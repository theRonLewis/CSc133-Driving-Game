package a3;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Tick command
 * 
 * This class can, and actually must be instantiated
 * in order for Game to call it.
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
	private static int tickInterval = 0;
	private static int tickCount = 0;
	
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
	
	public static void setInterval(int ms){
		tickInterval = ms;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display the source of request in console
	//	System.out.println("Tick the clock requested from " + e.getActionCommand()
	//					+ " " + e.getSource().getClass() );
		
		if(target != null && tickInterval > 0){
			
			// handle counting up 10 sec (500 ticks) for changing npc strats
			tickCount++;
			if(tickCount == 500){
				tickCount = 0;
				target.changeNPCStrategies();
			}
			
			// actually tick the game clock & move objects
			target.advanceGameClock(tickInterval);
			
			// tell the game world to check for collisions
			target.detectCollisions();
			
			// update the views
			target.notifyObservers();
			
		} else{
			System.out.println("Command received to advance game clock, but a GameWorld target has been set,");
			System.out.println("or the interval has not been properly set in the TickCommand class...");
		}
	}
}