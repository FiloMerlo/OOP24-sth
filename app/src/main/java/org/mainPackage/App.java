package org.mainPackage;

import org.mainPackage.core.Game;
import org.mainPackage.engine.components.*;
import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.engine.components.graphics.*;
import org.mainPackage.engine.entities.impl.*;
import org.mainPackage.engine.events.api.EventType;
import org.mainPackage.enums.EnemyType;
import org.mainPackage.state_management.GameStateManager;


import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        int tileSize = 64, enemySize = 64, ringSize = 16, sonicSize = 54;
        ArrayList<Rectangle2D.Float> tileList = new ArrayList<>();
        EntityManagerImpl entityManager = EntityManagerImpl.getInstance();

        /* Sonic */
        EntityImpl sonic = PlayerFactory.createPlayer(tileList, sonicSize);
        HUDComponent hudRing = new HUDComponent(sonic);
        EntityImpl hud = new EntityImpl();
        hud.addComponent(hudRing);
        entityManager.addEntity(sonic);
        entityManager.addEntity(hud);
       
        /* Debug */
        System.out.println("Transform: " + sonic.getComponent(TransformComponent.class));
        System.out.println("Physics: " + sonic.getComponent(PhysicsComponent.class));
        System.out.println("Animator: " + sonic.getComponent(SonicAnimator.class));

        Game game = new Game();

        /* Level */
        int[][] levelGrid = {
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 4, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 5, 5, 5, 0, 2, 6, 1},
            {1, 0, 0, 0, 0, 0, 0, 3, 1, 1},
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
                        EntityImpl staticEnemy = EnemyFactory.createEnemy(EnemyType.STATIC, xPos, yPos, enemySize, sonicSize, tileSize, tileList, sonic);
                        entityManager.addEntity(staticEnemy);
                        System.out.println("Static enemy added");
                    }
                    case 3 -> {
                        EntityImpl chasingEnemy = EnemyFactory.createEnemy(EnemyType.CHASING, xPos, yPos, enemySize, sonicSize, tileSize, tileList, sonic);
                        entityManager.addEntity(chasingEnemy);
                        System.out.println("Chasing enemy added");
                    }
                    case 4 -> {
                        /* Replace Sonic's TransformComponent with a new one derived from map */
                        sonic.getComponent(TransformComponent.class).setX(xPos);
                        sonic.getComponent(TransformComponent.class).setY(yPos + tileSize - sonicSize);
                        System.out.println("Sonic positioned at: " + xPos + ", " + (yPos + tileSize - sonicSize));

                    }

                    case 5 -> {
                        EntityImpl ring = RingFactory.createRing(xPos, yPos, ringSize, tileSize, tileList, sonic);
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
        
        GameStateManager gameStateManager = GameStateManager.getInstance();

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

        game.start();
    }
}
