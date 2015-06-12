package a2;

import java.util.Random;
import java.awt.Color;

// this class represents an object in the game world
// every object has a location and a color

public abstract class GameObject {
	
	// object needs location and color
	private FloatPoint location;
	private Color color;
	
	// create new object with random position and color
	GameObject(){
		// create new random point and color and save them
		FloatPoint p = new FloatPoint(randInt(20,980), randInt(20,980));
		Color c = new Color(randInt(0,255), randInt(0,255), randInt(0,255));
		
		// don't use setters as they are
		// Overridden down the chain to prevent use
		// and cause pointer errors
		location = p;
		color = c;
	}
	
	// create new object with given point and color
	GameObject(FloatPoint p, Color c){
		location = p;
		color = c;
	}
	
	// every class will need to output a string for printing
	public abstract String toString();
	
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
}
