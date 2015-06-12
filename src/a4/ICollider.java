package a4;

// interface for collidable objects

// I still am unsure about the handleCollision() method living in each object
// I feel like this might be better handled living in the CollisionHandler object
// for this game - it might be better for the GameWorld to have a CollisionHandler
// which is specific to this game, to allow these objects to be re-used in a similar game,
// but allowing the developer to handle the collisions and what happens at the 
// GameWorld level, and not in the individual concrete objects.

public interface ICollider {
	
	// do these objects collide
	public boolean collidesWith(ICollider otherObj);
	
	// used for easily determining what is colliding
	//public abstract String getType();
	
	// used as bounds for collision detection
	public abstract int getLeft();
	public abstract int getRight();
	public abstract int getTop();
	public abstract int getBottom(); 
}
