package org.mainPackage.engine.entities.impl;

import java.util.HashMap;

import org.mainPackage.engine.components.Component;
import org.mainPackage.engine.entities.api.Entity;

public class EntityImpl implements Entity {
    private int ID;
    private HashMap<Class<? extends Component>, Component> components = new HashMap<>();
    public EntityImpl(int ID) {
        this.ID = ID;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void update(float deltaTime){
        for (Component c : components.values()){
            c.update(deltaTime);
        }
    }
    @Override
    public Boolean hasComponent(Class<? extends Component> componentClass){
        return components.containsKey(componentClass);
    }
    @Override
    public <T extends Component> T getComponent(Class<T> componentClass){
        return componentClass.cast(components.get(Component.class));
    }
    @Override
    public void addComponent(Component c){
        components.put(c.getClass(), c);
    }
}
    