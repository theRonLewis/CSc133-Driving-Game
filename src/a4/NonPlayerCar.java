package a4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;

public class NonPlayerCar extends Car {
	
	// current car strategy, only applies to NPC cars
	private ICarStrategy curStrategy;

/*	
	public NonPlayerCar(FloatPoint p, Color c, int h, float s) {
		super(p, c, h, s);
		
		// also setup the npc car vars appropriately
		setMaxDamage(30);
		
		// init the car with a LOT of fuel
		setFuelLevel(100);
	}
*/
	
	public NonPlayerCar(float x, float y, Color c, int h, float s) {
		super(x, y, c, h, s, "NPC");
		
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
		carDetails += "loc=" + df.format( this.getX() ) + "," + df.format( this.getY() ) + " ";
		
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
	public void draw(Graphics2D g2d) {
		
		// save the transform for future restoration
		AffineTransform saveAT = g2d.getTransform();
		
		// add current objects transformations
		g2d.transform(getTranslate());
		g2d.transform(getRotation());
		g2d.transform(getScale());
		
		// draw the body (should draw axles first)
		rearAxle.draw(g2d);
		frontAxle.draw(g2d);
		myBody.draw(g2d);
		
		// show current damage level in the car
		g2d.scale(1,  -1);
		g2d.setColor(Color.BLACK);
		g2d.drawString("" + getDamageLevel(), -3, 5);
		g2d.scale(1,  -1);
		
		if( isSelected() )
			drawSelected(g2d);
		
		g2d.setTransform(saveAT);
	}

	@Override
	public void drawSelected(Graphics2D g) {
		// draw small cursor at center
		g.drawLine(-3, 0, 3, 0);
		g.drawLine(0, 3, 0, -3);
	}	
}