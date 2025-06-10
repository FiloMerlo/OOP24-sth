package  org.mainPackage.engine.events.api;

import org.mainPackage.engine.events.impl.GameEvent;

/*
 * 
 */
public interface Observer {
    void onNotify(GameEvent event);
}
