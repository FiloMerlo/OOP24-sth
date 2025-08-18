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
 * Gestisce la creazione e il reset del livello di gioco.
 * Questa classe è responsabile di istanziare e posizionare tutti gli elementi del livello,
 * come Sonic, i nemici, gli anelli, i blocchi (tiles) e l'obiettivo.
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

    public LevelManager(int tileSize, int staticEnemySize, int chasingEnemySize, int ringSize, int sonicSize, int[][] levelGrid) {
        this.tileSize = tileSize;
        this.staticEnemySize = staticEnemySize;
        this.chasingEnemySize = chasingEnemySize;
        this.ringSize = ringSize;
        this.sonicSize = sonicSize;
        this.levelGrid = levelGrid;
        System.out.println("LevelManager: Inizializzazione livello");
    }


    public LevelLoadResult loadLevel() {
        
        ArrayList<Rectangle2D.Float> tileList = new ArrayList<>();
        EntityManagerImpl entityManager = EntityManagerImpl.getInstance();
        System.out.println("LevelManager: Inizio caricamento livello");

        entityManager.killAllEntities(); 
        
       EntityImpl sonic = PlayerFactory.createPlayer(tileList, sonicSize, ringSize);
        HUDComponent hudRing = new HUDComponent(sonic);
        EntityImpl hud = new EntityImpl();
        hud.addComponent(hudRing);
        entityManager.addEntity(sonic);
        entityManager.addEntity(hud);

        EntityImpl goal = null;

        
        for (int r = 0; r < levelGrid.length; r++) {
            for (int c = 0; c < levelGrid[0].length; c++) {
                int xPos = c * tileSize;
                int yPos = r * tileSize;

                switch (levelGrid[r][c]) {
                    case 1 -> { /* Tile*/
                        Rectangle2D.Float tile = new Rectangle2D.Float(xPos, yPos, tileSize, tileSize);
                        tileList.add(tile);
                        /*System.out.println("Tile posizionato a: " + xPos + ", " + yPos);*/
                    }
                    case 2 -> { /*static enemy*/
                        EntityImpl staticEnemy = EnemyFactory.createEnemy(EnemyType.STATIC, xPos, yPos, staticEnemySize, sonicSize, tileSize, tileList, sonic);
                        entityManager.addEntity(staticEnemy);
                        /*System.out.println("Nemico statico aggiunto");*/
                    }
                    case 3 -> { /* chasing enemy*/
                        EntityImpl chasingEnemy = EnemyFactory.createEnemy(EnemyType.CHASING, xPos, yPos, chasingEnemySize, sonicSize, tileSize, tileList, sonic);
                        entityManager.addEntity(chasingEnemy);
                        /*System.out.println("Nemico inseguitore aggiunto");*/
                    }
                    case 4 -> { /* Sonic position */
                        sonic.getComponent(TransformComponent.class).setX(xPos);
                        sonic.getComponent(TransformComponent.class).setY(yPos + tileSize - sonicSize);
                        System.out.println("Sonic posizionato a: " + xPos + ", " + (yPos + tileSize - sonicSize));
                    }
                    case 5 -> { /* Ring */
                        EntityImpl ring = RingFactory.createRing(xPos, yPos, ringSize, tileSize, tileList, sonic, false);
                        entityManager.addEntity(ring);
                    }
                    case 6 -> { /* Goal */
                        goal = new EntityImpl();
                        goal.addComponent(new TransformComponent(xPos, yPos, 1, 3200));
                        goal.addComponent(new GoalComponent(goal, sonic));
                        
                        entityManager.addEntity(goal);
                        System.out.println("Entità obiettivo creata");
                    }
                }
            }
        }

        if (goal == null) {
            System.err.println("ATTENZIONE: Nessun obiettivo (goal) trovato nella griglia del livello!");
        }

        return new LevelLoadResult(sonic, tileList, goal != null ? goal.getComponent(GoalComponent.class) : null);
    }
    
    public void resetLevel(){
        EntityManagerImpl.getInstance().killAllEntities();
        InputManager.getInstance().resetInputState(); /* Reset inputs */

        LevelManager.LevelLoadResult newLoadResult = loadLevel();

        this.sonicEntity = newLoadResult.sonic;
        this.goal = newLoadResult.goalComponent;
    }

    public Entity getSonicEntity() {
        return sonicEntity;
    }
    
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
