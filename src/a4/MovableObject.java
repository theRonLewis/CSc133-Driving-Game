package a4;

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
		rotate(heading);
	}
	
	MovableObject(float x, float y, float s){
		super(x, y);
		heading = (randInt(0,359));
		speed = s;
		rotate(heading);
	}

	// create new movable object with given staring x and y pos,
	// color, heading, and speed
	MovableObject(float x, float y, Color c, int h, float s){
		super(x, y, c);
		heading = h;
		speed = s;
		rotate(h);
	}
	
	// calculate the change in x and y based upon the heading, speed, and elapsed ms
	// and then call the parent move location to update the object's location
	public void move(int ms){
		
		// compute the change in X and Y for 1 sec
		int angle = 90 - heading;
		float deltaX = ( ((float)Math.cos( Math.toRadians(angle) )) * speed);
		float deltaY = ( ((float)Math.sin( Math.toRadians(angle) )) * speed);
		
		// calc for elapsed time (above calc assumes 1 sec)
		deltaX = ( deltaX * ( (float)ms / 1000 ));
		deltaY = ( deltaY * ( (float)ms / 1000 ));
		
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
		// handle if the heading is being set
		// to outside the 0~359 range
		// keep it in the 0~359 range
		if (h >= 0 && h <= 359)
				heading = h;
		else if( h >= 360 )
			heading = (h - 360);
		else if( h < 0 )
			heading = (h + 360);
		
		rotate(heading);
	}
	
	// set the speed for this object
	public void setSpeed(float s){
		speed = s;
	}
}
