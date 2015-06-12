package a4;

import java.util.Vector;

public class GameObjectCollection implements ICollection {

	// dynamically sized vector of objects
	@SuppressWarnings("rawtypes")
	private Vector theCollection;
	
	// construct a collection
	@SuppressWarnings("rawtypes")
	GameObjectCollection() {
		theCollection = new Vector();
	}
	
	// add an object to the collection
	@SuppressWarnings("unchecked")
	public boolean add(Object newObj) {
		if(newObj != null){
			theCollection.add(newObj);
			return true;
		}
		System.out.println("GameObjectCollection was asked to add a null object...");
		return false;
	}
	
	public Object elementAt(int i){
		return theCollection.elementAt(i);
	}

	// get an iterator to move through the collection
	public IIterator getIterator(){
		return new GameObjectIterator();
	}
	
	// remove the given object from the collection
	public boolean remove(Object o) {
		if( o instanceof Pylon)
			Pylon.decrement();
		return theCollection.remove(o);
	}
	
	public Object[] toArray(){
		return theCollection.toArray();
	}
	
	public int getSize(){
		return theCollection.size();
	}

	// returned Iterator Class 
	private class GameObjectIterator implements IIterator {
		
		// index for
		private int currObjIndex;
		
		// construct an iterator, and initialize index to -1
		public GameObjectIterator(){
			currObjIndex = -1;
		}
		
		// are there more objects in the collection?
		public boolean hasNext(){
			
			// are there any at all?
			if(theCollection.size() < 1)
				return false;
			
			// are there any after the last one we returned?
			if(currObjIndex == theCollection.size() - 1 )
				return false;
			
			// there's more, so true
			return true;
		}

		// return the next object in the collection
		public Object next() {
			currObjIndex++;
			return(theCollection.elementAt(currObjIndex));
		}
	}
}
