package org.mainPackage.state_management;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.mainPackage.engine.events.impl.SubjectImpl;
import org.mainPackage.engine.systems.GameStateManager;
import org.mainPackage.util.SizeView;


/**
 * Abstract base class that defines a generic game state in the 
 * state management system. 
 * <p> 
 * A {@code GameState} represents a particular phase of the game 
 * (e.g., Menu, Playing, Paused). Each state is responsible for 
 * handling its own update and rendering logic, while also 
 * responding to user input events.
 * </p> 
 * 
 * <p> 
 * By extending {@link SubjectImpl}, a game state can also notify 
 * observers of specific events.
 * </p> 
 */

public abstract class GameState extends SubjectImpl {
    protected GameStateManager gameStateManager;
    protected SizeView sizeView;
    
    /**
     * Constructs a new game state.
     *
     * @param gameStateManager the state manager responsible for 
     *                         switching and maintaining game states
     * @param sizeView provides screen size and scaling information 
     *                 for rendering
     */

    public GameState(GameStateManager gameStateManager, SizeView sizeView) {
        this.gameStateManager = gameStateManager;
        this.sizeView = sizeView;
    }

    // --- Abstract methods - must be implemented by concrete game states ---
    
    /**
     * Updates the game state logic. This method is called periodically 
     * to update the state of the game, such as processing input, 
     * updating animations and managing game logic.
     */
    
    public abstract void update();

    /**
     * Draws the current state of the game to the provided graphics context.
     * This method is responsible for rendering all visual elements of the 
     * game state, such as backgrounds and sprites.
     *
     * @param g the graphics context to draw on
     */
    
    public abstract void draw(Graphics g);

    
    // --- Optional input handling methods ---
    
    /**
     * Handles keyboard key press events. 
     * <p>
     * By default, this method is empty. Subclasses can override 
     * it to implement custom behavior.
     * </p>
     *
     * @param e the {@link KeyEvent} representing the key pressed
     */
    
    public void keyPressed(KeyEvent e) {} 
    
    /**
     * Handles keyboard key release events. 
     * <p>
     * By default, this method is empty. Subclasses can override 
     * it to implement custom behavior.
     * </p>
     *
     * @param e the {@link KeyEvent} representing the key released
     */

    public void keyReleased(KeyEvent e) {} 
    
    /**
     * Handles mouse press events.
     * <p>
     * This method is particularly useful for states like 
     * {@code PausedState}, where interaction with on-screen 
     * buttons (e.g., EXIT) is required.
     * </p>
     *
     * @param e the {@link MouseEvent} representing the mouse press
     */
    
    public void mousePressed(MouseEvent e) {}
    
    /**
     * Handles mouse mouved events.
     * <p>
     * This method is particularly useful for states like
     * {@code PausedState}, where interaction based on cursor
     * movement over on-screen
     * </p>
     * @param e the {@link MouseEvent} representing the mouse movement
     */
    
    public void mouseMoved(MouseEvent e) {}

}