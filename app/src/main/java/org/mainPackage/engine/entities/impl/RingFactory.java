package org.mainPackage.engine.entities.impl;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.components.PhysicsTypes.RingPhysics;
import org.mainPackage.engine.components.graphics.RingAnimator;

public class RingFactory {
        public static EntityImpl createRing(int x, int y, int ringSize, int tileSize, ArrayList<Rectangle2D.Float> tiles, EntityImpl sonic) {
        EntityImpl ring = new EntityImpl();
        ring.addComponent(new TransformComponent(x + tileSize - ringSize, y + tileSize - ringSize, ringSize, ringSize));
        ring.addComponent(new RingAnimator());
        RingPhysics physics = new RingPhysics(ring, tiles, sonic);
        ring.addComponent(physics);
        physics.addObserver(EntityManagerImpl.getInstance());
        ring.addObserver(EntityManagerImpl.getInstance());
        return ring;
    }
}
