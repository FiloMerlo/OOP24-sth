package org.mainPackage.engine.components;

import java.util.ArrayList;

import org.mainPackage.engine.components.PhysicsTypes.RingPhysics;
import org.mainPackage.engine.components.graphics.RingAnimator;
import org.mainPackage.engine.entities.api.Entity;
import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.engine.entities.impl.EntityManagerImpl;
import org.mainPackage.engine.events.api.Event;
import org.mainPackage.engine.events.api.EventType;
import org.mainPackage.engine.events.api.Observer;
import org.mainPackage.engine.events.impl.GameEvent;
import org.mainPackage.engine.events.impl.SubjectImpl;

import java.awt.geom.Rectangle2D;

public class WalletComponent extends SubjectImpl implements Component, Observer{
    private Entity owner;
    private int ringAmount;
    private float ringSize;
    private ArrayList<Rectangle2D.Float> tiles;
    public WalletComponent(ArrayList<Rectangle2D.Float> t, Entity owner, float size) {
        ringAmount = 0;
        tiles = t;
        this.owner = owner;
        ringSize = size;
    }

    public void increaseAmount(){
        ringAmount++;
        notifyObservers(new GameEvent((EventType.RING_COLLECTED), owner));
    }
    public int getAmount() {
        return ringAmount;
    }

    public void spawnRings(){
        TransformComponent playerTransform = owner.getComponent(TransformComponent.class);
        while (ringAmount > 0){
            EntityImpl newRing = new EntityImpl();            
            TransformComponent newTransform = new TransformComponent(playerTransform.getX(), playerTransform.getY(), ringSize, ringSize);
            newRing.addComponent(newTransform);
            RingPhysics newPhysics = new RingPhysics(newRing, tiles, (EntityImpl)(EntityManagerImpl.getInstance().getEntities().getFirst()));
            newRing.addComponent(newPhysics);
            newRing.addComponent(new RingAnimator());
            newRing.getComponent(RingPhysics.class).spreadOut();
            System.out.println("Wallet Component : ANELLO AGGIUNTO");
            EntityManagerImpl.getInstance().addEntity(newRing);
            ringAmount--;
        }
    }

    @Override
    public void onNotify(Event e) {
        if (e instanceof GameEvent){
            if (e.getType() == EventType.PLAYER_HIT){
                spawnRings();
            }
        }
    }   
    
    @Override
    public void update(float deltaTime) {
        /*this component doesn't need to be updated */
    } 
}
