package org.mainPackage.engine.entities.impl;

import java.util.HashMap;

import org.mainPackage.engine.components.Component;
import org.mainPackage.engine.entities.api.Entity;
import org.mainPackage.engine.events.impl.SubjectImpl;

/**
 * Implemention of {@link Entity}
 * It is a {@link SubjectImpl}, it causes {@link org.mainPackage.engine.events.api.Event} 
 * to occurs and fire {@link OnNotify}
 */
public class EntityImpl extends SubjectImpl implements Entity {
    private HashMap<Class<? extends Component>, Component> components = new HashMap<>();
    
    public EntityImpl() {
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
        return componentClass.cast(components.get(componentClass));

    }
    
    /**
     * Given a {@link Component} @param c , it is added to {@link Components}
     * To allow abstraction with update methods, I add superclasses and interfaces
     * that are assignable from {@link Component}
     */
    
    @SuppressWarnings("unchecked")
    @Override
    public void addComponent(Component c){
        components.put(c.getClass(), c);
        Class<?> superClass;
        do {
        superClass = c.getClass().getSuperclass();

        if (superClass != null && Component.class.isAssignableFrom(superClass))
            components.put((Class<? extends Component>) superClass, c);
        } while(!superClass.getClass().getSuperclass().isAssignableFrom(Component.class));

        for (Class<?> interfaceClass : c.getClass().getInterfaces()) {
            if (Component.class.isAssignableFrom(interfaceClass)){
                components.put((Class<? extends Component>) interfaceClass, c);
            }
        }
    }
}

    
