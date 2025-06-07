package sth.engine.events.api;

import java.util.List;

import sth.engine.events.impl.GameEvent;

public interface EventQueue {

    List<GameEvent> getEvents();
    void PoolProcess();
}