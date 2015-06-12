package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Enter Oil Slick command
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
public class PlayerEntersOilSlickCommand extends AbstractAction {
	
	private static PlayerEntersOilSlickCommand thePlayerEntersOilSlickCommand;

	private static GameWorld target;
	
	private PlayerEntersOilSlickCommand(){
		super("Enter Oil Slick");
	}
	
	// request the instance of the command
	public static PlayerEntersOilSlickCommand getInstance(){
		if(thePlayerEntersOilSlickCommand == null)
			thePlayerEntersOilSlickCommand = new PlayerEntersOilSlickCommand();
		return thePlayerEntersOilSlickCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target sent
		if(target == null)
			target = tgw;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display the source of request in console
	//	System.out.println("Enter Oil Slick requested from " + e.getActionCommand()
	//					+ " " + e.getSource().getClass() );
		
		if(target != null){
			// actually enter the oil slick
			target.playerEntersOil();
		} else{
			System.out.println("Command received to puut player in oil, but a GameWorld target has been set...");
		}
	}
	
}
