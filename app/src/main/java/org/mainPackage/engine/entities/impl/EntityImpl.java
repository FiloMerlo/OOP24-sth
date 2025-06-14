package org.mainPackage.engine.entities.impl;

import java.util.HashMap;

import org.mainPackage.engine.components.Component;
import org.mainPackage.engine.entities.api.Entity;
import org.mainPackage.engine.events.impl.SubjectImpl;

public class EntityImpl extends SubjectImpl implements Entity {
    private HashMap<Class<? extends Component>, Component> components = new HashMap<>();
    public EntityImpl() {}
    
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
        /*
         * I add the superclass with the same object name to allow better scalabity
         */
        Class<?> superClass = c.getClass().getSuperclass();
        components.put((Class<? extends Component>) superClass, c);
        superClass = superClass.getSuperclass();
        /*
        * I add the interfaces too because there are components which implements observers or other interfaces
        */
        for (Class<?> interfaceClass : c.getClass().getInterfaces()) {
            components.put((Class<? extends Component>) interfaceClass, c);
        }
    }
    }
}
    
