package sth.engine.events.api;

import sth.engine.events.impl.GameEvent;

/*
 * 
 */
public interface Observer {
    void onNotify(GameEvent event);
}
