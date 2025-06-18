package  org.mainPackage.engine.events.impl;

import java.util.LinkedList;
import java.util.List;

import  org.mainPackage.engine.events.api.EventQueue;

public class EventQueueImpl implements EventQueue {
    private List<GameEvent> events = new LinkedList<>();
    private static EventQueue istanceQueue = new EventQueueImpl();

    private EventQueueImpl(){

    }

    public static EventQueue getIstanceQueue() {
        return istanceQueue;
    }
    @Override
    public List<GameEvent> getEvents() {
        return events;
    }

    @Override
    public void PoolProcess() {
        
    }

}
