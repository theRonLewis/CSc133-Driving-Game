package a3;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;

public class NonPlayerCar extends Car {
	
	// current car strategy, only applies to NPC cars
	private ICarStrategy curStrategy;

	public NonPlayerCar(FloatPoint p, Color c, int h, float s) {
		super(p, c, h, s);
		
		// also setup the npc car vars appropriately
		setMaxDamage(30);
		
		// init the car with a LOT of fuel
		setFuelLevel(100);
	}
	
	public void move(int ms){
		invokeStrategy();
		super.move(ms);
	}
	
	// override the toString method
	public String toString(){
		
		// initialize the string
		String carDetails = "Car: ";
		
		// prep decimal formatting
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("##.#");
		
		// add details to string for...
		
		// location
		carDetails += "loc=" + df.format( this.getLocation().getX() ) + "," + df.format( this.getLocation().getY() ) + " ";
		
		// color RGB
		carDetails += "color=[" + this.getColor().getRed() + "," +  this.getColor().getGreen() +  "," + this.getColor().getBlue() + "] ";
		
		// heading
		carDetails += "heading=" + getHeading() + " ";
		
		// speed
		carDetails += "speed=" + df.format( getSpeed() ) + " ";
		
		// width
		carDetails += "width=" + (int)this.getWidth() + " ";		
		
		// length
		carDetails += ( "length=" + (int)this.getLength() + "\n     ");
		
		// maxSpeed
		carDetails += ("maxSpeed=" + df.format( this.getMaxSpeed() ) + " ");
		
		// fuelLevel
		carDetails += ("fuelLevel=" + df.format( this.getFuelLevel() ) + " ");
		
		// damageLevel
		carDetails += ("damage=" + this.getDamageLevel() + " ");
		
		// car max damage
		carDetails += ("maxDamage=" + getMaxDamage() + " ");
		
		// car has traction?
		carDetails += ( "\n     hasTraction=" + hasTraction() + " ");
		
		// car pylon progress?
		carDetails += ("lastPylon=" + getHighestPylon() + " ");
		
		// car type & strategy
		carDetails += "type=NPC " + "strategy=" + curStrategy.toString();
		
		// return the car details
		return carDetails;
	}
	
	// code for applying strategies from IStrategy interface
	public ICarStrategy getStrategy(){
		return curStrategy;
	}
	
	public void setStrategy(ICarStrategy s){
		curStrategy = s;
	}
	
	public void invokeStrategy(){
		curStrategy.applyStrategy();
	}

	@Override
	public void draw(Graphics g) {
		
		// print a string at the location of the object
		//g.drawString(toString(), (intS)getLocation().getX(), (int)getLocation().getY());
		
		g.setColor(getColor());
		g.drawRect( (int)(getLocation().getX()- (getWidth()/2)) , (int)(getLocation().getY()- (getLength()/2)), (int)getWidth(), (int)getLength() );
		
		// show current damage level in the car
		g.setColor(Color.WHITE);
		g.drawString("" + getDamageLevel(), (int)(getLocation().getX()), (int)(getLocation().getY()));
		
		// draw a line to the destination... for testing
		//g.setColor(Color.RED);
		//g.drawLine( (int)getLocation().getX(), (int)getLocation().getY(), (int)curStrategy.getTargetLocation().getX(), (int)curStrategy.getTargetLocation().getY() );
		
		if( isSelected() )
			drawSelected(g);
	}

	@Override
	public void drawSelected(Graphics g) {
		// draw small cursor at center
		g.drawLine((int)getLocation().getX()-3, (int)getLocation().getY(), (int)getLocation().getX()+3, (int)getLocation().getY());
		g.drawLine((int)getLocation().getX(), (int)getLocation().getY()+3, (int)getLocation().getX(), (int)getLocation().getY()-3);
		
	}

	@Override
	public String getType() {
		return "NPC";
	}	
}