package org.mainPackage.engine.entities.api;

import java.util.List;


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