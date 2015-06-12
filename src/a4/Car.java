package a4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

// this class represents a movable Car object
// it inherits the hierarchy GameObject -> MovableObject
// it also implements the programmer defined interface ISteerable

public abstract class Car extends MovableObject  {
	
	// progress, highest pylon reached
	private int highestPylonReached;
	
	// car needs width and length for area location representation
	private float width;
	private float length;
	
	// car needs to sometimes not have traction
	// this is used for accelerating, braking, and
	// turning, and preventing that from happening
	// in OilSlick objects
	private boolean hasTraction;
	
	// car needs to have a max speed, defined at creation
	// for now its always 50
	private float maxSpeed;
	
	// car has some amount of fuel
	private float fuelLevel;
	
	// car has a damage meter
	private int damageLevel;
	
	// the car is OK
	private boolean carIsOkay;

	// car has a max damage, when reached the player loses a life
	private int maxDamage;
	
	// should be "PLAYER" or "NPC" at creation
//	private String type;
	
	// car parts for drawing
	protected Body myBody;
	protected Axle frontAxle, rearAxle;
	
/*	
	// construct a car with given starting location, color,
	// as well as given heading, and speed
	public Car(FloatPoint p, Color c, int h, float s){
		super(p, c, h, s);
		highestPylonReached=1;
		width=15;
		length=25;
		maxSpeed = 50;
		damageLevel = 0;
		hasTraction = true;
		carIsOkay = true;
	}
*/
	
	// construct a car with given starting location, color,
	// as well as given heading, speed, and type (for drawing)
	public Car(float x, float y, Color c, int h, float s, String t){
		super(x, y, c, h, s);
		highestPylonReached=1;
		width=20;
		length=24;
		maxSpeed = 50;
		damageLevel = 0;
		hasTraction = true;
		carIsOkay = true;
	//	type = t;
		myBody = new Body();
		
		frontAxle = new Axle(12, 8);
		rearAxle = new Axle(18, -8);
	}
	
	public boolean hitPylon(int p){	
		
		// car cannot hit pylon outside the realm of total pylons
		if( p > Pylon.getCount() )
			return false;
		
		// car can only activate this pylon if it is
		// the one immediately after the last one they hit
		if( p == (highestPylonReached + 1) ){
			// update the player's pylon progress
			highestPylonReached = p;
			return true;
			}
		
		// player didn't hit the next valid pylon
		return false;
	}
	
	public int getHighestPylon(){
		return highestPylonReached;
	}
	
	// get the width of the car
	public float getWidth(){
		return width;
	}
	
	// get the length of the car
	public float getLength(){
		return length;
	}
	
	// get the max speed for this car
	// right now is always 50, but may differ from
	// car to car in later versions
	public float getMaxSpeed(){
		return maxSpeed;
	}
	
	// get max damage of this car, used for checking
	// if player is still alive
	public int getMaxDamage(){
		return maxDamage;
	}
	
	protected void setMaxDamage(int dmg){
		maxDamage = dmg;
	}
	
	// return the car's current fuel level
	public float getFuelLevel(){
		return fuelLevel;
	}
	
	protected void setFuelLevel(int f){
		fuelLevel = f;
	}
	
	// add more fuel to the car
	public void addFuel(int moreFuel){
		fuelLevel += moreFuel;
	}
	
	public boolean isOkay(){
		return carIsOkay;
	}
	
	// get the current damage level of the car, used
	// for checking if player is still alive
	public int getDamageLevel(){
		return damageLevel;
	}
	
	// add damage to the car from a collision
	public void addDamage(int damage){
		damageLevel += damage;
		
		// adjust speed for dmg taken
		checkSpeed();
		
		// check to see if car is still ok
		if(damageLevel >= maxDamage)
			carIsOkay = false;
	}
	
	// car took damage, or lost fuel,
	// need to check and maybe adjust the speed
	private void checkSpeed(){
		// if car is already near max speed...
		// need to adjust speed because
		// damage was taken and will reduce max speed
		if( getSpeed() > ( maxSpeed * ( ((float)maxDamage - (float)damageLevel) / (float)maxDamage))){
			// speed was over new max, adjust
			setSpeed( (maxSpeed * ( ((float)maxDamage - (float)damageLevel) / (float)maxDamage) ) );
		}
		
		// if the car has no fuel, set the speed to 0
		if( getFuelLevel() == 0)
			setSpeed(0);
	}

/*	
	public void reset(FloatPoint p){
		setLocation(p); 		// put car at this point (probably a pylon)
		setSpeed(0);			// put car speed to 0
		setHeading(0);			// face car north
		setFuelLevel(20);		// reset the fuel
		damageLevel = 0;		// reset the damage
		hasTraction = true;		// car should have traction to start
		carIsOkay = true;		// car is OK again (was not ok to trigger death and reset)
	}
*/	
	public void reset(float x, float y){
		setLocation(x, y); 		// put car at this point (probably a pylon)
		setSpeed(0);			// put car speed to 0
		setHeading(0);			// face car north
		setFuelLevel(20);		// reset the fuel
		damageLevel = 0;		// reset the damage
		hasTraction = true;		// car should have traction to start
		carIsOkay = true;		// car is OK again (was not ok to trigger death and reset)
	}
	
	// car entered an oil slick and no longer has
	// traction for turning and accelerating or braking
	public void enterOil(){
		hasTraction = false;
	}
	
	// car exited an oil slick and now has traction
	// again and can turn, accelerate, and brake properly
	public void exitOil(){
		hasTraction = true;
	}
	
	public boolean hasTraction(){
		return hasTraction;
	}
	
	// override move function to check for traction
	// and adjust heading if needed
	// also accounts for NPCs currently
	public void move(int ms){
		
		// time has passed, car uses fuel
		// even if not actually moving
		useFuel(ms);
		
		// call the parent move() to perform
		// remaining operations and actually
		// move the car
		super.move(ms);
		
		// check the fuel level in case speed needs adjusting
		checkSpeed();
	}
	
	// car uses some fuel
	private void useFuel(int ms){
		// if the car has some fuel, use some
		if (getFuelLevel() > 0)
			fuelLevel -= ( (float)ms / 1000 );
		
		// if the car is out of fuel, make sure level isn't below 0
		if ( getFuelLevel() < 0)
			fuelLevel = 0;
		
		// car is out of fuel, no longer OK to drive
		if( getFuelLevel() == 0)
			carIsOkay = false;
	}
	
	// attempt to accelerate the car if
	// it currently has traction and also
	// check speed vs max speed
	public void accelerate(){
		// car can only accelerate if
		// it currently has traction
		if( hasTraction ){
			// if car is already at max speed...
			// it also cannot accelerate
			// check for damage factor as well
			if( getSpeed() < ( maxSpeed * ( (maxDamage - damageLevel) / maxDamage) )){
				setSpeed( getSpeed() + 3 );
				//check if you went over max and adjust
				checkSpeed();
			}
		}
	}
	
	// attempt to apply brakes to the car (if
	// it currently has traction) and also
	// prevent speed from dropping below 0
	public void brake(){
		// car can only brake if
		// it currently has traction
		if( hasTraction ){
			// cannot go in reverse... (yet?)
			if( getSpeed() > 0 ){
				setSpeed( getSpeed() - 3);
				// check for going under
				if( getSpeed() < 0)
					setSpeed(0);
			}
		}
	}
	
	protected Axle getFrontAxle(){
		return frontAxle;
	}
	
	public boolean contains(float x, float y){
	
	/*	UNCOMMENT THIS TO ENABLE SELECTION OF PLAYER CARS AND NPC CARS
		// get my x and y bounds
		float x1, x2, y1, y2;
		x1 = getLocation().getX()-(width/2);
		x2 = getLocation().getX()+(width/2);
		y1 = getLocation().getY()-(length/2);
		y2 = getLocation().getY()+(length/2);
		
		if( p.getX() >= x1 && p.getX() <= x2 )
			if( p.getY() >= y1 && p.getY() <= y2 ) 
				return true;
	*/
		return false;
	}
	
	@Override
	public int getLeft() {
		return (int)(getX()-(getWidth()/2));
	}

	@Override
	public int getRight() {
		return (int)(getX()+(getWidth()/2));
	}

	@Override
	public int getTop() {
		return (int)(getY()+(getLength()/2));
	}

	@Override
	public int getBottom() {
		return (int)(getY()-(getLength()/2));
	}
	
	// body class for drawing
	protected class Body implements IDrawable {
		
		// type 0 = player, type 1 = npc
		// for drawing...
		
		private final int [] xBodyPoints = { -5, -5, -2,  2, 5,   5 };
		private final int [] yBodyPoints = { -12, 0, 12, 12, 0, -12 };
		
		Body(){
		}

		@Override
		public void draw(Graphics2D g2d) {
			// set the color
			g2d.setColor(getColor());
			g2d.fillPolygon(xBodyPoints, yBodyPoints, 6);	
		}
	}
	
	// Axle for drawing
	protected class Axle implements IDrawable {
		
		private Shaft myShaft;
		private Tire leftTire, rightTire;
		private AffineTransform axleTranslation;
		
		Axle(int w, int yDis){
			myShaft = new Shaft(w);
			leftTire = new Tire(0-(w/2)-1);
			rightTire = new Tire((w/2)+1);
			axleTranslation = new AffineTransform();
			axleTranslation.translate(0, yDis);
		}
		
		public void setWheelRotation(int degrees){
			leftTire.rotateTo(degrees);
			rightTire.rotateTo(degrees);
		}

		@Override
		public void draw(Graphics2D g2d) {
			
			// save the AT for restoration later
			AffineTransform saveAT = g2d.getTransform();
			
			g2d.transform(axleTranslation);
			
			// draw parts
			myShaft.draw(g2d);
			leftTire.draw(g2d);
			rightTire.draw(g2d);
			
			// restore the g2d object for the next object
			g2d.setTransform(saveAT);		
		}
		
		protected class Shaft implements IDrawable {

			private int shaftWidth;
			
			Shaft(int w){
				shaftWidth = w;
			}
			
			@Override
			public void draw(Graphics2D g2d) {
				g2d.setColor(Color.DARK_GRAY);
				g2d.fillRect(-(shaftWidth/2), -2, shaftWidth, 4);
			}
		}
		
		protected class Tire implements IDrawable {

			AffineTransform tireRotation, tireTranslation;
			
			Tire(int xDis){
				tireRotation = new AffineTransform();
				tireTranslation = new AffineTransform();
				tireTranslation.translate(xDis, 0);
			}
			
			public void rotateTo(int degrees){
				tireRotation.setToRotation(Math.toRadians(360-(degrees*1.5)));
			}
			
			@Override
			public void draw(Graphics2D g2d) {
				// save the transform for future restoration
				AffineTransform saveAT = g2d.getTransform();
				
				g2d.transform(tireTranslation);
				g2d.transform(tireRotation);
				
				// set color and draw tire
				g2d.setColor(Color.BLACK);
				g2d.fillRoundRect(-2, -3, 4, 6, 2, 2);
				
				// restore g2d
				g2d.setTransform(saveAT);
			}
		}
	}
}