package a2;

// this class is used to have float values represent
// the location of an object on the map
public class FloatPoint{
	
	// x and y values for 2d location
	private float x;
	private float y;
	
	// construct a point
	FloatPoint(float xVal, float yVal){
		x = xVal;
		y = yVal;
	}
	
	// copy constructor
	FloatPoint(FloatPoint fp){
		x = fp.getX();
		y = fp.getY();
	}
	
	// get the x value
	public float getX(){
		return x;
	}
	
	// get the y value
	public float getY(){
		return y;
	}
	
	// set the x value
	private void setX(float newX){
		x = newX;
	}
	
	// set the y value
	private void setY(float newY){
		y = newY;
	}
	
	// set both x and y
	public void setLocation(float newX, float newY){
		setX(newX);
		setY(newY);
	}	
}