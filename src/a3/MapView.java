package a3;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.*;

/*
 * VIEW CLASS
 * 
 * This class represents the Map View of the game
 * 
 * It is meant to be implemented as a panel in
 * a frame, and should be given to an observable
 * GameWorld.
 * 
 * It currently implements a blank panel, and still
 * only writes the "map" to the console screen.
 * 
 */
@SuppressWarnings("serial")
public class MapView extends JPanel implements IObserver {
	
	private IGameWorld myGW;
	
	// must have game world proxy upon creation
	MapView(IGameWorld gwp){
		// create an empty panel for future map output
		setBorder(new EtchedBorder());
		setBackground(Color.gray);
		myGW = gwp;
	}
	
	public void update(IObservable o) {
		if(o instanceof IGameWorld){
			// save reference to GameWorldProxy
			myGW = (IGameWorld)o;
			
			repaint();
			
			// continue to print to console for now
			//myGW.printTextMap();
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		// draw everything
		IIterator anIterator = null;

		// get an iterator for the collection
		anIterator = myGW.getIterator();
		
		// setup a placeholder object
		Object currentObj;
		
		// iterate through, and print each one
		while( anIterator.hasNext() ){
			currentObj = anIterator.next();
			if(currentObj != null)
				((GameObject)currentObj).draw(g);
		}
		setVisible(true);
	}
}
