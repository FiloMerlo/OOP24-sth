/*
 * This source file was generated by the Gradle 'init' task
 */
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
        
        
        EntityImpl sonic = new EntityImpl();
        sonic.addComponent(new TransformComponent(0, 0, sonicSize, sonicSize)); 
        sonic.addComponent(new PlayerPhysics(sonic, tileList));
        sonic.addComponent(new SonicAnimator());
        entityManager.addEntity(sonic);
        System.out.println("Transform: " + sonic.getComponent(TransformComponent.class));
        System.out.println("Physics: " + sonic.getComponent(PhysicsComponent.class));
        System.out.println("Animator: " + sonic.getComponent(SonicAnimator.class));

        Game game = new Game();

       
        /* levelGrid è la matrice che indica cosa c'è in ogni porzione del livello
        0 = empty, 1 = Tile, 2 = Static Enemies, 3 = Dynamic Enemies, 4 = player, 5 = ring, 6 = goal*/
        int[][] levelGrid = {
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 5, 5, 5, 2, 0, 5, 0, 0, 1},
        {1, 4, 0, 0, 1, 0, 0, 3, 6, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 10; c++) {  /* sostituire i numeri */
                int xPos = c * tileSize;
                int yPos = r * tileSize;
                
                switch (levelGrid[r][c]){
                    case 1:
                        Rectangle2D.Float tile = new Rectangle2D.Float(xPos, yPos, tileSize, tileSize);
                        tileList.add(tile);
                        System.out.println("tile ok");
                        break;
                    case 2:
                        EntityImpl staticEnemy = new EntityImpl();
                        staticEnemy.addComponent(new EnemyPhysics(0, staticEnemy, tileList, sonic));
                        staticEnemy.addComponent(new TransformComponent(xPos, yPos + tileSize - enemySize, enemySize, enemySize));
                        staticEnemy.addComponent(new StaticEnemyAnimator());
                        entityManager.addEntity(staticEnemy);
                        System.out.println("nemici statici");
                        break;
                    case 3:
                        EntityImpl chasingEnemy = new EntityImpl();
                        chasingEnemy.addComponent(new EnemyPhysics(5, chasingEnemy, tileList, sonic));
                        chasingEnemy.addComponent(new TransformComponent(xPos, yPos + tileSize - enemySize, enemySize, enemySize));
                        chasingEnemy.addComponent(new ChasingEnemyAnimator());
                        entityManager.addEntity(chasingEnemy);                        
                        break;
                    case 4:
                        //
                        break;
                    case 5:
                        EntityImpl ring = new EntityImpl();
                        ring.addComponent(new RingPhysics(ring, tileList, (PlayerPhysics)sonic.getComponent(PhysicsComponent.class)));
                        ring.addComponent(new TransformComponent(xPos + (tileSize - ringSize) / 2, yPos + (tileSize - ringSize) / 2, ringSize, ringSize));
                        ring.addComponent(new RingAnimator());
                        entityManager.addEntity(ring); 
                        break;
                    case 6:
                        EntityImpl goal = new EntityImpl();
                        goal.addComponent(new TransformComponent(xPos, yPos, 1, 3200));
                        goal.addComponent(new GoalComponent(goal));
                        break;
                }

            }
        }
         

         GameStateManager gameStateManager = game.getGameStateManager();


            if (gameStateManager != null) {
                gameStateManager.initState(sonic, levelGrid, sonicSize);
                System.out.println("GameStateManager inizializzato con successo");
            } else {
                System.err.println("GameStateManager non inizializzato correttamente");
            }


            game.start();
            
    }
}
