package org.mainPackage.engine.events.impl;

import java.util.List;

import org.mainPackage.engine.events.api.Observer;
import org.mainPackage.engine.events.api.Subject;

import java.util.ArrayList;

public class SubjectImpl implements Subject {
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(GameEvent e) {
        observers.stream().forEach(o -> o.onNotify(e));
    }
    
}
