package org.mainPackage.engine.systems;

import org.mainPackage.engine.components.GoalComponent;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.components.HUDComponent;
import org.mainPackage.engine.entities.impl.EnemyFactory;
import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.engine.entities.impl.PlayerFactory;
import org.mainPackage.engine.entities.impl.RingFactory;
import org.mainPackage.enums.EnemyType;
import org.mainPackage.engine.entities.api.Entity;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Manages the creation and restoration of the game level.
 * This class is responsible for instantiating and positioning all level elements,
 * such as Sonic, enemies, rings, tiles, and the objective.
 */

public class LevelManager {

    private final int tileSize;
    private final int staticEnemySize;
    private final int chasingEnemySize;
    private final int ringSize;
    private final int sonicSize;
    private final int[][] levelGrid;

    private Entity sonicEntity;
    private GoalComponent goal;

    /**
     * Constructs a LevelManager with the specified parameters.
     *
     * @param tileSize The size of each tile in the level.
     * @param staticEnemySize The size of static enemies.
     * @param chasingEnemySize The size of chasing enemies.
     * @param ringSize The size of rings.
     * @param sonicSize The size of Sonic.
     * @param levelGrid A 2D array representing the level layout.
     */

    public LevelManager(int tileSize, int staticEnemySize, int chasingEnemySize, int ringSize, int sonicSize, int[][] levelGrid) {
        this.tileSize = tileSize;
        this.staticEnemySize = staticEnemySize;
        this.chasingEnemySize = chasingEnemySize;
        this.ringSize = ringSize;
        this.sonicSize = sonicSize;
        this.levelGrid = levelGrid;
    }

    /**
     * Loads the level by scanning the {@code levelGrid} and instantiating entities accordingly.
     * <p>
     * Each integer value in the grid corresponds to an entity type
     *
     * @return A {@link LevelLoadResult} object containing references to
     * the created Sonic entity, the list of tile rectangles, and the goal component
     * 
     */

    public LevelLoadResult loadLevel() {
        
        ArrayList<Rectangle2D.Float> tileList = new ArrayList<>();
        EntityManagerImpl entityManager = EntityManagerImpl.getInstance();
        
        // --- Clear previous entities ---
        entityManager.killAllEntities(); 
        
        // --- Create Sonic entity and HUB ---
        EntityImpl sonic = PlayerFactory.createPlayer(tileList, sonicSize, ringSize);
        HUDComponent hudRing = new HUDComponent(sonic);
        EntityImpl hud = new EntityImpl();
        hud.addComponent(hudRing);
        entityManager.addEntity(sonic);
        entityManager.addEntity(hud);

        EntityImpl goal = new EntityImpl();

        for (int r = 0; r < levelGrid.length; r++) {
            for (int c = 0; c < levelGrid[0].length; c++) {
                int xPos = c * tileSize;
                int yPos = r * tileSize;

                switch (levelGrid[r][c]) {
                    case 1 -> { // --- Tile ---
                        Rectangle2D.Float tile = new Rectangle2D.Float(xPos, yPos, tileSize, tileSize);
                        tileList.add(tile);
                        }
                    case 2 -> { // --- Static enemy ---
                        EntityImpl staticEnemy = EnemyFactory.createEnemy(EnemyType.STATIC, xPos, yPos, staticEnemySize, sonicSize, tileSize, tileList, sonic);
                        entityManager.addEntity(staticEnemy);
                    }
                    case 3 -> { // --- Chasing enemy ---
                        EntityImpl chasingEnemy = EnemyFactory.createEnemy(EnemyType.CHASING, xPos, yPos, chasingEnemySize, sonicSize, tileSize, tileList, sonic);
                        entityManager.addEntity(chasingEnemy);
                    }
                    case 4 -> { // --- Sonic position ---
                        sonic.getComponent(TransformComponent.class).setX(xPos);
                        sonic.getComponent(TransformComponent.class).setY(yPos + tileSize - sonicSize);
                    }
                    case 5 -> { // --- Rings ---
                        EntityImpl ring = RingFactory.createRing(xPos, yPos, ringSize, tileSize, tileList, sonic, false);
                        entityManager.addEntity(ring);
                    }
                    case 6 -> { // --- Goal ---
                        goal.addComponent(new TransformComponent(xPos, yPos, 1, 3200));
                        goal.addComponent(new GoalComponent(goal, sonic));
                        entityManager.addEntity(goal);
                    }
                }
            }
        }
        return new LevelLoadResult(sonic, tileList, goal != null ? goal.getComponent(GoalComponent.class) : null);
    }
    
    /**
     * This method allows restarting the game without recreating a new {@code LevelManager}.
     */

    public void resetLevel(){
        LevelManager.LevelLoadResult newLoadResult = loadLevel();
        this.sonicEntity = newLoadResult.sonic;
        this.goal = newLoadResult.goalComponent;
    }

    /**
     * @return The Sonic entity.
     */

    public Entity getSonicEntity() {
        return sonicEntity;
    }
    
    /**
     * @return The goal component.
     */

    public GoalComponent getGoal() {
        return goal;
    }

    /**
     * Internal class that incapsulates the results of the level load.
     */
    
     public static class LevelLoadResult {
        public final EntityImpl sonic;
        public final ArrayList<Rectangle2D.Float> tileList;
        public final GoalComponent goalComponent;

        public LevelLoadResult(EntityImpl sonic, ArrayList<Rectangle2D.Float> tileList, GoalComponent goalComponent) {
            this.sonic = sonic;
            this.tileList = tileList;
            this.goalComponent = goalComponent;
        }
    }
    
}
