package a3;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;

/*
 * COMMAND CLASS
 * 
 * This class represents the Delete command
 * 
 * 
 * This support is limited in this class ONLY
 * In GameWorld, deletion of all objects is supported
 * BUT this Command prevents that - there will likely be issues
 * if there is no player car specifically, but we do not want
 * to delete other objects either.
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
public class DeleteCommand extends AbstractAction {
	
	private static DeleteCommand theDeleteCommand;

	private static GameWorld target;
	
	private DeleteCommand(){
		super("Delete");
	}
	
	// request the instance of the command
	public static DeleteCommand getInstance(){
		if(theDeleteCommand == null)
			theDeleteCommand = new DeleteCommand();
		return theDeleteCommand;
	}
	
	public static void setTarget(GameWorld tgw){
		// only accept and set the first ever target sent
		if(target == null)
			target = tgw;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void actionPerformed(ActionEvent e){


		// display the source of request in console
	//	System.out.println("Delete requested from " + e.getActionCommand()
	//					+ " " + e.getSource().getClass() );
		
		// keep a list of items to delete
		Vector toDelete = new Vector();
		int i; // for counting later on
		
		// target needs to be set
		if(target != null){
			// create iterator to go through and delete selected pylons and fuel cans
			IIterator anIterator = target.getObjects().getIterator();
			
			Object currentObj = new Object();
			
			while( anIterator.hasNext() ){
				currentObj = anIterator.next();
				
				// if the object is selected...
				if( ((GameObject)currentObj).isSelected() ){
					// add to list of items to delete
					toDelete.add(currentObj);
				}
			}
			
			// turn vector into an array
			Object toBeDeleted[] = toDelete.toArray();
			
			// delete all the items in the toDelete list, if there are any
			if( toDelete.size() > 0 ){
				for( i=0; i< toDelete.size(); i++){
					target.remove( (GameObject)(toBeDeleted[i]) );
				}
			}
			
			// need to repaint the map
			target.notifyObservers();
			
		} else{ // target not set
			System.out.println("Command received to delete selected items, but a GameWorld target has been set...");
		}
	}
}