package org.mainPackage.engine.entities.impl;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mainPackage.engine.components.PhysicsComponent;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.engine.components.graphics.GenericAnimator;
import org.mainPackage.engine.components.graphics.SonicAnimator;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class EntityTest {
    @Test
    void componentsAddedCorrectly(){
        EntityImpl testEntity = new EntityImpl();
        testEntity.addComponent(new TransformComponent(0, 0, 0, 0));
        testEntity.addComponent(new PlayerPhysics(testEntity, new ArrayList<Rectangle2D.Float>()));
        testEntity.addComponent(new SonicAnimator());
        //assertInstanceOf(TransformComponent.class, testEntity.getComponent(TransformComponent.class));
        assertInstanceOf(PlayerPhysics.class, testEntity.getComponent(PlayerPhysics.class));
        assertInstanceOf(SonicAnimator.class, testEntity.getComponent(GenericAnimator.class));
        assertNotNull(testEntity.getComponent(PhysicsComponent.class).getHitbox());
    }
}
