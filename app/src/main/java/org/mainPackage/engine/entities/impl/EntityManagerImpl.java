package org.mainPackage.engine.entities.impl;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import org.mainPackage.engine.components.*;
import org.mainPackage.engine.entities.api.*;
import org.mainPackage.engine.events.api.*;
import org.mainPackage.engine.events.impl.*;

public class EntityManagerImpl extends SubjectImpl implements EntityManager{
    private List<Entity> entities = new ArrayList<>();
    private final static EntityManager instance = new EntityManagerImpl();

    private EntityManagerImpl(){

    }

    public static EntityManager getInstance(){
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
        if (entity.hasComponent(HealthComponent.class) && entity.getComponent(HealthComponent.class).getHealth() <= 0) {
            entities.remove(entity);
        } 
    }

    @Override
    public void updateEntities(float deltaTime) {
        for (Entity entity : entities) {
            entity.update(deltaTime);
        }
    }

    @Override
    public void renderEntities(Graphics g) {
        for (Entity entity : entities) {
            if(entity.hasComponent(RenderComponent.class)){
                entity.getComponent(RenderComponent.class).Render(g);
            }
        }
    }

    @Override
    public List<Entity> getEntities() {
        return entities;
    }

    @Override
    public Entity getEntityByID(int ID) {
        return (Entity)entities.stream().filter(e -> e.getID() == ID);
    }
    
    @Override
    public void removeEntityByID(int ID) {

        entities.remove((Entity)entities.stream().filter(e -> e.getID() == ID));
    }

    @Override
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    @Override
    public void kiLLAllEntities() {
        entities.clear();
    }
}
