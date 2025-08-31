package  org.mainPackage.engine.events.api;

/**
 * Given a @param Event by a {@link Subject} , this interface is implemented by
 * those classes which need to observe the {@link Event} , then behave according to the
 * {@link EventType} and it's source
 */
public interface Observer {
    void onNotify(Event e);
}
