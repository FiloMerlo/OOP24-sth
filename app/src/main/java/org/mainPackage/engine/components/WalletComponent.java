package org.mainPackage.engine.components;

import java.util.ArrayList;
import java.util.List;

import org.mainPackage.engine.components.PhysicsTypes.RingPhysics;
import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.engine.entities.impl.EntityManagerImpl;
import org.mainPackage.engine.events.api.Event;
import org.mainPackage.engine.events.api.EventType;
import org.mainPackage.engine.events.api.Observer;
import org.mainPackage.engine.events.impl.GameEvent;
import org.mainPackage.engine.events.impl.SubjectImpl;

import java.awt.geom.Rectangle2D;

public class WalletComponent extends SubjectImpl implements Component, Observer{
    private int ringAmount;
    private ArrayList<Rectangle2D.Float> tiles;
    public WalletComponent(ArrayList<Rectangle2D.Float> t) {
        ringAmount = 0;
        tiles = t;
        addObserver(EntityManagerImpl.getInstance());
    }

    public void increaseAmount(){
        ringAmount++;
    }
    public int getAmount() {
        return ringAmount;
    }
    public void spawnRings(){
        TransformComponent playerTransform = EntityManagerImpl.getInstance().getEntities().getFirst().getComponent(TransformComponent.class);
        while(ringAmount > 0){
            EntityImpl newRing = new EntityImpl();
            RingPhysics  newPhysics = new RingPhysics(newRing, tiles, (EntityImpl)(EntityManagerImpl.getInstance().getEntities().getFirst()));
            TransformComponent newTransform = new TransformComponent(playerTransform.getX(), playerTransform.getY(), playerTransform.getWidth(), playerTransform.getHeight());
            newRing.addComponent(newPhysics);
            newRing.addComponent(newTransform);
            newRing.getComponent(RingPhysics.class).spreadOut();
            EntityManagerImpl.getInstance().addEntity(newRing);
            ringAmount--;
        }
    }

    @Override
    public void update(float deltaTime) {
        /*this component doesn't need to be updated */
    }

    @Override
    public void onNotify(Event e) {
        if (e instanceof GameEvent){
            if (e.getType() == EventType.PLAYER_HIT){
                spawnRings();
                GameEvent ringSpreadEvent = new GameEvent(EventType.SPREADED_RINGS, ((GameEvent) e).getSource());
                notifyObservers(ringSpreadEvent);
            }
        }
    }
    
}
