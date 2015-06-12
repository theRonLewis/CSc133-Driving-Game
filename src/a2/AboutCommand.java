package a2;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/*
 * COMMAND CLASS
 * 
 * This class represents the About command
 * 
 * It cannot be instantiated, and must always 
 * be used via the static method getInstance().
 * 
 * This implements the singleton design pattern.
 * 
 */

@SuppressWarnings("serial")
public class AboutCommand extends AbstractAction {
	
	private static AboutCommand theAboutCommand;
	
	private AboutCommand(){
		super("About");
	}
	
	// request the instance of the command
	public static AboutCommand getInstance(){
		if(theAboutCommand == null)
				theAboutCommand = new AboutCommand();
		return theAboutCommand;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display source of request in console
		System.out.println("About pane requested from " + e.getActionCommand() 
							+ " " + e.getSource().getClass() );
		
		// display a pane with information about the application
		JOptionPane.showMessageDialog(null, "Programmer: Ron Lewis\n"
								+ "Class: CSC 133 - Spring 2015\n"
								+ "Version: Assignment 2", "About this program...",
								JOptionPane.PLAIN_MESSAGE);
		
	}

}
