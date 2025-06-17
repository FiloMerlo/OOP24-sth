package org.mainPackage.engine.entities.impl;

import java.util.ArrayList;
import java.util.List;

import org.mainPackage.engine.entities.api.*;
import org.mainPackage.engine.events.api.*;
import org.mainPackage.engine.events.impl.*;


public class EntityManagerImpl extends SubjectImpl implements EntityManager{
    /*
     * Separating lists to preventing concurrentiality issues
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
        }
        return instance;
    }

    @Override
    public void addEntity(Entity entity) {
        if (!entitiesToAdd.contains(entity)) {
            entitiesToAdd.add(entity);
            GameEvent e = new GameEvent(EventType.ENTITY_SPAWN, entity);
            notifyObservers(e);
        }
    }

    @Override
    public void killEntity(Entity entity) {
        entitiesToRemove.add(entity);
    }

    /*
     * Player is the last entity to be updated, plus the life cycle of an entity is:
     * ADDED -> UPDATED -> REMOVED
     */
    @Override
    public void updateEntities(float deltaTime) {
        if (!entitiesToAdd.isEmpty()){
            entitiesToUpdate.addAll(entitiesToAdd);
            entitiesToAdd.clear();
        }
        if (!entitiesToUpdate.isEmpty()){
            for (int i = 1; i < entitiesToUpdate.size(); i++){
                entitiesToUpdate.get(i).update(deltaTime);
            }
        }
        entitiesToUpdate.getFirst().update(deltaTime);
        if (!entitiesToRemove.isEmpty()){
            entitiesToUpdate.removeAll(entitiesToRemove);
            entitiesToRemove.clear();
        }
    }

    public List<Entity> getEntities() {
        return entitiesToUpdate;
    }

    @Override
    public void removeEntity(Entity entity) {
        entitiesToRemove.add(entity);
    }

    @Override
    public void killAllEntities() {
        entitiesToUpdate.clear();
    }
}
