package org.mainPackage.engine.entities.api;

import java.util.List;
import java.awt.Graphics;


/*  This interface could be seen as the 'world' of the game 
    since it manages lifecycle of entities on a single instance 
*/
public interface EntityManager {
    
    void addEntity(Entity entity);

    void killEntity(Entity entity);

    public void updateEntities(float deltaTime);
    //public void renderEntities(Graphics g);
    public List<Entity> getEntities();
    
    public void removeEntity(Entity entity);

    // Method to kill all entities
    public void kiLLAllEntities();

}