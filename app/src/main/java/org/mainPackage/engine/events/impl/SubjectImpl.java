package org.mainPackage.engine.events.impl;

import java.util.List;
import org.mainPackage.engine.events.api.Event;
import org.mainPackage.engine.events.api.Observer;
import org.mainPackage.engine.events.api.Subject;
import java.util.ArrayList;

/**
 * Implementaion of {@link Subject} , it comes with a list of {@link Observer}s
 */

 public class SubjectImpl implements Subject {
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer o) {
        if (!observers.contains(o)) { // Prevent duplicates
            observers.add(o);
        }
    }   

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    /**
     * Notify all observers in the list, using a copy of the list to avoid
     * ConcurrentModificationException if an observer modifies the list during notification.
     */
    
    @Override
    public void notifyObservers(Event e) {
        new ArrayList<>(observers).forEach(o -> o.onNotify(e));
    }
    
}
