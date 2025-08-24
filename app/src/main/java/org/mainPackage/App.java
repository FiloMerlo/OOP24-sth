package org.mainPackage;

import org.mainPackage.core.Game;
import org.mainPackage.engine.components.*;
import org.mainPackage.engine.entities.impl.*;
import org.mainPackage.level.LevelGenerator;
import org.mainPackage.engine.systems.GameStateManager;
import org.mainPackage.engine.systems.LevelManager;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Main application class for the Sonic Game.
 * This class serves as the entry point and handles the initial setup
 * of the game world, entities, and core systems.
 * 
*/

public class App {
    /**
     * Main method that initializes and starts the Sonic game.
     * <p>
     * It creates the level grid, sets up entity managers, and initializes
     * the game state with all required components.
     * </p>
     *
     * @param args Command line arguments (not used).
     * @throws IllegalStateException if the {@link GameStateManager} or 
     *         {@link GoalComponent} cannot be initialized correctly.
     */
    public static void main(String[] args) {
        // --- Game parameters ---
        int tileSize = 48, staticEnemySize = 32, chasingEnemySize = 48, ringSize = 16, sonicSize = 48;
        int levelRows = 5; 
        int levelCols = 150; 
        
        // --- Level generation ---
        LevelGenerator levelGenerator = new LevelGenerator(levelRows, levelCols);
        int[][] levelGrid = levelGenerator.getLevelGrid();
        LevelManager levelManager = new LevelManager(tileSize, staticEnemySize, chasingEnemySize, ringSize, sonicSize, levelGrid);
        
        // --- Game initialization ---
        Game game = new Game();
        
        GameStateManager gameStateManager = GameStateManager.getInstance();
        gameStateManager.setLevelManager(levelManager);
        gameStateManager.setLevelParameters(tileSize, staticEnemySize, chasingEnemySize, ringSize, sonicSize, levelGrid);
        
        // --- Loading the level ---
        LevelManager.LevelLoadResult initialLoadResult = levelManager.loadLevel();
        EntityImpl sonic = initialLoadResult.sonic;
        
        ArrayList<Rectangle2D.Float> tileList = initialLoadResult.tileList;
        GoalComponent goalComponent = initialLoadResult.goalComponent;
       
        // --- Final initialization ---
        if (gameStateManager != null && goalComponent != null) {
            gameStateManager.initGame(sonic, levelGrid, tileSize, goalComponent);
        } else {
           throw new IllegalStateException("Failed to initialize GameStateManager or GoalComponent.");
        }
    }
}


