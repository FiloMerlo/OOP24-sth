package org.mainPackage;

import org.mainPackage.core.Game;
import org.mainPackage.engine.components.*;
import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.engine.components.graphics.*;
import org.mainPackage.engine.entities.impl.*;
import org.mainPackage.engine.events.api.EventType;
import org.mainPackage.enums.EnemyType;
import org.mainPackage.state_management.GameStateManager;
import org.mainPackage.level.LevelManager; // Importa la nuova classe LevelManager
import org.mainPackage.level.LevelGenerator;
import org.mainPackage.engine.systems.InputManager; // Importa InputManager



import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        
        
        
        int tileSize = 64, enemySize = 32, ringSize = 16, sonicSize = 54;

        int levelRows = 5; 
        int levelCols = 250; 
        LevelGenerator levelGenerator = new LevelGenerator(levelRows, levelCols);
        
        
        int[][] levelGrid = levelGenerator.getLevelGrid();

        LevelManager levelManager = new LevelManager(tileSize, enemySize, ringSize, sonicSize, levelGrid);
        
        Game game = new Game();
        
        GameStateManager gameStateManager = GameStateManager.getInstance();

       
        gameStateManager.setLevelManager(levelManager);
        gameStateManager.setLevelParameters(tileSize, enemySize, ringSize, sonicSize, levelGrid);

        LevelManager.LevelLoadResult initialLoadResult = levelManager.loadLevel();
        EntityImpl sonic = initialLoadResult.sonic;
        
        ArrayList<Rectangle2D.Float> tileList = initialLoadResult.tileList;
        GoalComponent goalComponent = initialLoadResult.goalComponent;
       
        /* Debug */
        System.out.println("Transform: " + sonic.getComponent(TransformComponent.class));
        System.out.println("Physics: " + sonic.getComponent(PhysicsComponent.class));
        System.out.println("Animator: " + sonic.getComponent(SonicAnimator.class));

        
       

        if (gameStateManager != null && goalComponent != null) {
            gameStateManager.initGame(sonic, levelGrid, tileSize, goalComponent);
            System.out.println("GameStateManager inizializzato con successo.");
        } else {
            System.err.println("GameStateManager o GoalComponent non inizializzati!");
        }
        
       
    }
}


/* note
/* TODO: sistemare il bug per cui sonic si blocca dopo una collisione con anelli
sistemare l'archittetura del levelManager e gsm */  