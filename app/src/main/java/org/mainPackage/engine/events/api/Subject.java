package  org.mainPackage.engine.events.api;

/**
 * {@link Subject} is the object that causes {@link Event}s to happen
 * and adds {@link Observer}s to it's list and notifies them
 */
public interface Subject {
    
    public void addObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers(Event e);
}
