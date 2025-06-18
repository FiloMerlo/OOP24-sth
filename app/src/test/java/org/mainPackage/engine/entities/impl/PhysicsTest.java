package org.mainPackage.engine.entities.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.enums.action;
import org.mainPackage.enums.direction;

public class PhysicsTest {
    @Test 
    void playerCollisionsTest(){
        EntityImpl player = new EntityImpl();
        player.addComponent(new TransformComponent(10, 10, 4, 4));    
        ArrayList<Rectangle2D.Float> rects = new ArrayList<>();
        rects.add(new Rectangle2D.Float(10, 14, 4, 4));
        rects.add(new Rectangle2D.Float(14, 10, 4, 4));
        player.addComponent(new PlayerPhysics(player, rects));
        float firstX = player.getComponent(TransformComponent.class).getX();
        float firstY = player.getComponent(TransformComponent.class).getY();
        System.out.println("FirstY: " + firstY);

        /*check vertical collisions*/
        player.update(0.1f);
        float secondY = player.getComponent(TransformComponent.class).getY();
        System.out.println("SecondY: " + secondY);
        player.getComponent(PlayerPhysics.class).jump();
        player.update(0.1f);
        float thirdY = player.getComponent(TransformComponent.class).getY();
        System.out.println("thirdY: " + thirdY);
        assertEquals(firstY, secondY);
        assertTrue(firstY >= thirdY, "Sonic non ha saltato correttamente");
        assertEquals(action.jumping, player.getComponent(PlayerPhysics.class).getAction());

        /*wait until sonic hits the ground.*/
        while(player.getComponent(PlayerPhysics.class).getAction() == action.jumping) {
            player.update(0.1f);
        }
        System.out.println(player.getComponent(PlayerPhysics.class).getAction());

        /*check horyzontal collisions*/
        player.getComponent(PlayerPhysics.class).setWill(direction.right, true);
        player.update(0.1f);
        float secondX = player.getComponent(TransformComponent.class).getX();
        player.getComponent(PlayerPhysics.class).setWill(direction.left, true);
        player.getComponent(PlayerPhysics.class).setWill(direction.right, false);
        player.update(0.1f);
        float thirdX = player.getComponent(TransformComponent.class).getX();
        System.out.println("firstX: " + firstX);
        System.out.println("secondX: " + secondX);
        System.out.println("thirdX: " + thirdX);
        assertEquals(firstX, secondX);
        assertTrue(firstX > thirdX, "Sonic non si è mosso correttamente");
    }

    /*@Test
    void checkIfSonicDies(){
        EntityImpl testSonic = new EntityImpl();
    EntityImpl testEnemy = new EntityImpl();
    
    ArrayList<Rectangle2D.Float> tileList = new ArrayList<>();
    tileList.add(new Rectangle2D.Float(10, 14, 4, 2)); // piattaforma sotto Sonic

    testSonic.addComponent(new TransformComponent(10, 10, 4, 4));
    testSonic.addComponent(new PlayerPhysics(testSonic, tileList));
    testEnemy.addComponent(new TransformComponent(10, 10, 3, 3));
    testEnemy.addComponent(new EnemyPhysics(0, testEnemy, tileList, testSonic));

    PlayerPhysics sonicPhysics = testSonic.getComponent(PlayerPhysics.class);

    final boolean[] gameOverTriggered = {false};
    sonicPhysics.addObserver(event -> {
        if (event.getType() == EventType.GAME_OVER) {
            gameOverTriggered[0] = true;
        }
    });
    testEnemy.update(0);
    testSonic.update(0);
    System.out.println("State: " + sonicPhysics.getAction());

    assertTrue(gameOverTriggered[0], "Sonic dies"); 
    }*/
}
