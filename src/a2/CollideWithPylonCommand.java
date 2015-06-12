package a2;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/*
 * COMMAND CLASS
 * 
 * This class represents the Collide With Pylon command
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
public class CollideWithPylonCommand extends AbstractAction {
	
	private static CollideWithPylonCommand theCollideWithPylonCommand;
	
	private static GameWorld target;

	private CollideWithPylonCommand(){
		super("Collide with Pylon");
	}
	
	// request the instance of the command
	public static CollideWithPylonCommand getInstance(){
		if(theCollideWithPylonCommand == null)
			theCollideWithPylonCommand = new CollideWithPylonCommand();
		return theCollideWithPylonCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target sent
		if(target == null)
			target = tgw;
	}
	
	public void actionPerformed(ActionEvent e){
		
		// display the source of request in console
//		System.out.println("Collide with Pylon requested from " + e.getActionCommand()
//						+ " " + e.getSource().getClass() );
		
		// declare the input string and pylonNum for the prompt
		String inputString;
		int pylonNum;
		
		try{
			
			inputString = JOptionPane.showInputDialog("Please input a pylon number");
			pylonNum = Integer.parseInt(inputString);
			
		}catch(NumberFormatException nfe){
			// set input to 0 for use later
			pylonNum = 0;
			
			// print to console
			System.out.println("The user entered a non numeric for pylon number... Stack Trade for exception: ");
			nfe.printStackTrace();
			
			// show pane with error
			JOptionPane.showMessageDialog(null, "You must enter a number...");
		}
		
		// print pylon number received to console for testing
//		System.out.println("Got the pylon number: " + pylonNum);
		
		if(target != null){
			// actually try and hit the pylon
			target.playerHitPylon(pylonNum);
		} else{
			System.out.println("Command received to collide with pylon, but a GameWorld target has not been set...");
		}		
	}
}