package a3;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Play/Pause command
 * 
 * It can be instantiated, and but may also
 * be used via the static method getInstance()
 * if assigning to a GUI button or keybinding.
 * 
 * This implements the singleton design pattern.
 * 
 * In order to be used properly, you must also use
 * the setTarget(Game) method to assign the 
 * command to a game.
 * 
 */

@SuppressWarnings("serial")
public class PlayPauseCommand extends AbstractAction {
	
	private static PlayPauseCommand thePlayPauseCommand;

	private static Game target;
	
	private PlayPauseCommand(){
		super("PlayPause");
	}
	
	// request the instance of the command
	public static PlayPauseCommand getInstance(){
		if(thePlayPauseCommand == null)
			thePlayPauseCommand = new PlayPauseCommand();
		return thePlayPauseCommand;
	}
	
	public static void setTarget(Game tg){
		// only accept and set the first ever target sent
		if(target == null)
			target = tg;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display the source of request in console
	//	System.out.println("Play or Pause requested from " + e.getActionCommand()
	//					+ " " + e.getSource().getClass() );
		
		if(target != null){
			if(target.isPaused() )
				target.resumeGame();
			else if( target.isRunning() )
				target.pauseGame();
			
		} else{
			System.out.println("Command received to pause the game, but a Game target has been set...");
		}
	}
}