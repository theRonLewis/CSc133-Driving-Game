package a3;

import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;

// this class represents an object in the game world
// every object has a location and a color

public abstract class GameObject implements IDrawable, ISelectable, ICollider {
	
	// object needs location and color
	private FloatPoint location;
	private Color color;
	private boolean selected;
	
	// create new object with random position and color
	GameObject(){
		// create new random point and color and save them
		FloatPoint p = new FloatPoint(randInt(20,845), randInt(20,695));
		Color c = new Color(randInt(0,255), randInt(0,255), randInt(0,255));
		
		// don't use setters as they are
		// Overridden down the chain to prevent use
		// and may cause pointer errors
		location = p;
		color = c;
		
		// be default, objects do not start selected
		selected = false;
	}
	
	GameObject(FloatPoint p){
		location = p;
		setRandomColor();
		selected = false;
	}
	
	// create new object with given point and color
	GameObject(FloatPoint p, Color c){
		location = p;
		color = c;
		selected = false;
	}
	
	// mark it selected or not
	public void setSelected(boolean yesNo){
		selected = yesNo;
	}
	
	// see if it is selected
	public boolean isSelected(){
		return selected;
	}
	
	// every class will need to output a string for printing
	public abstract String toString();
	
	// every class will need to draw itself
	public abstract void draw(Graphics g);
	
	// every class will need to be able to make it look selected
	public abstract void drawSelected(Graphics g);
	
	// every class will need to see if it has a point in it
	public abstract boolean contains(FloatPoint p);
	
	// get the current location of the object
	public FloatPoint getLocation(){
		return location;
	}
	
	// set new location with given point
	public void setLocation(FloatPoint newLoc){
		location = newLoc;
	}
	
	// move the object the given amount in the x and t directions
	public void move(float deltaX, float deltaY){
		
		// compute new values based on delta values
		float newX = location.getX() + deltaX;
		float newY = location.getY() + deltaY;
		
		// save the new x and y
		location.setLocation( newX, newY );
	}
	
	// get the color of the object
	public Color getColor(){
		return color;
	}
	
	// get the contrast color, probably for text on object
	public Color getContrastColor(){
		Color contrast;
		contrast = new Color(255-getColor().getRed(),
							 255-getColor().getGreen(),
							 255-getColor().getBlue() );
		return contrast;
	}
	
	// set the color of the object
	// overridden later for objects that cannot change color
	public void setColor(Color newColor){
		color = newColor;
	}
	
	// create new random color for the object
	// overridden later for objects that cannot change color
	public void setRandomColor(){
		Color c = new Color(randInt(0,255), randInt(0,255), randInt(0,255));
		setColor(c);
	}
	
	// random integer method for initializations
	public int randInt(int min, int max){
		int theNum;
		Random numGen = new Random();
		theNum = numGen.nextInt(max - min + 1);
		theNum += min;
		return theNum;
	}
	
	@Override
	public boolean collidesWith(ICollider otherObj) {
		if( getRight() <= otherObj.getLeft() || getLeft() >= otherObj.getRight() )
			// no left/right overlap, return false
			return false;
		
		// there was left/right overlap, now check top/bottom
		if( otherObj.getTop() <= getBottom() || getTop() <= otherObj.getBottom() )
			// no top/bottom overlap, return false
			return false;
		
		// there was overlap in both L/R and T/B, they are colliding
		return true;
	}
}