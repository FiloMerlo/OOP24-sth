package org.mainPackage.engine.events.api;

import java.util.List;

import  org.mainPackage.engine.events.impl.GameEvent;

public interface EventQueue {

    List<GameEvent> getEvents();
    void PoolProcess();
}