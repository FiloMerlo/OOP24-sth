package org.mainPackage.engine.components.PhysicsTypes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.entities.impl.EntityImpl;

public class EnemyPhysicsTest {
        EntityImpl enemy = new EntityImpl();
        EntityImpl sonic = new EntityImpl();
        ArrayList<Rectangle2D.Float> world = new ArrayList<>();
    
    @Test
    void testTheChaseAndCollisionWithGround(){
        world.add(new Rectangle2D.Float(10, 14, 4, 4));
        sonic.addComponent(new TransformComponent(0, 0, 1, 1));
        sonic.addComponent(new PlayerPhysics(sonic, world));
        enemy.addComponent(new TransformComponent(10, 10, 4, 4));
        enemy.addComponent(new EnemyPhysics(0.3f, enemy, world, sonic));
        float firstY = enemy.getComponent(TransformComponent.class).getY();
        float firstX = enemy.getComponent(TransformComponent.class).getX();

        enemy.update(0.1f);
        float secondY = enemy.getComponent(TransformComponent.class).getY();
        float secondX = enemy.getComponent(TransformComponent.class).getX();
        //System.out.println("FirstX: " + firstX);
        //System.out.println("FirstY: " + firstY);
        //System.out.println("SecondX: " + secondX);
        //System.out.println("SecondY: " + secondY);
        assertEquals(firstY, secondY);
        assertTrue(firstX > secondX);
    }

    @Test
    void testFalling(){
        world.add(new Rectangle2D.Float(10, 16, 4, 4));
        sonic.addComponent(new TransformComponent(0, 0, 1, 1));
        sonic.addComponent(new PlayerPhysics(sonic, world));
        enemy.addComponent(new TransformComponent(10, 10, 4, 4));
        enemy.addComponent(new EnemyPhysics(0, enemy, world, sonic));
        float firstY = enemy.getComponent(TransformComponent.class).getY();

        enemy.update(0.1f);
        float secondY = enemy.getComponent(TransformComponent.class).getY();
        //System.out.println("FirstY: " + firstY);
        //System.out.println("SecondY: " + secondY);
        assertTrue(firstY < secondY);
    }
}
