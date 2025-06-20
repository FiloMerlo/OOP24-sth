package org.mainPackage.engine.entities.impl;

import java.util.ArrayList;
import java.util.List;

import org.mainPackage.engine.components.WalletComponent;
import org.mainPackage.engine.entities.api.*;
import org.mainPackage.engine.events.api.*;
import org.mainPackage.engine.events.impl.*;

/**
 * Implemention of {@link EntityManager}
 */
public class EntityManagerImpl implements EntityManager, Observer {
    /*
     * Separating lists to prevent concurrentiality issues
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
        }
    }

    @Override
    public void killEntity(Entity entity) {
        entitiesToRemove.add(entity);
    }

    /**
     * Player is the last entity to be updated, plus the life cycle of an {@link Entity} is:
     * ADDED -> UPDATED -> REMOVED
     */
    @Override
    public void updateEntities(float deltaTime) {
        if (!entitiesToAdd.isEmpty()){
            for (Entity entity : entitiesToAdd){
                entitiesToUpdate.add(entity);
            }
            entitiesToAdd.clear();
        }
        if (!entitiesToUpdate.isEmpty()){
            for (int i = 1; i < entitiesToUpdate.size(); i++){
                entitiesToUpdate.get(i).update(deltaTime);
            }
        }
        entitiesToUpdate.getFirst().update(deltaTime);
        if (!entitiesToRemove.isEmpty()){
            for (Entity entity : entitiesToRemove){
                entitiesToUpdate.remove(entity);
            }
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
        entitiesToRemove.clear();
        entitiesToUpdate.clear();
    }

    @Override
    public void onNotify(Event e) {
        if (e instanceof GameEvent){
            GameEvent gameEvent = (GameEvent) e;
            switch(e.getType()){
                case ENTITY_DEAD:
                    removeEntity((gameEvent.getSource()));
                    break;
                case ENTITY_SPAWN:
                    addEntity((gameEvent.getSource()));
                    break;
                case SPREADED_RINGS:
                    Entity player =(EntityImpl) gameEvent.getSource();
                    player.getComponent(WalletComponent.class).spawnRings();
                    break;
                default:
                    break;
                
            }
        }
    }
}
