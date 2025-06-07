package sth.engine.entities.api;

import sth.engine.components.Component;

public interface Entity {
    
    int getID();

    void update(float deltaTime);

    Boolean hasComponent(Class<? extends Component> componentClass);

    <T extends Component> T getComponent(Class<T> componentClass);

    void addComponent(Component c);

}