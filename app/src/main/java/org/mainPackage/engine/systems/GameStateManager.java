package org.mainPackage.engine.systems;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import org.mainPackage.util.SizeView;
import org.mainPackage.engine.components.GoalComponent;
import org.mainPackage.engine.components.InputComponent;
import org.mainPackage.engine.components.WalletComponent;
import org.mainPackage.engine.entities.api.Entity;
import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.engine.events.api.Event;
import org.mainPackage.engine.events.api.Observer;
import org.mainPackage.engine.events.impl.GameEvent;
import org.mainPackage.state_management.GameState;
import org.mainPackage.state_management.MenuState;
import org.mainPackage.state_management.PausedState;
import org.mainPackage.state_management.PlayingState;

/**
 * Gestisce i diversi stati del gioco (es. menu, gioco, pausa).
 * Si occupa di delegare l'aggiornamento e il disegno dello stato corrente. Delega anche gli input
 */
public class GameStateManager implements Observer {

    private static GameStateManager instance = null;
    
    private GameState currentState; 
    private PlayingState playingState;
    private PausedState pausedState;
    
    private SizeView sizeView;
    private Runnable shutdownGame;

    /* Campi per il playingState */
    private Entity sonicEntity;
    private int[][] levelGrid;
    private int tileWorldSize;
    private GoalComponent goal;

    private State currentEnumState;
    private LevelManager levelManager;
    private int storedTileSize, storedCEnemySize, storedSEnemySize, storedRingSize, storedSonicSize;
    private int[][] storedLevelGrid;
  
    public enum State {
        MENU,
        PLAYING,
        PAUSED
    }
    
    public static GameStateManager getInstance() {
        if (instance == null){
            instance = new GameStateManager();
            System.out.println("GameStateManager: istanza creata.");
        }
        System.out.println("GameStateManager: istanza già esistente, restituita.");
        return instance;
    }
    
    private GameStateManager() {
        System.out.println("GameStateManager: costruttore chiamato.");
    }
    
    
    public void initState(SizeView sizeView, Runnable shutdownGame) {
        this.sizeView = sizeView;
        this.shutdownGame = shutdownGame;
        setState(State.MENU);
        System.out.println("GameStateManager: inizializzazione con SizeView e shutdownGame e MENU.");
    }

   
    public void initGame(Entity sonicEntity, int[][] levelGrid, int tileWorldSize, GoalComponent goal) {
        this.sonicEntity = sonicEntity;
        this.levelGrid = levelGrid;
        this.tileWorldSize = tileWorldSize;
        this.goal = goal;
        
        
        if (this.playingState == null) {
            
            this.playingState = new PlayingState(this, sizeView, sonicEntity, levelGrid, tileWorldSize, goal);
        } else {
            this.playingState.updateGameElements(sonicEntity, levelGrid, tileWorldSize, goal);
        }

        if (this.pausedState == null) {
            this.pausedState = new PausedState(this, sizeView);
        }
        
        if (this.goal != null) { 
            this.goal.removeObserver(this); 
        }
        this.goal = goal; 
        if (this.goal != null) { 
            this.goal.addObserver(this); 
        }
    }

 
    public void setLevelManager(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

  
    public void setLevelParameters(int tileSize, int sEnemySize, int cEnemySize, int ringSize, int sonicSize, int[][] levelGrid) {
        this.storedTileSize = tileSize;
        this.storedCEnemySize = cEnemySize;
        this.storedSEnemySize = sEnemySize;
        this.storedRingSize = ringSize;
        this.storedSonicSize = sonicSize;
        this.storedLevelGrid = levelGrid;
    }

    
    public void setState(State state) {
        this.currentEnumState = state;
        
        switch (state) {
            case MENU:
                currentState = new MenuState(this, sizeView);
                System.out.println("GameStateManager: creato nuovo menu."); 
                break;
            case PLAYING:
                currentState = playingState; 
                break;
            case PAUSED:
                currentState = pausedState;
                break;
        }
        System.out.println("GameStateManager: stato cambiato in: " + state);
    }

    public State getEnumState(){
        return currentEnumState;
    }
    public GameState getCurrentState() {
        return currentState;
    }

    /* Metodi per playing state */
    public void setSonicEntity(Entity Sonic) {
        this.sonicEntity = Sonic;
    }

    public void setLevelGrid(int[][] grid) {
        this.levelGrid = grid;
    }

    public void setTileWorldSize(int size) {
        this.tileWorldSize = size;
    }

    
    public Entity getSonicEntity() {
        return this.sonicEntity;
    }

    public int[][] getLevelGrid() {
        return this.levelGrid;
    }

    public int getTileWorldSize() {
        return this.tileWorldSize;
    }

    
    public void update() {
        if (currentState != null) 
            currentState.update();
    }

    
    public void draw(Graphics g) {
        if (currentState != null) {
            currentState.draw(g);
        } else {
            System.out.println("GameStateManager: nessuno stato da disegnare.");
        }
    }


    public void gameShutdown() {
        if (shutdownGame != null) {
            shutdownGame.run(); 
        }
    }


    public void keyPressed(KeyEvent e) {
            System.out.println("GameStateManager - keyPressed delegato allo stato corrente. Key: " + KeyEvent.getKeyText(e.getKeyCode()));

        if (currentState != null) {
            currentState.keyPressed(e);
        }
    }


    public void keyReleased(KeyEvent e) {
            System.out.println("GameStateManager - keyReleased delegato allo stato corrente. Key: " + KeyEvent.getKeyText(e.getKeyCode()));

        if (currentState != null) {
            currentState.keyReleased(e);
        }
    }

    
    public void mousePressed(MouseEvent e) {
        if (currentState != null) {
            currentState.mousePressed(e);
        }
    }
    
    public void mouseMoved (MouseEvent e) {
        if (currentState != null) {
            currentState.mouseMoved(e);
        }
    }

  
   @Override
    public void onNotify(Event e) {
        System.out.println("GameStateManager - onNotify ricevuto evento: " + e.getType());
        if (e instanceof GameEvent){
            
            EntityImpl currentSonic = (EntityImpl) this.sonicEntity; 

            switch (e.getType()){
                case GAME_OVER:
                case STAGE_CLEARED:
                    System.out.println("GameStateManager - Evento GAME_OVER o STAGE_CLEARED rilevato. Reset del gioco.");
                    resetGame();
                    setState(State.MENU);
                    removeObservers(currentSonic);
                    System.out.println("GameStateManager - Stato cambiato a MENU da evento.");
                    break;
                case LEVEL_STARTED:
                    setState(State.PLAYING);
                    System.out.println("GameStateManager - Stato cambiato a PLAYING (LEVEL_STARTED) da evento.");
                    break;
                case PAUSE:
                    setState(State.PAUSED);
                    System.out.println("GameStateManager - Stato cambiato a PAUSED da evento.");
                    break;
                case RESUME:
                    setState(State.PLAYING);
                    System.out.println("GameStateManager - Stato cambiato a PLAYING (RESUME) da evento.");
                    break;
                default:
                    break;
            }
        }
    }   

    /**
     * Resetta lo stato del gioco, ricreando il livello e gli elementi di gioco.
     */
    private void resetGame() {
        System.out.println("GameStateManager - Esecuzione del reset del gioco.\n");
    
        levelManager.resetLevel();
        initGame(levelManager.getSonicEntity(), storedLevelGrid, storedTileSize, levelManager.getGoal());
        System.out.println("GameStateManager - Livello resettato e GameStateManager re-inizializzato.");
    
    }
   
    private void removeObservers(EntityImpl entityImpl) {
        System.out.println("RIMOZIONE DEGLI OBSERVERS");
        if (entityImpl.getComponent(WalletComponent.class) != null) {
            entityImpl.removeObserver(entityImpl.getComponent(WalletComponent.class));
        }
        if (entityImpl.getComponent(InputComponent.class) != null) {
            entityImpl.removeObserver(entityImpl.getComponent(InputComponent.class));
        }
        
        if (this.goal != null) {
            this.goal.removeObserver(this);
        }
        entityImpl.removeObserver(this);    
    }
}