package org.mainPackage.state_management;

import java.awt.*;
import java.awt.event.KeyEvent;

import org.mainPackage.engine.components.GoalComponent;
import org.mainPackage.engine.components.TransformComponent;
import org.mainPackage.engine.components.PhysicsTypes.PlayerPhysics;
import org.mainPackage.engine.components.graphics.SonicAnimator;
import org.mainPackage.engine.entities.api.Entity;
import org.mainPackage.engine.systems.EntityManagerImpl;
import org.mainPackage.engine.systems.GameStateManager;
import org.mainPackage.renderer.PlayingRenderer;
import org.mainPackage.util.SizeView;

/**
 * PlayingState represents the state of the game when it is actively being played.
 * <p>
 * It manages the rendering of the game world, including the player character and
 * the level grid, and updates game elements such as player animations and camera position.
 * </p>
 * 
 * <p>
 * The {@link PlayingState} extends {@link GameState}, reusing the input-handling structure
 * and ensuring consistency with other states in the system. Rendering tasks are delegated
 * to {@link PlayingRenderer}, which draws the game world based on the current state.
 * </p>
 */
public class PlayingState extends GameState {

    private PlayingRenderer playingRenderer;
    private EntityManagerImpl entityManager; 
    private Entity sonicPlayer;
    private int[][] levelGrid;
    private int tileWorldSize;
    private GoalComponent goal;
    
    /** Tracks the last update time for delta-time calculations. */
    private long lastUpdateTime = System.currentTimeMillis();
    
    private static final float MILLIS_TO_SECONDS = 1000.0f;
    
    /**
     * Constructs a new {@code PlayingState}.
     *
     * @param gameStateManager the manager responsible for handling state transitions
     * @param sizeView utility for handling screen dimensions and scaling
     * @param sonic the player entity
     * @param grid the tile grid representing the level layout
     * @param tileSize the size of each tile in world units
     * @param goal the goal component representing the level objective
     */
    
    public PlayingState(GameStateManager gameStateManager, SizeView sizeView, Entity sonic, int[][] grid, int tileSize, GoalComponent goal) {
        super(gameStateManager, sizeView);
        this.entityManager = EntityManagerImpl.getInstance();
        this.sonicPlayer = sonic;
        this.levelGrid = grid;
        this.tileWorldSize = tileSize;
        //this.goal = goal;
        this.playingRenderer = new PlayingRenderer(entityManager, levelGrid, tileWorldSize);
        
        if (this.sonicPlayer == null) {
            throw new IllegalArgumentException("Sonic player entity cannot be null in PlayingState constructor.");
        }
    }

    /**
     * Updates the game elements and reinitializes the {@link PlayingRenderer}.
     * <p>
     * This method can be called to refresh the state whenever the grid, 
     * player entity, tile size, or goal changes.
     * </p>
     *
     * @param sonic the player entity
     * @param grid the updated level grid
     * @param tileSize the updated tile size
     * @param goal the updated goal component
     */

    public void updateGameElements(Entity sonic, int[][] grid, int tileSize, GoalComponent goal) {
        this.sonicPlayer = sonic;
        this.levelGrid = grid;
        this.tileWorldSize = tileSize;
        this.goal = goal;
        this.playingRenderer = new PlayingRenderer(entityManager, levelGrid, tileWorldSize);
    }

    /**
     * Updates all game entities, animations, and the camera position.
     * <p>
     * Uses {@code deltaTime} to ensure consistent updates independent of frame rate.
     * Also updates Sonic's animation state and centers the camera on Sonic 
     * if the entity exists.
     * </p>
     */

    @Override
    public void update() {
        long currentTime = System.currentTimeMillis();
        float deltaTime = (currentTime - lastUpdateTime) / MILLIS_TO_SECONDS;
        lastUpdateTime = currentTime;
        
        entityManager.updateEntities(deltaTime);

        if (sonicPlayer != null) { 
            if (sonicPlayer.hasComponent(SonicAnimator.class) && sonicPlayer.hasComponent(PlayerPhysics.class)) {
                SonicAnimator anim = sonicPlayer.getComponent(SonicAnimator.class);
                PlayerPhysics physics = sonicPlayer.getComponent(PlayerPhysics.class);
                anim.setState(physics.getAction()); 
            }
            if (sonicPlayer.hasComponent(TransformComponent.class)) {
                TransformComponent sonicTransform = sonicPlayer.getComponent(TransformComponent.class);
                playingRenderer.updateCamera((int) sonicTransform.getX(), (int) sonicTransform.getY());
            }
        } else {
            throw new IllegalStateException("Sonic player entity is null in PlayingState update method.");
        }
    }
    
    /**
     * Draws the current frame of the game world.
     * <p>
     * Delegates rendering to {@link PlayingRenderer}, which handles 
     * camera transformations and level rendering.
     * </p>
     *
     * @param g the {@link Graphics} context used for rendering
     */

    @Override
    public void draw(Graphics g) { 
        Graphics2D g2d = (Graphics2D) g;
        int currentWidth = sizeView.getSizeWidth();
        int currentHeight = sizeView.getSizeHeight();

        playingRenderer.updateViewPort(currentWidth, currentHeight);
        playingRenderer.render(g2d, currentWidth, currentHeight);
    }
    
    /**
     * Handles keyboard key press events.
     * <p>
     * Currently unused in this state, but kept for consistency and possible future use.
     * </p>
     */

    @Override
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Handles keyboard key release events.
     * <p>
     * Currently unused in this state, but kept for consistency and possible future use.
     * </p>
     */
    
    @Override
    public void keyReleased(KeyEvent e) {
    }
}