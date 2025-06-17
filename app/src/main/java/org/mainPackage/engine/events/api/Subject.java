package  org.mainPackage.engine.events.api;

/*
 * 
 */
public interface Subject {
    
    public void addObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers(Event e);
}
