package a4;

import java.awt.Graphics2D;

public interface ISelectable {

	// way to mark an object as selected or not
	public void setSelected(boolean yesNo);
	
	// see if it is selected
	public boolean isSelected();

/*	
	// is the mouse in it to select on click?
	public boolean contains(FloatPoint p);	
*/
	
	// is the mouse in it to select on click?
	public boolean contains(float x, float y);
	
	// way to draw the object which knows about
	// if it is selected or not
	public void draw(Graphics2D g2d);
	
	// way to draw the selection graphic on top
	// of the normally drawn object
	public void drawSelected(Graphics2D g2d);
	
}
