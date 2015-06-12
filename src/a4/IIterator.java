package a4;

//custom Iterator interface
//do not want to clutter code with unused
//methods from the built-in Java 
//collection interface
public interface IIterator {
	
	// does the collection have another one?
	public boolean hasNext();
	
	// return the next object in the collection
	public Object next();
	
}
