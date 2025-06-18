package org.mainPackage;

import org.mainPackage.core.Game;
import org.mainPackage.engine.components.*;
import org.mainPackage.engine.components.PhysicsTypes.*;
import org.mainPackage.engine.components.graphics.*;
import org.mainPackage.engine.entities.impl.*;
import org.mainPackage.state_management.GameStateManager;


import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        int tileSize = 64, enemySize = 64, ringSize = 64, sonicSize = 54;
        ArrayList<Rectangle2D.Float> tileList = new ArrayList<>();
        EntityManagerImpl entityManager = EntityManagerImpl.getInstance();

        /* Sonic */
        EntityImpl sonic = new EntityImpl();
        
        sonic.addComponent(new PlayerPhysics(sonic, tileList));
        sonic.addComponent(new SonicAnimator());
        sonic.addComponent(new WalletComponent(tileList));
        entityManager.addEntity(sonic);
       
        /* Debug */
        System.out.println("Transform: " + sonic.getComponent(TransformComponent.class));
        System.out.println("Physics: " + sonic.getComponent(PhysicsComponent.class));
        System.out.println("Animator: " + sonic.getComponent(SonicAnimator.class));

        Game game = new Game();

        /* Level */
        int[][] levelGrid = {
            {1, 4, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 5, 5, 5, 5, 5, 2, 2, 1},
            {1, 0, 0, 0, 1, 0, 0, 3, 6, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        EntityImpl goal = null;

        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 10; c++) {
                int xPos = c * tileSize;
                int yPos = r * tileSize;

                switch (levelGrid[r][c]) {
                    case 1 -> {
                        Rectangle2D.Float tile = new Rectangle2D.Float(xPos, yPos, tileSize, tileSize);
                        tileList.add(tile);
                        System.out.println("Tile placed at: " + xPos + ", " + yPos);
                    }
                    case 2 -> {
                        EntityImpl staticEnemy = new EntityImpl();
                        staticEnemy.addComponent(new TransformComponent(xPos, yPos + tileSize - enemySize, enemySize, enemySize));
                        //staticEnemy.addComponent(new EnemyPhysics(0, staticEnemy, tileList, sonic));
                        staticEnemy.addComponent(new StaticEnemyAnimator());
                        entityManager.addEntity(staticEnemy);
                        System.out.println("Static enemy added");
                    }
                    case 3 -> {
                        EntityImpl chasingEnemy = new EntityImpl();
                        chasingEnemy.addComponent(new TransformComponent(xPos, yPos + tileSize - enemySize, enemySize, enemySize));
                        //chasingEnemy.addComponent(new EnemyPhysics(5, chasingEnemy, tileList, sonic));
                        chasingEnemy.addComponent(new ChasingEnemyAnimator());
                        entityManager.addEntity(chasingEnemy);
                        System.out.println("Chasing enemy added");
                    }
                    case 4 -> {
                        /* Replace Sonic's TransformComponent with a new one derived from map */
                        sonic.addComponent(new TransformComponent(xPos, yPos + tileSize - sonicSize, sonicSize, sonicSize));
                        System.out.println("Sonic positioned case 4");
                    }

                    case 5 -> {
                        EntityImpl ring = new EntityImpl();
                        ring.addComponent(new TransformComponent(xPos + (tileSize - ringSize) / 2, yPos + (tileSize - ringSize) / 2, ringSize, ringSize));
                        //ring.addComponent(new RingPhysics(ring, tileList, (PlayerPhysics)sonic.getComponent(PhysicsComponent.class)));
                        ring.addComponent(new RingAnimator());
                        ring.getComponent(RingPhysics.class).changeTangibility();
                        entityManager.addEntity(ring); 
                    }
                    case 6 -> {
                        goal = new EntityImpl();
                        goal.addComponent(new TransformComponent(xPos, yPos, 1, 3200));
                        goal.addComponent(new GoalComponent(goal));
                        System.out.println("Goal entity created");
                    }
                }
            }
        }
        
        game.start();

        GameStateManager gameStateManager = game.getGameStateManager();

        if (gameStateManager != null && goal != null) {
            GoalComponent goalComponent = goal.getComponent(GoalComponent.class);
            if (goalComponent != null) {
                gameStateManager.initState(sonic, levelGrid, sonicSize, goalComponent);
                System.out.println("GameStateManager initialized successfully.");
            } else {
                System.err.println("GoalComponent missing!");
            }
        } else {
            System.err.println("GameStateManager or goal not initialized!");
        }

        
    }
}
