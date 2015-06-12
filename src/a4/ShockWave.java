package a4;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;
import java.util.Vector;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ShockWave extends MovableObject {

	// the lifespan of a shock wave, in ms
	private final static int THELIFESPAN = 1500;
	
	// the speed of a shock wave
	private final static int SPEED = 200;
	private final static int MAX_LEVEL = 8;
	private final static float EPSILON = (float)0.001;
	
	// this shock waves current lifespan
	private int lifeSpan;
	
	// holds the 4 control points
	Vector cpVector = new Vector();
	
	ShockWave(){
		super();
		lifeSpan = THELIFESPAN;
		initPoints();
	}
	
	ShockWave(float x, float y){
		super(x, y, SPEED);
		lifeSpan = THELIFESPAN;
		initPoints();
	}
	
	// initialize the shock wave with 4 control points
	// if called a second time, will not create new points
	private void initPoints(){
		// set up random points, don't do this for now
		if(cpVector.isEmpty()){
			// try to make semi-consistent, but still random and unique, shockwaves
			cpVector.add(new ControlPoint(randInt(-30, -5), randInt(-30, -5)));
			cpVector.add(new ControlPoint(randInt(-30, -5), randInt(  5, 30)));
			cpVector.add(new ControlPoint(randInt(  5, 30), randInt(  5, 30)));
			cpVector.add(new ControlPoint(randInt(  5, 30), randInt(-30, -5)));	
		}
	}
	
	@Override
	public void move(int ms){
		super.move(ms);
		lifeSpan -= ms;
	}
	
	public boolean isExpired(){
		return (lifeSpan <= 0);
	}
	
	@Override
	public String toString() {
		// initialize the string
		String swDetails = "ShockWave: ";
		
		// prep decimal formatting
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("##.#");
		
		// add details to the string for...
		
		// location
		swDetails += "loc=" + df.format( this.getX() ) + "," + df.format( this.getY() ) + " ";
		
		// color
		swDetails += "color=[" + this.getColor().getRed() + "," + this.getColor().getGreen() + "," + this.getColor().getBlue() + "] ";
		
		// heading
		swDetails += "heading=" + this.getHeading() + " ";
		
		// speed
		swDetails += "speed=" + df.format( getSpeed() ) + " ";
		
		//return all the details
		return swDetails;
	}
	
	private void drawBezierCurve(Graphics2D g2d, Vector controlPoints, int level){
		if( straightEnough(controlPoints)  || (level > MAX_LEVEL) ){
			g2d.setColor(getColor());
			g2d.drawLine( (int)((ControlPoint)controlPoints.elementAt(0)).getX(), (int)((ControlPoint)controlPoints.elementAt(0)).getY(),
						  (int)((ControlPoint)controlPoints.elementAt(3)).getX(), (int)((ControlPoint)controlPoints.elementAt(3)).getY());
		} else{
			Vector leftPoints = new Vector(), rightPoints = new Vector();
			subdivideCurve(controlPoints, leftPoints, rightPoints);
			drawBezierCurve(g2d, leftPoints, level+1);
			drawBezierCurve(g2d, rightPoints, level+1);
		}
	}
	
	
	private void subdivideCurve(Vector controlPoints, Vector leftSubVector, Vector rightSubVector){
		ControlPoint l0, l1, l2, l3, r0, r1, r2, r3;
		
		l0 = new ControlPoint( ((ControlPoint)controlPoints.elementAt(0)) );
		
		l1 = new ControlPoint( ((((ControlPoint)controlPoints.elementAt(0)).getX() + ((ControlPoint)controlPoints.elementAt(1)).getX()) / 2), 
							   ((((ControlPoint)controlPoints.elementAt(0)).getY() + ((ControlPoint)controlPoints.elementAt(1)).getY()) / 2));
		
		l2 = new ControlPoint( (l1.getX()/2) + ((((ControlPoint)controlPoints.elementAt(1)).getX() + ((ControlPoint)controlPoints.elementAt(2)).getX()) / 4), 
							   (l1.getY()/2) + ((((ControlPoint)controlPoints.elementAt(1)).getY() + ((ControlPoint)controlPoints.elementAt(2)).getY()) / 4));
		
		r3 = new ControlPoint( ((ControlPoint)controlPoints.elementAt(3)) );
		
		r2 = new ControlPoint( ((((ControlPoint)controlPoints.elementAt(2)).getX() + ((ControlPoint)controlPoints.elementAt(3)).getX()) / 2), 
				   			   ((((ControlPoint)controlPoints.elementAt(2)).getY() + ((ControlPoint)controlPoints.elementAt(3)).getY()) / 2));
		
		r1 = new ControlPoint( (r2.getX()/2) + ((((ControlPoint)controlPoints.elementAt(1)).getX() + ((ControlPoint)controlPoints.elementAt(2)).getX()) / 4), 
				   			   (r2.getY()/2) + ((((ControlPoint)controlPoints.elementAt(1)).getY() + ((ControlPoint)controlPoints.elementAt(2)).getY()) / 4));
		
		l3 = new ControlPoint( ( (l2.getX() + r1.getX()) / 2), ( (l2.getY() + r1.getY()) / 2) );
		
		r0 = new ControlPoint( l3 );
		
		leftSubVector.add(l0);
		leftSubVector.add(l1);
		leftSubVector.add(l2);
		leftSubVector.add(l3);
		
		rightSubVector.add(r0);
		rightSubVector.add(r1);
		rightSubVector.add(r2);
		rightSubVector.add(r3);
	}
	
	private boolean straightEnough(Vector controlPoints){
		
		float d1, d2;
		
		// d1^2 
		d1 = length( ((ControlPoint)cpVector.elementAt(0)), (((ControlPoint)cpVector.elementAt(1))) )
		   + length( ((ControlPoint)cpVector.elementAt(1)), (((ControlPoint)cpVector.elementAt(2))) )
		   + length( ((ControlPoint)cpVector.elementAt(2)), (((ControlPoint)cpVector.elementAt(3))) );
		
		// d2^2
		d2 = length( ((ControlPoint)cpVector.elementAt(0)), (((ControlPoint)cpVector.elementAt(3))) );
		
		if( Math.abs(d1-d2) < EPSILON )
			return true;
		else
			return false;
	}
	
	private float length(ControlPoint p1, ControlPoint p2){
		float length, xDis, yDis;
		
		xDis = p2.getX() - p1.getX();
		yDis = p2.getY() - p1.getY();
		
		// length = sqrt( xDis^2 + yDis^2 )
		length =  (float)Math.sqrt( (Math.pow(xDis, 2) + Math.pow(yDis, 2)) );
		
		return length;
	}

	@Override
	public void draw(Graphics2D g2d) {
		
		// save the AT for restoration later
		AffineTransform saveAT = g2d.getTransform();
		
		// add current objects transformations
		g2d.transform(getTranslate());
		g2d.transform(getRotation());
		g2d.transform(getScale());
	
	/*	
		// draw the control point lines first, so curve is on top
		g2d.setColor(Color.RED);
		g2d.drawLine( (int)((ControlPoint)cpVector.elementAt(0)).getX(), (int)((ControlPoint)cpVector.elementAt(0)).getY(), (int)((ControlPoint)cpVector.elementAt(1)).getX(), (int)((ControlPoint)cpVector.elementAt(1)).getY() );
		g2d.setColor(Color.GREEN);
		g2d.drawLine( (int)((ControlPoint)cpVector.elementAt(1)).getX(), (int)((ControlPoint)cpVector.elementAt(1)).getY(), (int)((ControlPoint)cpVector.elementAt(2)).getX(), (int)((ControlPoint)cpVector.elementAt(2)).getY() );
		g2d.setColor(Color.BLUE);
		g2d.drawLine( (int)((ControlPoint)cpVector.elementAt(2)).getX(), (int)((ControlPoint)cpVector.elementAt(2)).getY(), (int)((ControlPoint)cpVector.elementAt(3)).getX(), (int)((ControlPoint)cpVector.elementAt(3)).getY() );
		g2d.setColor(Color.WHITE);
		g2d.drawLine( (int)((ControlPoint)cpVector.elementAt(3)).getX(), (int)((ControlPoint)cpVector.elementAt(3)).getY(), (int)((ControlPoint)cpVector.elementAt(0)).getX(), (int)((ControlPoint)cpVector.elementAt(0)).getY() );
	*/
		
		// set color and draw the bezier curve 
		g2d.setColor(getColor());
		drawBezierCurve(g2d, cpVector, 1);
		
		// restore the g2d object for the next object
		g2d.setTransform(saveAT);
	}

	@Override
	public void drawSelected(Graphics2D g2d) {
		// not implemented since Shock Waves cannot
		// be selected - see contains()
	}

	@Override
	public boolean contains(float x, float y) {
		// return false since Shock Waves cannot be selected
		// note most other classes have this implemented,
		// but commented out (for now?)
		return false;
	}

	
	
	// NOTE the remaining methods have not been fully implemented
	// since we are not handling collisions with shock waves
	// there is nothing to be done when a collision occurs,
	// so there is no need to implement detection w/it
	
	@Override
	public int getLeft() {
		return 0;
	}

	@Override
	public int getRight() {
		return 0;
	}

	@Override
	public int getTop() {
		return 0;
	}

	@Override
	public int getBottom() {
		return 0;
	}
	
	private class ControlPoint {
		
		// x and y coords
		private float x, y;
		
		// constructor, expects nums from ShockWave.randInt()
		ControlPoint(float xIn, float yIn){
			x = xIn;
			y = yIn;
		}
		
		ControlPoint(ControlPoint p){
			x = p.getX();
			y = p.getY();
		}
		
		public float getX(){
			return x;
		}
		
		public float getY(){
			return y;
		}
		
		public String toString(){
			// prep decimal formatting
			DecimalFormat df = new DecimalFormat();
			df.applyPattern("##.#");
			
			return ("Control Point - X: " + df.format(x)  + " ... Y: " + df.format(y));
		}
	}
}
