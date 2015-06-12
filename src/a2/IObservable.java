package a2;

public interface IObservable {

	public void addObserver(IObserver obs);
	public void notifyObservers();
}
