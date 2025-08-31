package org.mainPackage.engine.entities.impl;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.components.PhysicsTypes.RingPhysics;
import org.mainPackage.engine.components.graphics.RingAnimator;
import org.mainPackage.engine.systems.EntityManagerImpl;

public class RingFactory {
        public static EntityImpl createRing(int x, int y, int ringSize, int tileSize, ArrayList<Rectangle2D.Float> tiles, EntityImpl sonic, Boolean onHit) {
        EntityImpl ring = new EntityImpl();
        ring.addComponent(new TransformComponent(x + tileSize - ringSize, y + tileSize - ringSize, ringSize, ringSize));
        ring.addComponent(new RingAnimator());
        RingPhysics physics = new RingPhysics(ring, tiles, sonic);
        physics.changeTangibility();
        ring.addComponent(physics);
        if (onHit){
            physics.changeTangibility();
            physics.spreadOut();
        }
        physics.addObserver(EntityManagerImpl.getInstance());
        ring.addObserver(EntityManagerImpl.getInstance());
        return ring;
    }
}
