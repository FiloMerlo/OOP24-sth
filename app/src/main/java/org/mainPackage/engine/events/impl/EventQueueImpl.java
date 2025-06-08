package sth.engine.events.impl;

import java.util.LinkedList;
import java.util.List;

import sth.engine.events.api.EventQueue;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'PoolProcess'");
    }

}
