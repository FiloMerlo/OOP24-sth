package org.mainPackage.engine.systems;

import java.util.List;

import org.mainPackage.engine.entities.api.Entity;


/**
*   It manages the lifecycle of multiple {@link Entity} on a single instance 
*/
public interface EntityManager {
    
    void addEntity(Entity entity);

    void killEntity(Entity entity);

    public void updateEntities(float deltaTime);

    public List<Entity> getEntities();
    
    public void removeEntity(Entity entity);

    public void killAllEntities();

}