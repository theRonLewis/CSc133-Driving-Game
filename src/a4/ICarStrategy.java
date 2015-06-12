package a4;

// custom interface for NPC car strategies

public interface ICarStrategy {
	
	// must be able to apply a strategy
	public void applyStrategy();

	// get the location of the target, for drawing
//	public FloatPoint getTargetLocation();
	
	// get x and y of target, for drawing sometimes
	public float getTargetX();
	public float getTargetY();
}
