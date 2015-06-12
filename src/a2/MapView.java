package a2;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTextArea;
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

	private JTextArea textArea = new JTextArea("Test text for possible \"map\" output");
	
	MapView(){
		// create an empty panel for future map output
		setBorder(new EtchedBorder());
		setBackground(Color.gray);
	}
	
	public void update(IObservable o) {
		add(textArea);
		if(o instanceof GameWorld)
			((GameWorld)o).printTextMap();
		
	}
}
