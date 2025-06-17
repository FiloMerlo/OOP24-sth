package org.mainPackage.engine.events.impl;

import org.mainPackage.engine.events.api.Event;
import org.mainPackage.engine.events.api.EventType;
import java.awt.event.KeyEvent;

public class InputEvent implements Event {
    private KeyEvent keyEvent;
    private EventType type;

    public InputEvent(EventType type, KeyEvent keyEvent) {
        this.type = type;
        this.keyEvent = keyEvent;
    }
    @Override
    public EventType getType() {
        return this.type;
    }
    public KeyEvent getKeyEvent(){
        return this.keyEvent;
    }
    
}
