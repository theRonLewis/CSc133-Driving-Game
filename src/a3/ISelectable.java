package a3;

import java.awt.Graphics;

public interface ISelectable {

	// way to mark an object as selected or not
	public void setSelected(boolean yesNo);
	
	// see if it is selected
	public boolean isSelected();
	
	// is the mouse in it to select on click?
	public boolean contains(FloatPoint p);
	
	// way to draw the object which knows about
	// if it is selected or not
	public void draw(Graphics g);
	
	// way to draw the selection graphic on top
	// of the normally drawn object
	public void drawSelected(Graphics g);
	
}
