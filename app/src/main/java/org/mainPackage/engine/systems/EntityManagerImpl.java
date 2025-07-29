package org.mainPackage.engine.systems;

import java.util.ArrayList;
import java.util.List;
import org.mainPackage.engine.entities.api.*;
import org.mainPackage.engine.events.api.*;
import org.mainPackage.engine.events.impl.*;

/**
 * Implemention of {@link EntityManager}.
 */
public class EntityManagerImpl implements EntityManager, Observer {
    /*
     * Separation list to prevent concurrentiality issues
     */
    private List<Entity> entitiesToUpdate; 
    private List<Entity> entitiesToAdd;    
    private List<Entity> entitiesToRemove; 
    private static EntityManagerImpl instance = null;

    private EntityManagerImpl(){
        entitiesToUpdate = new ArrayList<>();
        entitiesToAdd = new ArrayList<>();
        entitiesToRemove = new ArrayList<>();
    }

    public static EntityManagerImpl getInstance(){
        if (instance == null){
            instance = new EntityManagerImpl();
            System.out.println("EntityManagerImpl - Istanza creata.");
        }
        return instance;
    }

    @Override
    public void addEntity(Entity entity) {
        if (!entitiesToAdd.contains(entity)) {
            entitiesToAdd.add(entity);
        }
    }

    @Override
    public void killEntity(Entity entity) {
        entitiesToRemove.add(entity);
    }

    /**
     * Player is the last entity to be updated, plus the life cycle of  an {@link Entity} is : 
     * 1. addEntity() -> 2. updateEntities() -> 3. removeEntity() -> 4. killEntity()
     * Updates entities: adds new ones, updates active ones, and removes those marked for removal.
     * Uses local copies to prevent ConcurrentModificationException
     * if entities modify the lists during update.
     * * @param deltaTime time elapsed since last frame
     */
   
    @Override
    public void updateEntities(float deltaTime) {
       
        List<Entity> currentBatchToAdd = new ArrayList<>(entitiesToAdd);
        entitiesToAdd.clear(); 
        for (Entity entity : currentBatchToAdd) {
            
            if (!entitiesToUpdate.contains(entity)) {
                entitiesToUpdate.add(entity);
            }
        }

        List<Entity> entitiesToUpdateCopy = new ArrayList<>(entitiesToUpdate);
        for (Entity entity : entitiesToUpdateCopy){
            entity.update(deltaTime);
        }

        List<Entity> currentBatchToRemove = new ArrayList<>(entitiesToRemove);
        entitiesToRemove.clear();
        for (Entity entity : currentBatchToRemove){
            entitiesToUpdate.remove(entity);
        }
    }

    /**
     * @return a copy of the list of entities to be updated, crucial for avoiding
     * ConcurrentModificationException if entities modify the list during update.
     */
    
     public List<Entity> getEntities() {
        return new ArrayList<>(entitiesToUpdate);
    }

    
    @Override
    public void removeEntity(Entity entity) {
        entitiesToRemove.add(entity);
    }

  
    @Override
    public void killAllEntities() {
        System.out.println("EntityManagerImpl - Chiamato killAllEntities(). Svuoto tutte le liste.");
        entitiesToUpdate.clear();
        entitiesToAdd.clear();
        entitiesToRemove.clear();
    }

    @Override
    public void onNotify(Event e) {
        if (e instanceof GameEvent){
            GameEvent gameEvent = (GameEvent) e;
            switch (e.getType()){
                case ENTITY_DEAD:
                    removeEntity((gameEvent.getSource()));
                    break;
                case ENTITY_SPAWN:
                    addEntity((gameEvent.getSource()));
                    break;
                default:
                    break;
            }
        }
    }
}