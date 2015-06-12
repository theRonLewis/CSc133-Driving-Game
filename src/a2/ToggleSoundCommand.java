package a2;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Toggle Sound command
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
public class ToggleSoundCommand extends AbstractAction {

	private static ToggleSoundCommand theToggleSoundCommand;
	
	private static GameWorld target;
	
	private ToggleSoundCommand(){
		super("Sound");
	}
	
	// request the instance of the command
	public static ToggleSoundCommand getInstance(){
		if(theToggleSoundCommand == null)
			theToggleSoundCommand = new ToggleSoundCommand();
		return theToggleSoundCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target sent
		if(target == null)
			target = tgw;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display source of request in console
		System.out.println("Toggle sound requested from " + e.getActionCommand()
				+ " " + e.getSource().getClass());

		if(target != null){
		// actually toggle the sound
		target.toggleSound();
		} else{
		System.out.println("Command received to toggle sound, but a GameWorld target has been set...");
		}
	}
}
