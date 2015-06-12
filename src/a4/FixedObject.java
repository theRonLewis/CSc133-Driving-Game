package a4;

import java.awt.Color;

// this class represents a fixed game object
// in a game world. It inherits GameObject
public abstract class FixedObject extends GameObject {
	
	// create object with random position and color
	FixedObject(){
		super();
	}
/*	
	FixedObject(FloatPoint p){
		super(p);
	}
*/	
	FixedObject(float x, float y){
		super(x, y);
	}
/*	
	// create object with given position and color
	FixedObject(FloatPoint p, Color c){
		super(p, c);
	}
*/	
	// create object with given position and color
	FixedObject(float x, float y, Color c){
		super(x, y, c);
	}
	
	// override the move method since
	// fixed objects cannot move
	public void move(int ms){
		// Override the move method
		// fixed objects cannot move
	}
}
