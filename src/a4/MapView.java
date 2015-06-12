package a4;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

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
	
	private double winLeft, winRight, winTop, winBot;
	private AffineTransform worldToND, ndToScreen, theVTM;
	private IGameWorld myGW;
	
	// must have game world proxy upon creation
	MapView(IGameWorld gwp){
		// create an empty panel for future map output
		setBorder(new EtchedBorder());
		setBackground(Color.gray);
		myGW = gwp;
		winLeft = winBot = 0;
		
		// Game window W: 1000, H: 800
		// results in MapView W: 867, H:713
		winRight = 867;
		winTop = 713;
		
	}
	
	public void update(IObservable o) {
		if(o instanceof IGameWorld){
			// save reference to GameWorldProxy
			myGW = (IGameWorld)o;
			
			this.repaint();
			
			// print to console for now
			//myGW.printTextMap();
		}
	}
	
	public void paintComponent(Graphics g){
		// parent call
		super.paintComponent(g);
		
		// setup the affine transformation stuff
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform saveAT = g2d.getTransform();
		
		// to determine initial screen size...
		//System.out.println("MapView has width: " + this.getWidth() + " and height: " + this.getHeight());
		
		//window stuff
		double winHeight = winTop - winBot;
		double winWidth = winRight - winLeft;
		
		// update the VTM for window
		worldToND = buildWorldToNDXform(winWidth, winHeight, winLeft, winBot);
		ndToScreen = buildNDToScreenXform( this.getWidth(), this.getHeight() );
		theVTM = (AffineTransform)ndToScreen.clone();
		theVTM.concatenate(worldToND);
		g2d.transform(theVTM);
		
		// draw everything
		IIterator anIterator = null;

		// get an iterator for the collection
		anIterator = myGW.getIterator();
		
		// setup a placeholder object
		Object currentObj;
		
		// iterate through, and print each one
		while( anIterator.hasNext() ){
			currentObj = anIterator.next();
			if(currentObj != null){
				// reset the g2d object, and draw next
				g2d.setTransform(theVTM);
				((GameObject)currentObj).draw(g2d);
			}
		}
		setVisible(true);
		
		g2d.setTransform(saveAT);
	}

	// set up the world to ND transform
	public AffineTransform buildWorldToNDXform(double width, double height, double left, double bot){
		AffineTransform theTransform = new AffineTransform();
		theTransform.scale((1/width), (1/height));
		theTransform.translate(-left, -bot);
		return theTransform;
	}
	
	// set up the ND to Screen transform
	public AffineTransform buildNDToScreenXform(double panelWidth, double panelHeight){
		AffineTransform theTransform = new AffineTransform();
		theTransform.translate(0, panelHeight);
		theTransform.scale(panelWidth, -panelHeight);
		return theTransform;
	}
	
	// zooms in 5%
	public void zoomIn(){
		double h = winTop - winBot;
		double w = winRight - winLeft;
		winLeft += w*0.05;
		winRight -= w*0.05;
		winTop -= h*0.05;
		winBot += h*0.05;
		this.repaint();
	}
	
	// zooms out 5%
	public void zoomOut(){
		double h = winTop - winBot;
		double w = winRight - winLeft;
		winLeft -= w*0.05;
		winRight += w*0.05;
		winTop += h*0.05;
		winBot -= h*0.05;
		this.repaint();
	}
	
	public void panLeft(){
		winLeft += 5;
		winRight += 5;
	}
	
	public void panRight(){
		winLeft -= 5;
		winRight -= 5;
	}
	
	public void panUp(){
		winTop -= 5;
		winBot -= 5;
	}
	
	public void panDown(){
		winTop += 5;
		winBot += 5;
	}
	
	public AffineTransform getVTM(){
		return new AffineTransform(theVTM);
	}
	
	public int getWindowWidth(){
		return (int)(winRight - winLeft);
	}
	
	public int getWindowHeight(){
		return (int)(winTop - winBot);
	}
}
