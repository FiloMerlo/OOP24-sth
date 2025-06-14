package org.mainPackage.engine.entities.impl;

import java.util.ArrayList;
import java.util.List;

import org.mainPackage.engine.entities.api.*;
import org.mainPackage.engine.events.api.*;
import org.mainPackage.engine.events.impl.*;


public class EntityManagerImpl extends SubjectImpl implements EntityManager{
    private List<Entity> entities = new ArrayList<>();
    private final static EntityManagerImpl instance = new EntityManagerImpl();

    private EntityManagerImpl(){

    }

    public static EntityManagerImpl getInstance(){
        return instance;
    }

    @Override
    public void addEntity(Entity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
            GameEvent e = new GameEvent(EventType.ENTITY_SPAWN, entity);
            notifyObservers(e);
        }
    }

    @Override
    public void killEntity(Entity entity) {
        entities.remove(entity);
    }


    @Override
    public void updateEntities(float deltaTime) {
        for (Entity entity : entities) {
            entity.update(deltaTime);
        }
    }
    /* Se ne occupa direttamente il PlayingRenderer */
    public List<Entity> getEntities() {
        return entities;
    }

    @Override
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    @Override
    public void killAllEntities() {
        entities.clear();
    }
}
