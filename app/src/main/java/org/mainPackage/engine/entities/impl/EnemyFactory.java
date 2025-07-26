package org.mainPackage.engine.entities.impl;

import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.components.PhysicsTypes.EnemyPhysics;
import org.mainPackage.engine.components.graphics.StaticEnemyAnimator;
import org.mainPackage.engine.components.graphics.ChasingEnemyAnimator;
import org.mainPackage.enums.EnemyType;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class EnemyFactory {    
    public static EntityImpl createEnemy(EnemyType type, int x, int y, int enemySize, int sonicSize, int tileSize, ArrayList<Rectangle2D.Float> tileList, EntityImpl sonic) {
        EntityImpl enemy = new EntityImpl();
        enemy.addComponent(new TransformComponent(x, y + tileSize - enemySize, enemySize, enemySize));
        if (type == EnemyType.CHASING){
            enemy.addComponent(new EnemyPhysics(0.2f, enemy, tileList, sonic));                        
            enemy.addComponent(new ChasingEnemyAnimator());    
        } else {
            enemy.addComponent(new EnemyPhysics(0, enemy, tileList, sonic));                        
            enemy.addComponent(new StaticEnemyAnimator());
        }
        enemy.addObserver(EntityManagerImpl.getInstance());
        enemy.getComponent(EnemyPhysics.class).addObserver(EntityManagerImpl.getInstance());
        return enemy;
    }
}
