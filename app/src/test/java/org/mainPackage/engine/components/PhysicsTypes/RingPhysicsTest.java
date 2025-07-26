package org.mainPackage.engine.components.PhysicsTypes;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.entities.impl.EntityImpl;

public class RingPhysicsTest {
    @Test
    void playerPickUpTest() {
        ArrayList<Rectangle2D.Float> world = new ArrayList<>();
        world.add(new Rectangle2D.Float(10, 14, 4, 4));
        EntityImpl player = new EntityImpl();
        player.addComponent(new TransformComponent(10, 10, 4, 4));    
        player.addComponent(new PlayerPhysics(player, world));

        EntityImpl ring = new EntityImpl();
        ring.addComponent(new TransformComponent(10, 10, 2, 2));    
        ring.addComponent(new RingPhysics(ring, world, player));
        
        player.update(0.1f);
        ring.update(0.1f);
    }
}
