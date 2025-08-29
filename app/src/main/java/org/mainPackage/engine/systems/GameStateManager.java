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
 * The {@code GameStateManager} is responsible for handling the different states of the game
 * (Menu, Playing, Paused). 
 * <p>
 * It implements the Singleton pattern to ensure only one manager exists throughout the game.
 * This class:
 * <ul>
 *   <li>Delegates updates and rendering to the active {@link GameState}.</li>
 *   <li>Handles user input (keyboard/mouse) and forwards it to the current state.</li>
 *   <li>Coordinates transitions between states based on events or user actions.</li>
 *   <li>Resets the game and cleans up observers when required.</li>
 * </ul>
 * </p>
 */

public class GameStateManager implements Observer {

    /** Singleton instance of the GameStateManager. */
    private static GameStateManager instance = null;
    
    private GameState currentState; 
    private PlayingState playingState;
    private PausedState pausedState;
    
    private SizeView sizeView;

    /** Runnable to execute when shutting down the game. */
    private Runnable shutdownGame;

    // --- Playing state specific fields ---
    private Entity sonicEntity;
    private int[][] levelGrid;
    private int tileWorldSize;
    private GoalComponent goal;

    /** The current state enumeration (MENU, PLAYING, PAUSED). */
    private State currentEnumState;
    
    private LevelManager levelManager;

    // --- Stored parameters for resetting the game ---
    private int storedTileSize, storedCEnemySize, storedSEnemySize, storedRingSize, storedSonicSize;
    private int[][] storedLevelGrid;
    
    /**
     * Enumeration of possible game states.
     */

    public enum State {
        MENU,
        PLAYING,
        PAUSED
    }

    /**
     * @return the singleton instance of the {@code GameStateManager}.
     */
    
    public static GameStateManager getInstance() {
        if (instance == null){
            instance = new GameStateManager();
        }
        return instance;
    }
    
    /** Private constructor to enforce singleton usage. */

    private GameStateManager() {
    }
    
    /**
     * Initializes the manager with the required {@link SizeView} and shutdown logic.
     * Sets the initial state to {@link State#MENU}.
     *
     * @param sizeView the utility for handling resolution/scaling
     * @param shutdownGame logic to run when shutting down the game
     */
    
    public void initState(SizeView sizeView, Runnable shutdownGame) {
        this.sizeView = sizeView;
        this.shutdownGame = shutdownGame;
        setState(State.MENU);
    }
    
    /**
     * Initializes the game elements for the {@link PlayingState}.
     * If already initialized, updates the elements instead.
     *
     * @param sonicEntity the player entity
     * @param levelGrid   the grid layout of the level
     * @param tileWorldSize size of each tile in world units
     * @param goal        the level goal component
     */
    
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

        if (goal != null) {
        goal.addObserver(this);
        }
    }

    /** Sets the LevelManager for level-related operations. */

    public void setLevelManager(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    /** 
     * Stores level parameters for future resets.
     */

    public void setLevelParameters(int tileSize, int sEnemySize, int cEnemySize, int ringSize, int sonicSize, int[][] levelGrid) {
        this.storedTileSize = tileSize;
        this.storedCEnemySize = cEnemySize;
        this.storedSEnemySize = sEnemySize;
        this.storedRingSize = ringSize;
        this.storedSonicSize = sonicSize;
        this.storedLevelGrid = levelGrid;
    }

    /** 
     * Changes the current game state.
     * @param state the new state to transition to
     */

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
    }

    /** 
     * This method is useful for quickly checking which state is active
     * without accessing the full GameState object.
     * @return the current state enumeration (MENU, PLAYING, PAUSED).
     * 
     */
    
    public State getEnumState(){
        return currentEnumState;
    }

    /** 
     * This provides direct access to the object handling logic and rendering
     * of the current state (e.g. MenuState, PlayState, PauseState).
     * @return the current active {@link GameState} instance.
     */

    public GameState getCurrentState() {
        return currentState;
    }

    // --- Playing state specific getters and setters ---

    /** 
     * Sets the player entity (Sonic).
     * @param Sonic the player entity
     */

    public void setSonicEntity(Entity Sonic) {
        this.sonicEntity = Sonic;
    }

    /**
     * Sets the level grid layout.
     * @param grid
     */

    public void setLevelGrid(int[][] grid) {
        this.levelGrid = grid;
    }

    /**
     * Sets the size of each tile in world units.
     * @param size the tile size
     */

    public void setTileWorldSize(int size) {
        this.tileWorldSize = size;
    }

    /**
     * @return the player entity (Sonic).
     */

    public Entity getSonicEntity() {
        return this.sonicEntity;
    }

    /**
     * @return the level grid layout.
     */

    public int[][] getLevelGrid() {
        return this.levelGrid;
    }

    /**
     * @return the size of each tile in world units.
     */

    public int getTileWorldSize() {
        return this.tileWorldSize;
    }

    // --- Core game loop methods ---

    /** 
     * Updates the current game state.
     * Delegates to the active state's update method.
     */

    public void update() {
        if (currentState != null) 
            currentState.update();
    }

    /** 
     * Renders the current game state.
     * Delegates to the active state's draw method.
     * @param g the Graphics context to draw on
     */

    public void draw(Graphics g) {
        if (currentState != null) {
            currentState.draw(g);
        } else {
            throw new IllegalStateException("GameStateManager: no state to draw");
        }
    }

    /** 
     * Executes the shutdown logic for the game.
     * Calls the provided shutdownGame Runnable if set.
     */

    public void gameShutdown() {
        if (shutdownGame != null) {
            shutdownGame.run(); 
        }
    }

    // --- Input handling methods ---

    /** 
     * Handles key press events and delegates to the current state.
     * @param e the KeyEvent representing the key press
     */

    public void keyPressed(KeyEvent e) {
        if (currentState != null) {
            currentState.keyPressed(e);
        }
    }

    /** 
     * Handles key release events and delegates to the current state.
     * @param e the KeyEvent representing the key release
     */

    public void keyReleased(KeyEvent e) {
        if (currentState != null) {
            currentState.keyReleased(e);
        }
    }

    /** 
     * Handles mouse click events and delegates to the current state.
     * @param e the MouseEvent representing the mouse click
     */
    
    public void mousePressed(MouseEvent e) {
        if (currentState != null) {
            currentState.mousePressed(e);
        }
    }

    /** 
     * Handles mouse movement events and delegates to the current state.
     * @param e the MouseEvent representing the mouse movement
     */
    
    public void mouseMoved (MouseEvent e) {
        if (currentState != null) {
            currentState.mouseMoved(e);
        }
    }

    /**
     * Receives and processes events dispatched by observable game components.
     * <p>
     * This method listens for {@link GameEvent}s (e.g., GAME_OVER, LEVEL_STARTED, 
     * PAUSE, RESUME) and triggers the appropriate state transitions or resets.
     * It acts as the main link between the event system and the game state 
     * management.
     * </p>
     *
     * @param e the received {@link Event}, expected to be of type {@link GameEvent}
     */
   
    @Override
    public void onNotify(Event e) {
        if (e instanceof GameEvent){
            switch (e.getType()){
                case GAME_OVER:
                case STAGE_CLEARED:
                    resetGame();
                    setState(State.MENU);
                    break;
                case LEVEL_STARTED:
                    setState(State.PLAYING);
                    break;
                case PAUSE:
                    setState(State.PAUSED);
                    break;
                case RESUME:
                    setState(State.PLAYING);
                    break;
                default:
                    break;
            }
        }
    }   

    /**
     * Resets the game state by reinitializing the current level and its entities.
     * <p>
     * This method is typically triggered by events such as {@code GAME_OVER} 
     * or {@code STAGE_CLEARED}. It uses the {@link LevelManager} to reset 
     * the level data and reinitializes the {@link PlayingState}.
     * Removes observers from the current player entity to avoid memory leaks.
     * </p>
     */

    private void resetGame() {
        removeObservers((EntityImpl) this.sonicEntity);
        levelManager.resetLevel();
        initGame(levelManager.getSonicEntity(), storedLevelGrid, storedTileSize, levelManager.getGoal());
    }
    
    /**
     * Removes observers attached to the specified player entity and related components.
     * <p>
     * Ensures proper cleanup of references to avoid memory leaks or unintended 
     * event propagation after a reset. This includes:
     * <ul>
     *   <li>Detaching {@link WalletComponent} and {@link InputComponent} observers from the player</li>
     *   <li>Detaching the {@link GoalComponent} observer from this manager</li>
     *   <li>Detaching this {@code GameStateManager} from the entity itself</li>
     * </ul>
     * </p>
     *
     * @param entityImpl the player entity (typically Sonic) from which observers should be removed
     */
    
    private void removeObservers(EntityImpl entityImpl) {
        
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