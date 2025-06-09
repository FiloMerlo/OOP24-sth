package org.mainPackage.engine.events.api;

import org.mainPackage.engine.events.impl.GameEvent;

/*
 * 
 */
public interface Subject {
    
    public void addObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers(GameEvent e);
}
