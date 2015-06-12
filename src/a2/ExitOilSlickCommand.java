package a2;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Exit Oil Slick command
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
public class ExitOilSlickCommand extends AbstractAction {
	
	private static ExitOilSlickCommand theExitOilSlickCommand;
	
	private static GameWorld target;

	private ExitOilSlickCommand(){
		super("Exit Oil Slick");
	}
	
	// request the instance of the command
	public static ExitOilSlickCommand getInstance(){
		if(theExitOilSlickCommand == null)
			theExitOilSlickCommand = new ExitOilSlickCommand();
		return theExitOilSlickCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target sent
		if(target == null)
			target = tgw;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display the source of request in console
		System.out.println("Exit Oil Slick requested from " + e.getActionCommand()
						+ " " + e.getSource().getClass() );
		
		if(target != null){
			// actually remove player from oil slick
			target.playerExitsOil();
		} else{
			System.out.println("Command received to remove player from oil, but a GameWorld target has been set...");
		}
	}
}