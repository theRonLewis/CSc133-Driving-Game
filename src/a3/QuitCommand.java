package a3;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/*
 * COMMAND CLASS
 * 
 * This class represents the Quit command
 * 
 * It can be instantiated, and but may also
 * be used via the static method getInstance()
 * if assigning to a GUI button or keybinding.
 * 
 * This implements the singleton design pattern.
 * 
 */

@SuppressWarnings("serial")
public class QuitCommand extends AbstractAction {

	private static QuitCommand theQuitCommand;
	
	private QuitCommand(){
		super("Quit");
	}
	
	// request the instance of the command
	public static QuitCommand getInstance(){
		if(theQuitCommand == null)
			theQuitCommand = new QuitCommand();
		return theQuitCommand;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		// display source of request in console
	//	System.out.println("Quit requested from " + e.getActionCommand() 
	//						+ " " + e.getSource().getClass() + "\nPrompting user to confirm quit...");
		
		// verify that user actually wants to close w/Yes No dialogue
		int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?",
				"Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		// check for yes
		if(result == JOptionPane.YES_OPTION) {
			System.out.println("User chose yes, closing application...");
			System.exit(0);
		}
		
		// didn't pick yes, go back to application
		System.out.println("User chose no, returning to application...");
		return;
	}

}
