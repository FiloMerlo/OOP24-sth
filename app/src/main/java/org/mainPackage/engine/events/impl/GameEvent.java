package org.mainPackage.engine.events.impl;

import org.mainPackage.engine.entities.api.Entity;
import org.mainPackage.engine.events.api.Event;
import org.mainPackage.engine.events.api.EventType;

/**
 * Different type of {@link Event} when it's {@link source} is a {@link Entity}
 */
public class GameEvent implements Event {
    
    private EventType type;
    private Entity source;

    public GameEvent(EventType type, Entity entity) {
        this.type = type;
        this.source = entity;   
    }

    @Override
    public EventType getType() {
        return type;
    }
    
    public Entity getSource(){
        return source;
    }
    
}
