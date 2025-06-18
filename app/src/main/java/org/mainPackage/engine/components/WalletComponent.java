package org.mainPackage.engine.components;

import java.util.ArrayList;

import javax.xml.crypto.dsig.Transform;

import org.mainPackage.engine.components.PhysicsTypes.RingPhysics;
import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.engine.entities.impl.EntityManagerImpl;
import java.awt.geom.Rectangle2D;

public class WalletComponent implements Component{
    private int ringAmount;
    private EntityManagerImpl entityManager;
    private ArrayList<Rectangle2D.Float> tiles;

    public WalletComponent(EntityManagerImpl eM, ArrayList<Rectangle2D.Float> t) {
        ringAmount = 0;
        entityManager = eM;
        tiles = t;
    }

    public void increaseAmount(){
        ringAmount++;
    }
    public int getAmount() {
        return ringAmount;
    }
    public void spawnRings(){
        TransformComponent playerTransform = entityManager.getEntities().getFirst().getComponent(TransformComponent.class);
        while(ringAmount > 0){
            EntityImpl newRing = new EntityImpl();
            RingPhysics  newPhysics = new RingPhysics(newRing, tiles, (EntityImpl)(entityManager.getEntities().getFirst()));
            TransformComponent newTransform = new TransformComponent(playerTransform.getX(), playerTransform.getY(), playerTransform.getWidth(), playerTransform.getHeight());
            newRing.getComponent(RingPhysics.class).spreadOut();
            entityManager.addEntity(newRing);
            ringAmount--;
        }
    }

    @Override
    public void update(float deltaTime) {
        /*this component doesn't need to be updated */
    }
    
}
