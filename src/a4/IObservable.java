package a4;

public interface IObservable {

	public void addObserver(IObserver obs);
	public void notifyObservers();
}
