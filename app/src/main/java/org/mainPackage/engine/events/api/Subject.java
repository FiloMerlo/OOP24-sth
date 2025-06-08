package sth.engine.events.api;

import sth.engine.events.impl.GameEvent;

/*
 * 
 */
public interface Subject {
    
    public void addObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers(GameEvent e);
}
