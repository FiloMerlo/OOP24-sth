package org.mainPackage.state_management;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import org.mainPackage.util.SizeView;
import org.mainPackage.engine.components.GoalComponent;
import org.mainPackage.engine.components.InputComponent;
import org.mainPackage.engine.components.WalletComponent;
import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.engine.entities.api.Entity;
import org.mainPackage.engine.entities.impl.EntityImpl;
import org.mainPackage.engine.entities.impl.EntityManagerImpl;
import org.mainPackage.engine.events.api.Event;
import org.mainPackage.engine.events.api.Observer;
import org.mainPackage.engine.events.impl.GameEvent;

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
  
    public enum State {
        MENU,
        PLAYING,
        PAUSED
    }
    
    public static GameStateManager getInstance() {
        if (instance == null){
            instance = new GameStateManager();
        }
        return instance;
    }
    
    public void setGameState(SizeView sizeView, Runnable shutdownGame){
        this.sizeView = sizeView;
        this.shutdownGame = shutdownGame;
        setState(State.MENU);
    }

    private GameStateManager() {
    }

    public void initState(Entity sonicEntity, int[][] levelGrid, int tileWorldSize, GoalComponent goal) {
        this.sonicEntity = sonicEntity;
        this.levelGrid = levelGrid;
        this.tileWorldSize = tileWorldSize;
        this.playingState = new PlayingState(this, sizeView, sonicEntity, levelGrid, tileWorldSize, goal);
        this.pausedState = new PausedState(this, sizeView);
        this.goal = goal;
        this.goal.addObserver(this);
    }

    
    public void setState(State state) {
        this.currentEnumState = state;
        
        switch (state) {
            case MENU:
                currentState = new MenuState(this, sizeView); 
                break;
            case PLAYING:
                currentState = playingState; 
                break;
            case PAUSED:
                currentState = pausedState;
                break;
        }
        System.out.println("Stato cambiato in: " + state);
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
            System.out.println("DEBUG: GameStateManager - keyPressed delegato allo stato corrente. Key: " + KeyEvent.getKeyText(e.getKeyCode()));

        if (currentState != null) {
            currentState.keyPressed(e);
        }
    }


    public void keyReleased(KeyEvent e) {
            System.out.println("DEBUG: GameStateManager - keyReleased delegato allo stato corrente. Key: " + KeyEvent.getKeyText(e.getKeyCode()));

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

    /**
     * Given a @param Event , it detects the {@link EventType} 
     * and set the {@link GameState} according to it
     */
   @Override
public void onNotify(Event e) {
    System.out.println("DEBUG: GameStateManager - onNotify ricevuto evento: " + e.getType());
    if (e instanceof GameEvent){
        EntityImpl entityImpl = (EntityImpl) sonicEntity;
        switch (e.getType()){
            case GAME_OVER:
                System.out.println("SIAMO NEL MENU!!!");
                setState(State.MENU);
                System.out.println("DEBUG: GameStateManager - Stato cambiato a MENU (GAME_OVER).");
                EntityManagerImpl.getInstance().killAllEntities();
                removeObservers(entityImpl);
                break;
            case STAGE_CLEARED:
                setState(State.MENU);
                System.out.println("DEBUG: GameStateManager - Stato cambiato a MENU (LEVEL_COMPLETED).");
                EntityManagerImpl.getInstance().killAllEntities();
                removeObservers(entityImpl);
                break;
            case LEVEL_STARTED:
                setState(State.PLAYING);
                System.out.println("DEBUG: GameStateManager - Stato cambiato a PLAYING (LEVEL_STARTED).");
                break;
            case PAUSE:
                setState(State.PAUSED);
                System.out.println("DEBUG: GameStateManager - Stato cambiato a PAUSED.");
                break;
            case RESUME:
                setState(State.PLAYING);
                System.out.println("DEBUG: GameStateManager - Stato cambiato a PLAYING (RESUME).");
                break;
            default:
                break;
        }
    }
}   
    /**
     * 
     * @param entityImpl Removal of all {@link Observer}s from {@link Subject}s but {@link PlayerPhysics}
     */
    private void removeObservers(EntityImpl entityImpl) {
        System.out.println("RIMOZIONE DEGLI OBSERVERS");
        entityImpl.removeObserver(entityImpl.getComponent(WalletComponent.class));
        entityImpl.removeObserver(entityImpl.getComponent(InputComponent.class));
        this.goal.removeObserver(this);
        entityImpl.removeObserver(this);    
    }
}