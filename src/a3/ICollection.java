package a3;

// custom Collection interface
// I do not want to clutter code with unused
// methods from the built-in Java 
// collection interface
public interface ICollection {
	
	// add an object to the collection
	public boolean add(Object newObj);
	
	// get an iterator to move through the collection
	public IIterator getIterator();
	
	// remove the given object from the collection
	public boolean remove(Object o);
	
}
