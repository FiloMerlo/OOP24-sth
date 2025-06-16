package org.mainPackage.engine.entities.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mainPackage.engine.components.PhysicsComponent;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.components.PhysicsTypes.EnemyPhysics;
import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.engine.events.api.EventType;
import org.mainPackage.enums.action;
import org.mainPackage.enums.direction;

public class PhysicsTest {
    @Test 
    void playerCollisionsTest(){
        EntityImpl testEntity = new EntityImpl();
        testEntity.addComponent(new TransformComponent(10, 10, 4, 4));
        Rectangle2D.Float rect1 = new Rectangle2D.Float(10, 13, 4, 4);
        Rectangle2D.Float rect2 = new Rectangle2D.Float(13, 10, 4, 4);
        ArrayList<Rectangle2D.Float> rects = new ArrayList<>();
        rects.add(rect1);
        rects.add(rect2);
        testEntity.addComponent(new PlayerPhysics(testEntity, rects));
        float firstX = testEntity.getComponent(TransformComponent.class).getX();
        float firstY = testEntity.getComponent(TransformComponent.class).getY();

        /*check vertical collisions*/
        testEntity.update(0.1f);
        float secondY = testEntity.getComponent(TransformComponent.class).getY();
        testEntity.getComponent(PlayerPhysics.class).jump();
        testEntity.update(0.1f);
        float thirdY = testEntity.getComponent(TransformComponent.class).getY();
        assertEquals(firstY, secondY);
        assertTrue(firstY >= thirdY, "Sonic non ha saltato correttamente");
        testEntity.update(0.1f);
        assertEquals(action.falling, testEntity.getComponent(PlayerPhysics.class).getAction());

        /*wait until sonic hits the ground.*/
        while(testEntity.getComponent(PlayerPhysics.class).getAction() != action.idle) {
            testEntity.update(0.1f);
        }

        /*check horyzontal collisions*/
        testEntity.getComponent(PlayerPhysics.class).moveX(direction.right);
        float secondX = testEntity.getComponent(TransformComponent.class).getX();
        testEntity.getComponent(PlayerPhysics.class).moveX(direction.left);
        float thirdX = testEntity.getComponent(TransformComponent.class).getX();
        assertEquals(firstX, secondX);
        assertEquals(firstX - 1, thirdX);
    }

    @Test
    void checkIfSonicDies(){
        EntityImpl testSonic = new EntityImpl();
        EntityImpl testEnemy = new EntityImpl();
        testSonic.addComponent(new TransformComponent(10, 10, 4, 4));
        testSonic.addComponent(new PlayerPhysics(testSonic, new ArrayList<>()));
        testEnemy.addComponent(new TransformComponent(10, 10, 3, 3));
        testEnemy.addComponent(new EnemyPhysics(0, testEnemy, new ArrayList<>(), testSonic));
        final boolean[] gameOverTriggered = {false};
        
        testEnemy.update(0.1f);
        testSonic.update(0.1f);

        testSonic.getComponent(PlayerPhysics.class).addObserver(event -> {
        if (event.getType() == EventType.GAME_OVER) {
            gameOverTriggered[0] = true;
        }
         });
         testEnemy.update(0.1f);
        testSonic.update(0.1f);
        System.out.println("State:" + testSonic.getComponent(PlayerPhysics.class).getAction());
         assertTrue(gameOverTriggered[0], "Sonic dies");
        /*TODO check if sonic dies*/
    }
}
