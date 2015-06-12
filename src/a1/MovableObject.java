package a1;

import java.awt.Color;

// this class represents a movable game object
// in a game world. It inherits GameObject
public abstract class MovableObject extends GameObject {
	
	// movable objects need to have a heading (direction) and speed
	private int heading;
	private float speed;
	
	// create new movable object with random heading and speed
	// will invoke parent constructors for random color and position
	MovableObject(){
		super();
		heading = (randInt(0,359));
		speed = (randInt(5,15));
	}
	
	// create new movable object with given staring location, color
	// heading and speed
	MovableObject(FloatPoint p, Color c, int h, float s){
		super(p, c);
		heading = h;
		speed = s;
	}
	
	// calculate the change in x and y based upon the heading and speed,
	// and then call the parent move location to update the object's location
	public void move(){
		// compute the change in X and Y
		int angle = 90 - heading;
		float deltaX = ( ((float)Math.cos( Math.toRadians(angle) )) * speed);
		float deltaY = ( ((float)Math.sin( Math.toRadians(angle) )) * speed);
		
		// call parent method to move location given changes
		super.move(deltaX, deltaY); 
	}
	
	// get this object's heading
	public int getHeading(){
		return heading;
	}
	
	// get this object's speed 
	public float getSpeed(){
		return speed;
	}
	
	// set the heading for this object
	public void setHeading(int h){
		heading = h;
	}
	
	// set the speed for this object
	public void setSpeed(float s){
		speed = s;
	}
}
